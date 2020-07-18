package hu.davidhalma.minecraft.events;

import hu.davidhalma.minecraft.AuthenticationPlugin;
import hu.davidhalma.minecraft.config.Messages;
import hu.davidhalma.minecraft.service.Authentication;
import lombok.extern.log4j.Log4j2;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class EventListeners implements Listener {

  private final Authentication authentication;
  private final Messages messages;

  public EventListeners(Authentication authentication, Messages messages) {
    this.authentication = authentication;
    this.messages = messages;
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    log.info(player.getDisplayName() + " joined the server...");
    printScheduledLoginUsage(player);
  }

  private void printScheduledLoginUsage(Player player) {
    new BukkitRunnable() {
      @Override
      public void run() {
        if (authentication.loggedIn(player.getUniqueId().toString())){
          cancel();
        }
        player.sendMessage(messages.getWhileUserIsNotLoggedIn());
      }
    }.runTaskTimerAsynchronously(JavaPlugin.getProvidingPlugin(AuthenticationPlugin.class), 0, 320);

  }

  @EventHandler(ignoreCancelled = true)
  public void onPlayerMove(PlayerMoveEvent event) {
    cancelIfNotLoggedIn(event, event.getPlayer().getUniqueId().toString());
  }

  @EventHandler(ignoreCancelled = true)
  public void onBlockDamage(BlockDamageEvent event) {
    cancelIfNotLoggedIn(event, event.getPlayer().getUniqueId().toString());
  }

  @EventHandler(ignoreCancelled = true)
  public void onBlockBreak(BlockBreakEvent event) {
    cancelIfNotLoggedIn(event, event.getPlayer().getUniqueId().toString());
  }

  @EventHandler(ignoreCancelled = true)
  public void onPlayerDropItem(PlayerDropItemEvent event) {
    cancelIfNotLoggedIn(event, event.getPlayer().getUniqueId().toString());
  }

  private void cancelIfNotLoggedIn(Cancellable event, String uuid) {
    if (!authentication.loggedIn(uuid)){
      event.setCancelled(true);
    }
  }

  @EventHandler(ignoreCancelled = true)
  public void onPlayerQuit(PlayerQuitEvent event) {
    authentication.logout(event.getPlayer().getUniqueId().toString());
    log.info(event.getPlayer().getDisplayName() + " left the server...");
  }
}
