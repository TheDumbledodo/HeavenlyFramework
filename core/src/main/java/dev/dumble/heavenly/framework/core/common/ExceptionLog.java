package dev.dumble.heavenly.framework.core.common;

import com.google.common.collect.ImmutableList;
import lombok.Getter;

@Getter
public class ExceptionLog {
    private final ImmutableList<String> message;
    private final Throwable cause;

    protected ExceptionLog(Throwable cause, String[] message) {
        this.message = ImmutableList.copyOf(message);
        this.cause = cause;
    }
}
