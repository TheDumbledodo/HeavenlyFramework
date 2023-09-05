package dev.dumble.api.container;

import org.bukkit.entity.Player;

import static dev.dumble.api.container.Container.*;

@FunctionalInterface
public interface ContainerListener {

	void onWindowInteraction(Player player, DataHolder dataHolder);
}
