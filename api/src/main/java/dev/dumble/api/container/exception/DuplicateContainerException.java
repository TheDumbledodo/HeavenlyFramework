package dev.dumble.api.container.exception;

public class DuplicateContainerException extends Error {

	public DuplicateContainerException() {
		super("A container with the same identifier already exists. Each container must have a unique identifier to prevent conflicts.");
	}
}
