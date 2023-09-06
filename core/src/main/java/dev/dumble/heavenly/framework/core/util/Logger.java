package dev.dumble.heavenly.framework.core.util;

import dev.dumble.heavenly.framework.core.HeavenlyPlugin;
import lombok.experimental.UtilityClass;

import java.util.logging.Level;

@UtilityClass
public class Logger {

    public void info(String msg) {
        Logger.info(msg, null);
    }

    public void info(String msg, Throwable thrown) {
        Logger.log(Level.INFO, msg, thrown);
    }

    public void warning(String msg) {
        Logger.warning(msg, null);
    }

    public void warning(String msg, Throwable thrown) {
        Logger.log(Level.WARNING, msg, thrown);
    }

    public void severe(String msg) {
        Logger.severe(msg, null);
    }

    public void severe(String msg, Throwable thrown) {
        Logger.log(Level.SEVERE, msg, thrown);
    }

    private void log(Level level, String msg, Throwable thrown) {
        HeavenlyPlugin.getInstance().getLogger().log(level, msg, thrown);
    }
}
