package dev.dumble.api;

import dev.dumble.api.container.Container;
import org.bukkit.entity.Player;

import java.util.Collection;

/**
 * Main entry point for accessing the Heavenly Menu API.
 *
 * @since ${projectVersion}
 */
public interface HeavenlyMenuAPI {

	/**
	 * Displays the specified menu container to a single player.
	 *
	 * @param container the menu container to be displayed
	 * @param player the player to whom the menu will be shown
	 * @since 1.0.0-alpha
	 */
	void showMenu(Container container, Player player);

	/**
	 * Displays the specified menu container to a collection of players.
	 *
	 * @param container the menu container to be displayed
	 * @param players a collection of players to whom the menu will be shown
	 * @since 1.0.0-alpha
	 */
	void showMenu(Container container, Collection<Player> players);

	/**
	 * Displays the specified menu container to multiple players.
	 *
	 * @param container the menu container to be displayed
	 * @param players an array of players to whom the menu will be shown
	 * @since 1.0.0-alpha
	 */
	void showMenu(Container container, Player... players);
}
