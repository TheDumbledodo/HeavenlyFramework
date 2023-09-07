package dev.dumble.common.util;

import lombok.SneakyThrows;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.RegisteredListener;

import javax.annotation.Nonnull;

public class BukkitHelper {

	@SneakyThrows
	public static void callEvent(@Nonnull Event event) {

		final HandlerList handlers = event.getHandlers();
		final RegisteredListener[] listeners = handlers.getRegisteredListeners();

		for (RegisteredListener registration : listeners) registration.callEvent(event);
	}
}
