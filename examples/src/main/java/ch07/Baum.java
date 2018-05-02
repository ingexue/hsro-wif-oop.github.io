package ch07;

public class Baum<T extends Comparable<T>> {
	class Element {
		T value;
		Element left, right;
		Element(T value) { this.value = value; }
		int size() {
			return 1 +
					(left == null ? 0 : left.size()) +
					(right == null ? 0 : right.size());
		}
	}

	Element root;

	int size() {
		if (root == null) {
			return 0;
		} else {
			return root.size();
		}
	}

	/**
	 * Fügt ein Element in den Baum ein.
	 */
	void add(T value) {
		if (root == null) {
			root = new Element(value);
			return;
		}

		Element it = root;
		while (it != null) {
			int c = it.value.compareTo(value);

			if (c == 0)
				return;
			else if (c < 0) {
				if (it.left == null) {
					it.left = new Element(value);
					return;
				} else {
					it = it.left;
				}
			} else {
				if (it.right == null) {
					it.right = new Element(value);
					return;
				} else {
					it = it.right;
				}
			}
		}
	}
}
