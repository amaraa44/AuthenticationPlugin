package hu.davidhalma.minecraft.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings.Builder;
import hu.davidhalma.minecraft.AuthenticationPlugin;
import java.util.Objects;
import org.bukkit.plugin.java.JavaPlugin;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

  private static final AuthenticationPlugin plugin = JavaPlugin.getPlugin(AuthenticationPlugin.class);
  private static final String DATABASE_MONGODB_URI = "authentication.database.mongodb.uri";
  private static final ConnectionString connectionString = new ConnectionString(
      Objects.requireNonNull(plugin.getConfig().getString(DATABASE_MONGODB_URI, "mongodb://localhost:27017/authDb")));

  @Override
  protected void configureClientSettings(Builder builder) {
    builder.applyConnectionString(connectionString);
    super.configureClientSettings(builder);
  }

  @Override
  protected String getDatabaseName() {
    return connectionString.getDatabase();
  }
}
