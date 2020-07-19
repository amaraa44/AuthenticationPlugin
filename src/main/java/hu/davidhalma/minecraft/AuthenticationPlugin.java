package hu.davidhalma.minecraft;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import dev.alangomes.springspigot.SpringSpigotInitializer;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

public class AuthenticationPlugin extends JavaPlugin {

    private ConfigurableApplicationContext context;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        ClassLoader classLoader = getClassLoader();
        ResourceLoader loader = new DefaultResourceLoader(classLoader);
        SpringApplication application = new SpringApplication(loader, Application.class);
        application.addInitializers(new SpringSpigotInitializer(this));
        context = application.run();

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
        rootLogger.setLevel(Level.INFO);
    }

    @Override
    public void onDisable() {
        context.close();
        context = null;
    }
}
