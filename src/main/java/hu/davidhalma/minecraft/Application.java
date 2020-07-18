package hu.davidhalma.minecraft;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = "hu.davidhalma.minecraft")
@EnableMongoRepositories(basePackages = "hu.davidhalma.minecraft.repository")
public class Application {
}

