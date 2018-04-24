package ch06;

class Pair<K extends Comparable<K>, V> implements Comparable<Pair<K, V>> {
	K key;
	V value;

	public boolean equals(Object other) {
		if (other == null)
			return false;

		if (this == other)
			return true;

		if (!(other instanceof Pair))
			return false;

		Pair that = (Pair) other;
		return this.key.equals(that.key);
	}

	public int compareTo(Pair<K, V> other) {
		return this.key.compareTo(other.key);
	}
}