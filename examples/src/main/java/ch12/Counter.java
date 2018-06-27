package ch12;

class Counter {
	private int c = 0;
	int getCount() {
		return c;
	}
	void increment() {
		c = c + 1;
	}
	void incrementA() {
		// Variante A: synchronized Block, Schluessel ist `this`
		synchronized (this) {
			c = c + 1;
		}
	}
	synchronized void incrementB() {
		// Variante B: synchronized Methode, Schluessel ist implizit `this`
		c = c + 1;
	}
}
