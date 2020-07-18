import de.undercouch.gradle.tasks.download.Download
import kr.entree.spigradle.kotlin.spigot

val spigotVersion: String by project
val pluginApiVersion: String by project
val pluginVersion: String by project
val pluginName: String by project

buildscript {
    repositories {
        mavenCentral()
        maven(uri("https://plugins.gradle.org/m2/"))
    }

    dependencies {
        classpath("gradle.plugin.com.nemosw:spigot-jar:1.0")
        classpath("dev.alangomes:spigot-spring-boot-starter:0.20.3")
        classpath("org.springframework.data:spring-data-mongodb:3.0.1.RELEASE")
    }
}

plugins {
    kotlin("jvm") version "1.3.70"
    id("kr.entree.spigradle") version "1.2.4"
    id("de.undercouch.download") version "4.0.4"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(spigot(spigotVersion))

    compileOnly("org.projectlombok:lombok:1.18.10")
    annotationProcessor("org.projectlombok:lombok:1.18.10")

    compile ("org.mongodb:mongo-java-driver:3.12.6")
    compile("org.springframework.boot:spring-boot-starter:2.3.1.RELEASE")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:2.3.1.RELEASE")
    compile ("org.springframework.security:spring-security-crypto:3.1.0.RELEASE")
    compile ("org.springframework.data:spring-data-mongodb:3.0.1.RELEASE")
    implementation ("dev.alangomes:spigot-spring-boot-starter:0.20.4")
}

spigot {
    authors = listOf("Dávid Halma")
    name = pluginName
    version = pluginVersion
    apiVersion = pluginApiVersion
    load = kr.entree.spigradle.attribute.Load.STARTUP
    permissions {
        create("${pluginName.toLowerCase()}.register") {
            description = "Allows players to use the register command"
            defaults = "true"
        }
        create("${pluginName.toLowerCase()}.login") {
            description = "Allows players to use the login command"
            defaults = "true"
        }
        create("${pluginName.toLowerCase()}.passwordchange") {
            description = "Allows players to use the passwordchange command"
            defaults = "true"
        }
        create("${pluginName.toLowerCase()}.logout") {
            description = "Allows players to use the logout command"
            defaults = "true"
        }
    }
}

tasks {

    register<Delete>("cleanProject") {
        group = "spigot-plugin"

        dependsOn(":clean")

        delete(fileTree("./testserver").exclude("spigot-$spigotVersion.jar"))
    }

    register<Jar>("fatJar") {
        group = "spigot-plugin"

        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        from(configurations.runtimeClasspath.get()
                .onEach { println("add from dependencies: ${it.name}") }
                .map { if (it.isDirectory) it else zipTree(it) })

        val sourcesMain = sourceSets.main.get()
        sourcesMain.allSource.forEach { println("add from sources: ${it.name}") }
        from(sourcesMain.output)

        archiveBaseName.set(pluginName)
    }

    register<Download>("downloadSpigotServer") {
        group = "spigot-plugin"

        src("https://cdn.getbukkit.org/spigot/spigot-$spigotVersion.jar")
        dest("./testserver/spigot-$spigotVersion.jar")
        overwrite(false)
    }

    register<Copy>("setupTestServer") {
        group = "spigot-plugin"

        from(file("./config/devserver"))
        into(file("./testserver"))
    }

    register<Copy>("copyPluginToTestServer") {
        group = "spigot-plugin"

        dependsOn(":cleanProject", ":fatJar", ":setupTestServer")

        from(file("./build/libs/$pluginName.jar"))
        into(file("./testserver/plugins"))
    }

    register<JavaExec>("runTestServer") {
        group = "spigot-plugin"

        dependsOn(":copyPluginToTestServer")

        classpath(files("./testserver/spigot-$spigotVersion.jar"))
        workingDir("./testserver/")
        standardInput = System.`in`
    }
}