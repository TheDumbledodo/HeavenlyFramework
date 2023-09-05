package dev.dumble.api.container;

import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * A builder for creating a Minecraft Container.
 *
 * @since ${projectVersion}
 */
public interface ContainerBuilder {

	/**
	 * Sets the title of the container.
	 *
	 * @param title the title of the container, using colors is supported
	 * @return the ContainerBuilder instance for method chaining
	 * @since ${projectVersion}
	 */
	ContainerBuilder setTitle(String title);

	/**
	 * Sets the size of the container.
	 *
	 * @param size the size of the container, representing the number of slots it contains
	 * @return the ContainerBuilder instance for method chaining
	 * @since ${projectVersion}
	 */
	ContainerBuilder setSize(Integer size);

	/**
	 * Sets the type of the container container.
	 *
	 * @param containerType the type of the container container
	 * @return the ContainerBuilder instance for method chaining
	 * @since ${projectVersion}
	 */
	ContainerBuilder setType(ContainerType containerType);

	/**
	 * Sets the item stacks that populate the container.
	 *
	 * @param itemStacks a list of ItemStack objects to be displayed in the container
	 * @return the ContainerBuilder instance for method chaining
	 * @since ${projectVersion}
	 */
	ContainerBuilder setContents(List<ItemStack> itemStacks);

	/**
	 * Sets the item stacks that populate the container.
	 *
	 * @param itemStacks an array of ItemStack objects to be displayed in the container
	 * @return the ContainerBuilder instance for method chaining
	 * @since ${projectVersion}
	 */
	ContainerBuilder setContents(ItemStack... itemStacks);

	/**
	 * Sets an item stack at a specific slot in the container.
	 *
	 * @param itemStack the ItemStack to be displayed in the container slot
	 * @param slot the slot index where the ItemStack should be placed
	 * @return the ContainerBuilder instance for method chaining
	 * @since ${projectVersion}
	 */
	ContainerBuilder setItem(ItemStack itemStack, int slot);

	/**
	 * Sets the refresh rate of the container.
	 *
	 * @param ticks the number of ticks after which the container should be refreshed
	 * @return the ContainerBuilder instance for method chaining
	 * @since ${projectVersion}
	 */
	ContainerBuilder setRefreshTicks(Integer ticks);

	/**
	 * Builds and returns the constructed container.
	 *
	 * @return the created Container instance
	 * @since ${projectVersion}
	 */
	Container build();
}
