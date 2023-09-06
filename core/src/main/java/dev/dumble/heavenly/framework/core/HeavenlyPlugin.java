package dev.dumble.heavenly.framework.core;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class HeavenlyPlugin extends JavaPlugin {

	@Getter @Setter
	private static HeavenlyPlugin instance;

	@Override
	public final void onLoad() {
		HeavenlyPlugin.setInstance(this);

		this.loaded();
	}

	@Override
	public final void onEnable() {

		this.enabled();
	}

	@Override
	public final void onDisable() {

		this.disabled();
	}

	abstract void loaded();

	abstract void enabled();

	abstract void disabled();
}