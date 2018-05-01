package ch07;

class Liste<T> {
	Element first;

	public int size() {
		if (first == null)  // Terminalfall 1
			return 0;
		else
			return first.size();  // Hilfsmethode!
	}

	public void add(T obj) {
		if (first == null) {
			first = new Element(obj);
			return;
		}

		Element it = first;
		while (it != null) {
			if (it.next == null) {
				it.next = new Element(obj);
				return;
			} else {
				it = it.next;
			}
		}
	}

	class Element {
		T value;
		Element next;
		Element(T obj) { this.value = obj; }
		int size() {
			if (next == null)  // Terminalfall 3a
				return 1;
			else
				return 1 + next.size();
		}
	}

	public static void main(String[] args) {
		Liste<Integer> li = new Liste<>();

		System.out.println(li.size());
		li.add(1);
		li.add(1);
		li.add(1);

		System.out.println(li.size());
	}
}
