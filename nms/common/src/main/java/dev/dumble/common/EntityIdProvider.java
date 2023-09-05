package dev.dumble.common;

import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class EntityIdProvider {

	private final Random random = new Random();

	public Integer generate() {
		return random.nextInt();
	}
}