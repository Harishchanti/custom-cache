package service;

import java.util.Objects;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Expirykey<K> implements Delayed {

    private K key;
    private long maxLifeTime;
    private long startTime;

    public Expirykey(K key, long maxLifetime) {
        this.key = key;
        this.maxLifeTime = maxLifetime;
        this.startTime = System.currentTimeMillis();

    }

    @Override
    public long getDelay(TimeUnit unit) {
        return getDelayInMiliSec();
    }

    public long getDelayInMiliSec() {
        return (startTime + maxLifeTime) - System.currentTimeMillis();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expirykey<?> expirykey = (Expirykey<?>) o;
        return maxLifeTime == expirykey.maxLifeTime && startTime == expirykey.startTime && Objects.equals(key, expirykey.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, maxLifeTime, startTime);
    }

    @Override
    public int compareTo(Delayed o) {
        return Long.compare(this.getDelayInMiliSec(), ((Expirykey) o).getDelayInMiliSec());
    }

}
