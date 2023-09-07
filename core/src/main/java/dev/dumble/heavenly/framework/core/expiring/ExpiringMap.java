package dev.dumble.heavenly.framework.core.expiring;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ExpiringMap<K, V> extends HashMap<K, V> {

    private final Map<K, ScheduledFuture<?>> expiringMap = new HashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private final long expirations;
    private final TimeUnit timeUnit;

    public ExpiringMap(long expirations, TimeUnit timeUnit) {
        this.expirations = expirations;
        this.timeUnit = timeUnit;
    }

    public V put(K key, V value) {
        return this.put(key, value, () -> {});
    }

    public V put(K key, V value, Runnable onExpire) {
        V oldValue = super.put(key, value);
        if (oldValue != null) {
            final ScheduledFuture<?> oldFuture = this.expiringMap.remove(key);
            if (oldFuture != null)
                oldFuture.cancel(false);
        }

        ScheduledFuture<?> future = this.scheduler.schedule(() -> {
            super.remove(key);
            this.expiringMap.remove(key);
            onExpire.run();
        }, this.expirations, this.timeUnit);

        this.expiringMap.put(key, future);

        return oldValue;
    }

    @Override
    public V remove(Object key) {
        final ScheduledFuture<?> future = this.expiringMap.remove(key);

        if (future != null)
            future.cancel(false);

        return super.remove(key);
    }
}