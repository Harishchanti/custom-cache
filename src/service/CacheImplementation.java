package service;

import exception.ElementNotFoundException;

import java.util.concurrent.DelayQueue;

public class CacheImplementation<K, V> implements ICache<K, V> {

    private CacheMap cacheMap;
    private Strategy strategy;
    DelayQueue<Expirykey> delayQueue;

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setCacheMap(CacheMap cacheMap) {
        this.cacheMap = cacheMap;
    }

    public CacheImplementation(int capacity) {
        this.cacheMap = new CacheMap(capacity);
        this.strategy = Strategy.LRU;
        delayQueue = new DelayQueue<Expirykey>();
    }

    @Override
    public void put(K key, V value) {
        // need to evict elment if no elements > capasity
        //cleanUp();
        cacheMap.put(key, value);
        delayQueue.offer(new Expirykey(key, 10000));
    }

    public void cleanUp() {
        Expirykey<K> key = delayQueue.poll();
        while (key == null) {
            cacheMap.remove(key);
            key = delayQueue.poll();
        }
    }

    // Assusuming if value not present,then map returns null
    @Override
    public V get(Object key) throws ElementNotFoundException {
        Object value = cacheMap.get((K) key);
        if (value == null) throw new ElementNotFoundException("Key not present in cache");
        return (V) value;
    }

    @Override
    public int size() {
        return cacheMap.size();
    }

    @Override
    public V evit(Object key) throws ElementNotFoundException {
        Object value = cacheMap.remove((K) key);
        if (value == null) throw new ElementNotFoundException("Key not present in cache");
        return (V) value;
    }

}
