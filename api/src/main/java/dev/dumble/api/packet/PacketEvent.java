package dev.dumble.api.packet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@RequiredArgsConstructor
public class PacketEvent extends Event implements Cancellable {

	@Getter
	private static final HandlerList handlers = new HandlerList();

	private final Object packet;
	private final Player player;

	private final PacketType type;

	@Setter
	private boolean cancelled;
}