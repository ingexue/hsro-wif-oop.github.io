package ch06;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.*;

class TreeMap<K extends Comparable<K>, V> implements Map<K, V> {

	class Entry<K extends Comparable<K>, V> implements Map.Entry<K, V>, Comparable<Entry<K, V>> {
		K key;
		V value;

		Entry<K, V> left, right;

		Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

		public V setValue(V value) {
			return this.value = value;
		}

		@Override
		public boolean equals(Object other) {
			if (other == null) return false;
			if (this == other) return true;
			if (!(other instanceof Pair)) return false;
			Entry that = (Entry) other;
			return this.key.equals(that.key);
		}

		@Override
		public int compareTo(Entry<K, V> other) {
			return this.key.compareTo(other.key);
		}

		@Override
		public int hashCode() {
			// hash of key is sufficient
			return key.hashCode();

//			Or more detailed:
//			HashCodeBuilder b = new HashCodeBuilder(17, 19);
//			b.append(key);
//			return b.hashCode();
		}
	}

	Entry<K, V> root;

	@Override
	public void put(K key, V value) {
		if (root == null) {
			root = new Entry<>(key, value);
			return;
		}

		Entry<K, V> it = root;
		while (it != null) {
			int c = it.key.compareTo(key);
			if (c == 0) {
				// update!
				it.value = value;
				return;
			} else if (c < 0) {
				if (it.left == null) {
					it.left = new Entry<>(key, value);
					return;
				} else {
					it = it.left;
				}
			} else {
				if (it.right == null) {
					it.right = new Entry<>(key, value);
					return;
				} else {
					it = it.right;
				}
			}
		}
	}

	@Override
	public V get(K key) {
		if (root == null)
			return null;

		Entry<K, V> it = root;
		while (it != null) {
			int c = it.key.compareTo(key);
			if (c == 0)
				return it.value;
			else if (c < 0)
				it = it.left;
			else
				it = it.right;
		}

		return null;
	}

	@Override
	public boolean containsKey(K key) {
		if (root == null)
			return false;

		Entry<K, V> it = root;
		while (it != null) {
			int c = it.key.compareTo(key);
			if (c == 0)
				return true;
			else if (c < 0)
				it = it.left;
			else
				it = it.right;
		}

		return false;
	}

	@Override
	public Set<K> keySet() {
		Set<K> keys = new TreeSet<>();
		List<Entry<K, V>> agenda = new LinkedList<>();

		if (root != null)
			agenda.add(root);

		while (agenda.size() > 0) {
			Entry<K, V> e = agenda.remove(0);

			keys.add(e.getKey());

			if (e.left != null)
				agenda.add(e.left);
			if (e.right != null)
				agenda.add(e.right);
		}

		return keys;
	}

	@Override
	public Collection<V> values() {
		List<V> values = new LinkedList<>();
		List<Entry<K, V>> agenda = new LinkedList<>();

		if (root != null)
			agenda.add(root);

		while (agenda.size() > 0) {
			Entry<K, V> e = agenda.remove(0);

			values.add(e.getValue());

			if (e.left != null)
				agenda.add(e.left);
			if (e.right != null)
				agenda.add(e.right);
		}

		return values;
	}

	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		Set<Map.Entry<K, V>> keys = new TreeSet<>();
		List<Entry<K, V>> agenda = new LinkedList<>();

		if (root != null)
			agenda.add(root);

		while (agenda.size() > 0) {
			Entry<K, V> e = agenda.remove(0);

			keys.add(e);

			if (e.left != null)
				agenda.add(e.left);
			if (e.right != null)
				agenda.add(e.right);
		}

		return keys;
	}
}
