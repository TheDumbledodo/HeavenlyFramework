package dev.dumble.heavenly.framework.core.expiring;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ExpiringSet<E> extends HashSet<E> {

    private final Map<E, ScheduledFuture<?>> expiringMap = new HashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private final long expirations;
    private final TimeUnit timeUnit;

    public ExpiringSet(long expirations, TimeUnit timeUnit) {
        this.expirations = expirations;
        this.timeUnit = timeUnit;
    }

    public boolean add(E element) {
        return this.add(element, () -> {});
    }

    public boolean add(E element, Runnable onExpire) {
        if (super.add(element)) {
            final ScheduledFuture<?> future = this.scheduler.schedule(() -> {
                super.remove(element);
                this.expiringMap.remove(element);
                onExpire.run();
            }, this.expirations, this.timeUnit);
            this.expiringMap.put(element, future);
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object element) {
        final ScheduledFuture<?> future = this.expiringMap.remove(element);
        if (future != null) {
            future.cancel(false);
        }
        return super.remove(element);
    }
}