package dev.dumble.heavenly.framework.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {

    String permission();
    String denyMessage() default "";

}
