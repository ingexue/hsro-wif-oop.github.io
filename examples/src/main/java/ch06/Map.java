package ch06;

import java.util.Collection;
import java.util.Set;

interface Map<K, V> {
	void put(K key, V value);
	V get(K key);
	boolean containsKey(K key);

	// inneres Interface
	interface Entry<K, V> {
		K getKey();
		V getValue();
		V setValue(V value);
	}

	Set<K> keySet();
	Collection<V> values();
	Set<Map.Entry<K, V>> entrySet();
}
