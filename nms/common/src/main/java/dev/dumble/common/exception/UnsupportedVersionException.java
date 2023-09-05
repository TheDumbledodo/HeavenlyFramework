package dev.dumble.common.exception;

public class UnsupportedVersionException extends Error {

	public UnsupportedVersionException() {
		super("The servers version does not support that object");
	}
}
