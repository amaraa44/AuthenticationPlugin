package hu.davidhalma.minecraft.commands;

import dev.alangomes.springspigot.context.Context;
import hu.davidhalma.minecraft.config.Messages;
import hu.davidhalma.minecraft.service.Authentication;
import lombok.extern.log4j.Log4j2;
import org.bukkit.entity.Player;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Log4j2
@Component
@CommandLine.Command(name = "logout")
public class LogoutCommand implements Runnable {

  private final Context context;
  private final Authentication authentication;
  private final Messages messages;

  public LogoutCommand(Context context, Authentication authentication, Messages messages) {
    this.context = context;
    this.authentication = authentication;
    this.messages = messages;
  }

  @Override
  public void run() {
    Player player = context.getPlayer();
    authentication.logout(player.getUniqueId().toString());
    player.sendMessage(messages.getSuccessfulLogout());
  }
}
