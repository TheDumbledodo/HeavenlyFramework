package dev.dumble.heavenly.framework.core;

import dev.dumble.common.NMSManager;
import dev.dumble.heavenly.framework.core.command.CommandManager;
import dev.dumble.heavenly.framework.core.exception.OutdatedVersionException;
import dev.dumble.heavenly.framework.core.exception.PluginEnableException;
import dev.dumble.heavenly.framework.core.exception.UnknownVersionException;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter @Setter
public abstract class HeavenlyPlugin extends JavaPlugin {

	@Getter @Setter
	private static HeavenlyPlugin instance;

	private NMSManager nmsManager;

	@Override
	public final void onLoad() {
		HeavenlyPlugin.setInstance(this);

		try {
			this.setNmsManager(NMSVersion.getCurrent().createNMSManager());

		} catch (UnknownVersionException exception) {
			throw new PluginEnableException("Heavenly Framework supports Spigot from 1.8 to 1.20");

		} catch (OutdatedVersionException exception) {
			throw new PluginEnableException("Heavenly Framework supports " + exception.getMinimumSupportedVersion() + " and above.");

		} catch (Throwable throwable) {
			throw new PluginEnableException(throwable, "Couldn't initialize the NMS manager.");
		}

		this.loaded();
	}

	@Override
	public final void onEnable() {
		CommandManager.bootstrap(this);

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