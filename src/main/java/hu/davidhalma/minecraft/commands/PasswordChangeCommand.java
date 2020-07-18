package hu.davidhalma.minecraft.commands;

import dev.alangomes.springspigot.context.Context;
import hu.davidhalma.minecraft.config.Messages;
import hu.davidhalma.minecraft.exception.NotRegisteredUser;
import hu.davidhalma.minecraft.exception.WrongPassword;
import hu.davidhalma.minecraft.service.Authentication;
import lombok.extern.log4j.Log4j2;
import org.bukkit.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.Parameters;

@Log4j2
@Component
@CommandLine.Command(name = "changepassword")
public class PasswordChangeCommand implements Runnable {

  @SuppressWarnings("unused")
  @Parameters(paramLabel = "PASSWORD", description = "password", arity = "0..3")
  private String[] passwords;

  private final Context context;
  private final Authentication authentication;
  private final Messages messages;

  @Autowired
  public PasswordChangeCommand(Context context, Authentication authentication, Messages messages) {
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
      authentication.passwordReset(player.getUniqueId().toString(), passwords[0], passwords[1]);
      player.sendMessage(messages.getSuccessfulPasswordReset());
    } catch (WrongPassword | NotRegisteredUser exception) {
      player.sendMessage(exception.getMessage());
    }
  }

  private boolean isValid(Player player) {
    if (passwords == null || passwords.length < 3){
      player.sendMessage(messages.getPasswordChangeUsage());
      return false;
    }
    for (String password :
        passwords) {
        if (password == null || "".equals(password)){
          player.sendMessage(messages.getInvalidPassword());
          return false;
        }
    }
    if (!passwords[1].equals(passwords[2])) {
      player.sendMessage(messages.getPasswordMismatch());
      return false;
    }
    return true;
  }
}
