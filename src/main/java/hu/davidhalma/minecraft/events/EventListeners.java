package hu.davidhalma.minecraft.events;

import hu.davidhalma.minecraft.service.Authentication;
import lombok.extern.log4j.Log4j2;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class EventListeners implements Listener {

  private final Authentication authentication;

  public EventListeners(Authentication authentication) {
    this.authentication = authentication;
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    log.info(event.getPlayer().getDisplayName() + " joined the server...");
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
