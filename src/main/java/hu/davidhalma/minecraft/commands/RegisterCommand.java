package hu.davidhalma.minecraft.commands;


import dev.alangomes.springspigot.context.Context;
import hu.davidhalma.minecraft.AuthenticationPlugin;
import hu.davidhalma.minecraft.config.Messages;
import hu.davidhalma.minecraft.exception.NotRegisteredUser;
import hu.davidhalma.minecraft.exception.UserAlreadyExists;
import hu.davidhalma.minecraft.exception.UserAlreadyLoggedIn;
import hu.davidhalma.minecraft.exception.WrongPassword;
import hu.davidhalma.minecraft.service.Authentication;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.Parameters;

@Log4j2
@Component
@CommandLine.Command(name = "register")
public class RegisterCommand implements Runnable {

  @SuppressWarnings("unused")
  @Parameters(paramLabel = "PASSWORD", description = "password", arity = "0..2")
  private String[] passwords;

  private final JavaPlugin plugin = JavaPlugin.getPlugin(AuthenticationPlugin.class);
  private final Context context;
  private final Authentication authentication;
  private final Messages messages;

  public RegisterCommand(Context context, Authentication authentication, Messages messages) {
    this.context = context;
    this.authentication = authentication;
    this.messages = messages;
  }

  @Override
  public void run() {
    Player player = context.getPlayer();
    if (!isValid(player)) {
      return;
    }
    try {
      authentication.register(player, passwords[0]);
      player.sendMessage(messages.getSuccessfulRegistration());
    } catch (NotRegisteredUser | WrongPassword | UserAlreadyExists | UserAlreadyLoggedIn exception) {
      player.sendMessage(exception.getMessage());
    }
  }

  private boolean isValid(Player player) {
    if (passwords == null || passwords.length != 2){
      player.sendMessage(messages.getRegistrationUsage());
      return false;
    }
    for (String password :
        passwords) {
      if (StringUtils.isBlank(password)){
        player.sendMessage(messages.getInvalidPassword());
        return false;
      }
    }
    if (!passwords[0].equals(passwords[1])) {
      player.sendMessage(messages.getPasswordMismatch());
      return false;
    }
    int minPasswordLength = plugin.getConfig().getInt("authentication.password.length.min", 6);
    int maxPasswordLength = plugin.getConfig().getInt("authentication.password.length.max", 64);
    if (passwords[0].length() > maxPasswordLength){
      player.sendMessage(messages.getPasswordIsTooLong(maxPasswordLength));
      return false;
    }
    if (passwords[0].length() < minPasswordLength){
      player.sendMessage(messages.getPasswordIsTooShort(minPasswordLength));
      return false;
    }
    return true;
  }
}
