package dev.dumble.framework.nms.v1_8_R3.menu;

import dev.dumble.api.HeavenlyMenuAPI;
import dev.dumble.framework.nms.v1_8_R3.packet.impl.MenuCreationNMSPacket;
import dev.dumble.api.container.Container;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collection;

public class ContainerAPI implements HeavenlyMenuAPI {

	@Override
	public void showMenu(Container container, Player player) {
		new MenuCreationNMSPacket(container).sendTo(player);
	}

	@Override
	public void showMenu(Container container, Player... players) {
		this.showMenu(container, Arrays.asList(players));
	}

	@Override
	public void showMenu(Container container, Collection<Player> players) {
		players.forEach(player -> this.showMenu(container, player));
	}
}
