package dev.dumble.api.container;

import dev.dumble.api.container.exception.DuplicateContainerException;
import lombok.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@Setter @Getter
@RequiredArgsConstructor
public class Container {

	@Getter
	public static Set<Container> containers = new LinkedHashSet<>();

	public ContainerListener containerListener;

	public int windowId;

	@NonNull private String title;
	@NonNull private ContainerType containerType;

	@NonNull private Integer size;
	@NonNull private Integer refreshTicks;

	@NonNull private Map<ItemStack, Integer> contents;

	public static Container getContainer(int windowId) {
		return Container.getContainers()
				.stream()
				.filter(container -> container.getWindowId() == windowId).findFirst()
				.orElse(null);
	}

	public void onClickListener(ContainerListener containerListener) {
		this.containerListener = containerListener;
	}

	public void callContainerInteraction(Player player, DataHolder data) {
		if (containerListener == null) return;

		containerListener.onWindowInteraction(player, data);
	}

	public static ContainerBuilder builder() {
		return new ContainerBuilderImpl();
	}

	public static class ContainerBuilderImpl implements ContainerBuilder {

		private String title;
		private ContainerType containerType;

		private Integer size;
		private Integer refreshTicks;

		private final Map<ItemStack, Integer> contents = new HashMap<>();

		@Override
		public ContainerBuilderImpl setTitle(String title) {
			this.title = title;
			return this;
		}

		@Override
		public ContainerBuilderImpl setSize(Integer size) {
			this.size = size;
			return this;
		}

		@Override
		public ContainerBuilderImpl setType(ContainerType containerType) {
			this.containerType = containerType;
			return this;
		}

		@Override
		public ContainerBuilderImpl setContents(List<ItemStack> itemStacks) {
			int slot = 0;
			for (ItemStack itemStack : itemStacks) this.contents.put(itemStack, slot++);
			return this;
		}

		@Override
		public ContainerBuilderImpl setContents(ItemStack... itemStacks) {
			return this.setContents(Arrays.asList(itemStacks));
		}

		@Override
		public ContainerBuilderImpl setItem(ItemStack itemStack, int slot) {
			this.contents.put(itemStack, slot);
			return this;
		}

		@Override
		public ContainerBuilderImpl setRefreshTicks(Integer ticks) {
			this.refreshTicks = ticks;
			return this;
		}

		@Override
		public Container build() {
			final Container container = new Container(title, containerType, size, refreshTicks, contents);
			final Set<Container> containerSet = Container.getContainers();

			if (containerSet.contains(container))
				throw new DuplicateContainerException();

			containerSet.add(container);
			return container;
		}
	}

	@ToString @Getter @Setter
	@Builder(setterPrefix = "set")
	public static class DataHolder {

		private int windowId;
		private int slot;

		private int button;
		private short actionNumber;

		private ItemStack item;
	}

	@Override
	public int hashCode() {
		return Objects.hash(windowId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof Container)) return false;

		return ((Container) obj).getWindowId() == windowId;
	}
}
