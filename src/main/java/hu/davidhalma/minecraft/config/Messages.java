package hu.davidhalma.minecraft.config;

import hu.davidhalma.minecraft.AuthenticationPlugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Messages {

  private static final String PREFIX = "authentication.messages.";
  private final JavaPlugin plugin = JavaPlugin.getPlugin(AuthenticationPlugin.class);

  private String getMessageOrDefault(String config, String defaultMessage) {
    return plugin.getConfig().getString(PREFIX + config, defaultMessage);
  }

  public String getNotRegisteredUser() {
    return getMessageOrDefault("notRegisteredUser", "You are not registered.");
  }

  public String getUserAlreadyExists() {
    return getMessageOrDefault("userAlreadyExists", "You are already registered.");
  }

  public String getUserAlreadyLoggedIn() {
    return getMessageOrDefault("userAlreadyLoggedIn", "You are already logged in.");
  }

  public String getWrongPassword() {
    return getMessageOrDefault("wrongPassword", "Wrong password.");
  }

  public String getSuccessfulLogin() {
    return getMessageOrDefault("successfulLogin", "Successfully logged in.");
  }

  public String getLoginUsage() {
    return getMessageOrDefault("loginUsage", "Usage: /login <password>");
  }

  public String getInvalidPassword() {
    return getMessageOrDefault("invalidPassword", "Invalid password.");
  }

  public String getSuccessfulPasswordReset() {
    return getMessageOrDefault("successfulPasswordReset", "Password change successful.");
  }

  public String getPasswordChangeUsage() {
    return getMessageOrDefault("passwordChangeUsage", "Usage: /changepassword <old_password> <new_password> <new_password_again>");
  }

  public String getPasswordMismatch() {
    return getMessageOrDefault("passwordMismatch", "Password mismatch.");
  }

  public String getSuccessfulRegistration() {
    return getMessageOrDefault("successfulRegistration", "Successfully registered.");
  }

  public String getRegistrationUsage() {
    return getMessageOrDefault("registrationUsage", "Usage: /register <password> <password_again>");
  }

  public String getSuccessfulLogout() {
    return getMessageOrDefault("successfulLogout", "Successfully logged out.");
  }

  public String getPasswordIsTooLong(int maximumCharacters) {
    return getMessageOrDefault("passwordIsTooLong", "Password must not contain more than " + maximumCharacters + " characters.");
  }

  public String getPasswordIsTooShort(int minimumCharacters) {
    return getMessageOrDefault("passwordIsTooShort", "Password must contain at least " + minimumCharacters + " characters.");
  }

  public String getWhileUserIsNotLoggedIn() {
    return getMessageOrDefault("whileUserIsNotLoggedIn", "You are not logged. Use \"/login <password>\" command to login.");
  }
}
