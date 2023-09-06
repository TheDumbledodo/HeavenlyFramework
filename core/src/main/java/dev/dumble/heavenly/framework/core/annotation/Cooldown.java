package dev.dumble.heavenly.framework.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.TimeUnit;

@Retention(RetentionPolicy.RUNTIME)
public @interface Cooldown {

    long duration() default -1;
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

    String bypassPermission() default "";

    boolean onCommand() default true;
    boolean clearOnQuit() default true;

}
