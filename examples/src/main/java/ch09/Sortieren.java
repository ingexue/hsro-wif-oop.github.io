package ch09;

import java.util.Arrays;

public class Sortieren {
	static void swap(int[] a, int i, int j) {
		int h = a[i];
		a[i] = a[j];
		a[j] = h;
	}

	static void isort(int[] a) {
		for (int i = 1; i < a.length; i++) {
			// aktuellen Wert sichern
			int x = a[i];

			// alle Elemente eins nach rechts ruecken
			// bis einfuegeposition gefunden
			int j = i-1;
			while (j >= 0 && a[j] > x) {
				swap(a, j, j + 1);
				j--;
			}

			// an der "freien" Stelle einfuegen
			a[j+1] = x;
		}
	}

	static void ssort(int[] a) {
		for (int i = 0; i < a.length; i++) {
			// Position des aktuellen Minimums
			int p = i;

			// nach kleinerem Wert suchen, Position merken
			for (int j = i+1; j < a.length; j++)
				if (a[j] < a[p])
					p = j;

			// tauschen, wenn noetig
			if  (i != p)
				swap(a, i, p);
		}
	}

	static int[] msort(int[] a) {
		if (a.length < 2)
			return a;

		int p = a.length / 2;

		// Teillisten sortieren
		int[] l = msort(Arrays.copyOfRange(a, 0, p));
		int[] r = msort(Arrays.copyOfRange(a, p, a.length));

		// sortierte Teillisten zusammenfuehren
		return merge(l, r);
	}

	private static int[] merge(int[] a, int[] b) {
		// neues Array so gross wie beide zusammen
		int[] res = new int [a.length + b.length];

		// drei Indices: in res, a und b
		int i = 0, l = 0, r = 0;
		while (l < a.length && r < b.length) {
			if (a[l] < b[r])
				res[i++] = a[l++];
			else
				res[i++] = b[r++];
		}

		// links oder rechts noch was ueber?
		while (l < a.length)
			res[i++] = a[l++];
		while (r < b.length)
			res[i++] = b[r++];

		return res;
	}

	static void qsort(int[] a) {
		qsort(a, 0, a.length);
	}

	private static void qsort(int[] a, int from, int to) {
		// kurze Arrays bereits sortiert
		if (to - from < 2)
			return;

		int p = partition(a, from, to);
		if (from < p - 1)
			qsort(a, from, p);
		if (p < to)
			qsort(a, p, to);
	}

	private static int partition(int[] a, int from, int to) {
		int p = from + (to-from)/2;
		to--;
		while (from <= to) {
			// von links weiter einruecken
			while (a[from] < a[p])
				from++;

			// ebenso von rechts
			while (a[to] > a[p])
				to--;

			// tauschen
			if (from <= to) {
				swap(a, from++, to--);
			}
		}

		// pivot wurde oben auf "die Mitte" gesetzt
		return p;
	}
}
