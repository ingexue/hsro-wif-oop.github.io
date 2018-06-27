package ch12;

class ErzeugerVerbraucher {
	private int c = 0;
	private final int max;

	ErzeugerVerbraucher(int max) {
		this.max = max;
	}

	int getVerfuegbar() {
		return c;
	}

	synchronized void bereitstellen() throws InterruptedException {
		// solange warten, bis wieder was rein passt
		while (c >= max)
			wait();

		// jetzt passt wieder was rein!
		c = c + 1;

		// andere Threads aufwecken, die moeglicherweise warten
		notifyAll();
	}

	synchronized void abholen() throws InterruptedException {
		// solange nicht mind. 1 Element da ist, warten!
		while (c < 0)
			wait();

		// es gibt jetzt mind. 1
		c = c - 1;

		// andere Threads aufwecken, die moeglicherweise warten
		notifyAll();
	}
}
