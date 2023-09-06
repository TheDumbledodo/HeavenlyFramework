package dev.dumble.heavenly.framework.core.exception;

public class PluginEnableException extends Error {

	public PluginEnableException(String message) {
		super(message);
	}

	public PluginEnableException(Throwable throwable, String message) {
		super(message, throwable);
	}
}
