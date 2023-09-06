package dev.dumble.heavenly.framework.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OutdatedVersionException extends Exception {

	private final String minimumSupportedVersion;
}