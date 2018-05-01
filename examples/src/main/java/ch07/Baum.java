package ch07;

public class Baum<T extends Comparable<T>> {
	class Element {
		T value;
		Element left, right;
		Element(T value) { this.value = value; }
		void add(T value) {
			int c = value.compareTo(this.value);
			if (c == 0)
				return;
			else if (c < 0) {
				if (left == null)
					left = new Element(value);  // Terminalfall!
				else
					left.add(value);  // Rekursursionsfall
			} else {
				if (right == null)
					right = new Element(value);  // Dito.
				else
					right.add(value);
			}
		}
	}

	Element root;

	void add(T obj) {
		if (root == null) {
			root = new Element(obj);
			return;
		} else {
			root.add(obj);
		}
	}
}
