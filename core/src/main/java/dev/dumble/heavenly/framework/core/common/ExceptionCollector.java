package dev.dumble.heavenly.framework.core.common;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class ExceptionCollector {

	protected final List<ExceptionLog> errors = new ArrayList<>();

	public void add(String... messageParts) {
		this.add(null, messageParts);
	}

	public void add(Throwable cause, String... messageParts) {
		this.getErrors().add(new ExceptionLog(cause, messageParts));
	}

	public boolean hasErrors() {
		return this.getErrors().size() > 0;
	}

	public int getErrorsCount() {
		return this.getErrors().size();
	}

	public abstract void logToConsole();
}
