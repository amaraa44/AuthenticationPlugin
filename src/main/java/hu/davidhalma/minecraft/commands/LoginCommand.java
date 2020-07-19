package hu.davidhalma.minecraft.commands;

import dev.alangomes.springspigot.context.Context;
import hu.davidhalma.minecraft.config.Messages;
import hu.davidhalma.minecraft.exception.NotRegisteredUser;
import hu.davidhalma.minecraft.exception.UserAlreadyLoggedIn;
import hu.davidhalma.minecraft.exception.WrongPassword;
import hu.davidhalma.minecraft.service.Authentication;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.Parameters;

@Log4j2
@Component
@CommandLine.Command(name = "login")
public class LoginCommand implements Runnable {

  @SuppressWarnings("unused")
  @Parameters(paramLabel = "PASSWORD", description = "password", arity = "0..1")
  private String[] password;

  private final Context context;
  private final Authentication authentication;
  private final Messages messages;

  @Autowired
  public LoginCommand(Context context, Authentication authentication, Messages messages) {
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
      authentication.login(player.getUniqueId().toString(), password[0]);

      String message = messages.getSuccessfulLogin();
      player.sendMessage(message);
    } catch (WrongPassword | NotRegisteredUser | UserAlreadyLoggedIn exception) {
      player.sendMessage(exception.getMessage());
    }
  }

  private boolean isValid(Player player) {
    if (password == null || password.length != 1){
      player.sendMessage(messages.getLoginUsage());
      return false;
    }
    if (password[0] == null || StringUtils.isBlank(password[0])){
      player.sendMessage(messages.getInvalidPassword());
      return false;
    }
    return true;
  }
}
