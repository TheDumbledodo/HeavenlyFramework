package dev.dumble.heavenly.framework.core.listener;

import dev.dumble.heavenly.framework.core.HeavenlyPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		HeavenlyPlugin.getInstance()
				.getNmsManager()
				.injectPacketListener(event.getPlayer());
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		HeavenlyPlugin.getInstance()
				.getNmsManager()
				.uninjectPacketListener(event.getPlayer());
	}
}
