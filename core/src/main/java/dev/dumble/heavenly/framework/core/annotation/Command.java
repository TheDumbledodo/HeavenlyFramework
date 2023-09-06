package dev.dumble.heavenly.framework.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

    String command();
    String[] aliases() default {};

    String description() default "";
    String usage() default "";

    int minimumArguments() default -1;

    Permission permission() default @Permission(permission = "");
    Cooldown cooldown() default @Cooldown;
    Group group() default @Group(subCommands = {});
}
