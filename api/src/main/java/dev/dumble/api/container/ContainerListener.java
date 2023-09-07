package dev.dumble.api.container;

import org.bukkit.entity.Player;

import static dev.dumble.api.container.Container.DataHolder;

public abstract class ContainerListener {

	/**
	 * Listens for clicks for the container
	 *
	 * @param player the player who clicked
	 * @param dataHolder the data stored in the clicked slot
	 * @since ${projectVersion}
	 */
	public abstract void onClickListener(Player player, DataHolder dataHolder);
}
