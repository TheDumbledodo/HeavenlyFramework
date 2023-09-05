package dev.dumble.framework.nms.v1_8_R3.menu;

import dev.dumble.api.container.ContainerType;
import dev.dumble.common.exception.UnsupportedVersionException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WrapperContainerType {
	CHEST("minecraft:chest", "Chest", 27),
	CRAFTING("minecraft:crafting_table", "Dispenser", 9),
	FURNACE("minecraft:furnace", "Furnace", 3),

	DISPENSER("minecraft:dispenser", "Dispenser", 9),
	ENCHANTING("minecraft:enchanting_table", "Enchanting", 2),
	BREWING_STAND("minecraft:brewing_stand", "Brewing", 4),
	BEACON("minecraft:beacon", "container.beacon", 1),

	ANVIL("minecraft:anvil", "Repairing", 3),
	HOPPER("minecraft:hopper", "Item Hopper", 5),
	DROPPER("minecraft:dropper", "Dropper", 9);

	private final String minecraftId;
	private final String defaultTitle;

	private final Integer defaultSize;

	public static WrapperContainerType convert(ContainerType containerType) {
		try {
			return WrapperContainerType.valueOf(containerType.name());

		} catch (IllegalArgumentException exception) {
			throw new UnsupportedVersionException();
		}
	}
}