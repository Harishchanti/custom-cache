package service;

import exception.ElementNotFoundException;

public interface ICache<K, V> {

    void put(K key, V value);

    V get(K key) throws ElementNotFoundException;

    int size();

    V evit(K key) throws ElementNotFoundException;

}
