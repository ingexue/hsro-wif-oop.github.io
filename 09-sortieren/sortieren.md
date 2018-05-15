---
title: "Sortieren"
permalink: /09-sortieren/
mathjax: true
---

# Sortieren

Sortieren ist ein Vorgang, bei dem man durch **Vergleich** und etwaigem **Tausch** von Elementen eine Ordnung herstellt, bei der das _kleinste_ Element an erster Stelle steht.
Wie sortiert man also?
Wir wählen der Anschaulichkeit halber zunächst noch `int`-Felder (`int[]`) und sprechen gegen Ende dieses Kapitels noch über Generics, Bounds und Comparatoren.


# Vergleichen und Tauschen

Der Vergleich von zwei `int` Werten ist trivial, der `<`-Operator wird ja unterstützt.
Interessanter wird es beim Tausch; hier muss sich einer Hilfsvariablen bedient werden:

```java
class Sortieren {
	static void swap(int[] a, int i, int j) {
		int hilf = a[i];
		a[i] = a[j];
		a[j] = hilf;
	}
}
```


# Sortieren durch Auswählen

Eines der wohl einfachsten wie anschaulichen Sortierverfahren ist das Sortieren durch Auswählen.
Bei diesem _in-place_-Verfahren betrachtet man das zu sortierende Array als zwei Teilarrays: ein sortierter Teil (von links) sowie ein unsortierter Teil (von rechts).
Man sucht nun der Reihe nach immer das nächst kleinste Element im _unsortierten_ Teil, um es hinten an den _sortierten_ Teil anzuhängen.


```java
class Sortieren {
	// ...
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
}
```

# Sortieren durch Einfügen

Verwandt, aber etwas kniffliger zu realisieren ist das Sortieren durch Einfügen.
Hierbei unterscheidet man ebenso zwischen dem bereits sortierten Teil sowie den unsortierten Teil.




![insertion sort]({{site.baseurl}}/09-sortieren/insertion-sort.gif)

By Swfung8 - Own work, CC BY-SA 3.0, https://commons.wikimedia.org/w/index.php?curid=14961606

```java
class Sortieren {
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
}
```

# Merge

```java
class Sortieren {
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
}
```


# Quicksort



```java
class Sortieren {
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
}
```

> Hinweis: Quicksort ist nicht klausurrelevant.


https://visualgo.net/de/sorting

# Comparable und Comparator

Andere Ordnung, -1 ==> Semantik
Comparator.comparingInt


