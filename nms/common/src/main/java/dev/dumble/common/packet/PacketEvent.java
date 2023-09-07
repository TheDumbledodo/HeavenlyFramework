package dev.dumble.common.packet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

@Getter @Setter
@RequiredArgsConstructor
public class PacketEvent implements Cancellable {

	private final Object packet;
	private final Player player;

	private boolean cancelled;
}