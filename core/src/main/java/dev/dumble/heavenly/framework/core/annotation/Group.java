package dev.dumble.heavenly.framework.core.annotation;

import dev.dumble.heavenly.framework.core.command.HeavenlyCommandGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Group {
    Class<? extends HeavenlyCommandGroup>[] subCommands();
}
