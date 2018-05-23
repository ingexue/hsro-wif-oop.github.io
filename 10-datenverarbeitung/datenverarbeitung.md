---
title: "Datenverarbeitung"
permalink: /10-datenverarbeitung/
mathjax: true
---

# Datenverarbeitung

## Arbeiten mit Datenstrukturen

Wir haben in den vergangenen Wochen die wichtigsten Datenstrukturen und Sortieralgorithmen kennengelernt.
Die Beispiele dazu waren aber meistens knapp und eher abstrakt -- in den wenigsten Fällen aber wirklich anschaulich.

Heute wollen wir üben, das Gelernte praxisnah anzuwenden, in dem wir exemplarisch einige typische Problemstellungen in der Datenverarbeitung durchgehen.
Als Datenbasis nehmen wir die Bundesligaergebnisse der Saison 2017/2018 (Quelle: [fussballdaten.de](https://www.fussballdaten.de), [Spiele](https://github.com/hsro-wif-prg2/hsro-wif-prg2.github.io/blob/master/examples/src/main/resources/bundesliga_Spiel.csv), [Vereine](https://github.com/hsro-wif-prg2/hsro-wif-prg2.github.io/blob/master/examples/src/main/resources/bundesliga_Verein.csv)).
Es liegen Daten aus der 1. (18 Vereine), 2. (18 Vereine) und 3. Bundesliga (20 Vereine) vor, aus der 1. und 2. Liga bis einschließlich des 32. Spieltags und aus der 3. Liga bis einschließlich des 36. Spieltags.

![Datenmodell]({{site.baseurl}}/10-datenverarbeitung/datenmodell.svg)

| Spiel\_ID | Spieltag | Datum | Uhrzeit | Heim | Gast | Tore\_Heim | Tore\_Gast |
|-----------|----------|-------|---------|------|------|------------|------------|
| 1 | 1 | 2017-08-18 | 20:30:00 | 1 | 5 | 3 | 1 |
| 2 | 1 | 2017-08-19 | 15:30:00 | 7 | 12 | 1 | 0 |


| V_ID | Name | Liga |
|------|------|------|
| 1    | FC Bayern München | 1 |
| 2    | FC Schalke 04     | 1 |

Man sieht, dass die Vereine `Heim` und `Gast` in der Spieltabelle als _foreign key_ auf die Vereinstabelle aufzulösen sind.
Für unsere heutigen Beispiele können wir die Fabrikmethode `Bundesliga.loadFromResource()` verwenden, um die Daten aus den beiliegenden CSV Dateien einzulesen.


## Grundoperationen

Datenverarbeitung beruht im Wesentlichen auf vier Grundoperationen:

- Sortieren, also die Daten in eine gewünschte Reihenfolge bringen;
- Filtern, also das Entfernen gewisser Daten, bzw. das Behalten von nur gewissen Daten;
- Abbilden, also Daten von einem Format auf ein anderes umzurechnen;
- Reduzieren, also Daten zusammenzufassen.


### Sortieren

Oft ist die Reihenfolge, in der man Daten zur Verfügung gestellt bekommt, nicht die Reihenfolge, in welcher man diese darstellen oder weiterverarbeiten möchte.

In unserem Beispiel sind die Vereine nach aktuellem Tabellenplatz und nach Liga sortiert.
Möchte man aber die Liste aufsteigend nach Vereinsnamen sortiert haben, so muss man diese Liste entsprechend _sortieren_ (siehe [Kapitel 9: Sortieren](/09-sortieren/)).

```java
Bundesliga b = Bundesliga.loadFromResource();

// neue Liste erstellen, alle Vereine hinzufügen
List<Verein> nachName = new LinkedList<>(b.vereine.values());

// nach Vereinsnamen sortieren
nachName.sort(new Comparator<Verein>() {
	@Override
	public int compare(Verein o1, Verein o2) {
		return o1.getName().compareTo(o2.getName());
	}
});
```

Möchte man erst nach Liga und dann nach Namen sortieren, so muss man einen Comperator schreiben, der dieses Verhalten erfüllt:

```java
Bundesliga b = Bundesliga.loadFromResource();

List<Verein> nachLigaName = new LinkedList<>(b.vereine.values());
nachLigaName.sort(new Comparator<Verein>() {
	@Override
	public int compare(Verein o1, Verein o2) {
		// erst wenn Ligen gleich sind, dann nach Name!
		if (o1.getLiga() == o2.getLiga())
			return o1.getName().compareTo(o2.getName());
		else
			return Integer.compare(o1.getLiga(), o2.getLiga());
	}
});
```


### Filtern

Je nach Anwendung kann es aber gut sein, dass wir garnicht an allen Vereinen interessiert sind, sondern nur an den Vereinen der 2. Liga.

```java
Bundesliga b = Bundesliga.loadFromResource();

List<Verein> zweiteLiga = new LinkedList<>();
for (Verein v : b.vereine.values()) {
	if (v.getLiga() == 2)
		zweiteLiga.add(v);
}

zweiteLiga.sort(new Comparator<Verein>() {
	@Override
	public int compare(Verein o1, Verein o2) {
		return o1.getName().compareTo(o2.getName());
	}
});
```

Man sieht hier auch: Es ist sinnvoll _vor_ dem Sortieren zu filtern, da man sonst Daten sortiert, an denen man nicht interessiert ist.


### Abbilden

Wir werfen nun einen Blick auf die Spieltabelle.

| Spiel\_ID | Spieltag | Datum | Uhrzeit | Heim | Gast | Tore\_Heim | Tore\_Gast |
|-----------|----------|-------|---------|------|------|------------|------------|
| 1 | 1 | 2017-08-18 | 20:30:00 | 1 | 5 | 3 | 1 |
| 2 | 1 | 2017-08-19 | 15:30:00 | 7 | 12 | 1 | 0 |

Jeder Eintrag (Klasse `Spiel`) hat also die vollständige Spielinformation.
Wenn wir nun aber nur an den _Spielpaarungen_ interessiert sind, also an welchem Datum welche Mannschaften gegeneinander spielen, so müssen wir ein `Spiel` auf ein Tripel abbilden, welches aus Datum sowie den Namen von jeweils Heim- und Gastverein besteht.
Wir wollen also eine `List<Spiel>` in eine `List<Triple<String, String, String>>` abbilden.

```java
Bundesliga b = Bundesliga.loadFromResource();

// neue Liste mit Zieldatentyp
List<Triple<String, String, String>> paarungen = new LinkedList<>();
for (Spiel s : b.spiele) {
	// Verwende Vereinstabelle um ID in Verein aufzulösen
	Verein heim = b.vereine.get(s.getHeim());
	Verein gast = b.vereine.get(s.getGast());

	// Erstelle neues Triple aus Datum sowie Vereinsnamen
	paarungen.add(Triple.of(s.getDatum(), heim.getName(), gast.getName()));
}
```

Die Klassen [`org.apache.commons.lang3.tuple.Pair`](https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/tuple/Pair.html) und [`org.apache.commons.lang3.tuple.Triple`](https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/tuple/Triple.html) sind generische Klassen für Paare und Tripel, welche mit den Fabrikmethoden `Pair.of(...)` sowie `Triple.of(...)` einfach instanziiert werden können.


### Reduzieren

Abbilden bedeutet also immer das Umwandeln einer Liste in eine andere Liste.
_Reduzieren_ bedeutet, eine Liste von Werten auf einen einzigen Wert zu reduzieren.
Ein einfaches Beispiel wäre das Aufsummieren von Werten in einem Array:

```java
int[] a = {1, 2, 3};
int summe = 0;
for (int v : a)
	summe += a;
```

In unserem Bundesligabeispiel wäre eine ähnliche Fragestellung: Wie viele Tore wurden insgesamt geschossen?

```java
Bundesliga b = Bundesliga.loadFromResource();

int tore = 0;
for (Spiel s : b.spiele) {
	tore = tore + s.getToreGast() + s.getToreHeim();
}

System.out.println("Es fielen insgesamt " + tore + " Tore in " + b.spiele.size() + " Spielen");
// "Es fielen insgesamt 1741 Tore in 714 Spielen"
```


## Beispiele

Wir wollen nun versuchen, für die folgenden Fragestellungen die entsprechende Datenverarbeitung zu programmieren.
Jeder dieser Datenverarbeitungsflüsse (engl. _pipelines_) soll dabei je nach Fragestellung Sortieren, Filtern, Abbilden und/oder Reduzieren.

1. Torstatistiken.
	1. Wie viele Tore fallen durchschnittlich in jedem Spiel?
	2. Wie viele Tore fallen durchschnittlich in einem Spiel der 1. Liga?
	3. Wie viele Tore fallen durchschnittlich an einem Spieltag der 2. Liga?
2. Vereine.
	1. Wie viele Tore hat der FC Bayern München (Verein 1) erzielt?
	2. Wie viele Tore hat der FC Schalke 04 (Verein 2) kassiert?
	3. Wie viele Punkte hat der 1. FC Nürnberg (Verein 20)? Ein Sieg zählt 3 Punkte, ein Unentschieden 1, eine Niederlage 0 Punkte.
	4. Was ist das Torverhältnis des VfL Bochum (Verein 26), also die Rate von erzielten zu kassierten Toren?
3. 1. Liga.
	1. Wie ist der aktuelle Tabellenstand? Die Tabelle wird als Vereinsname, gefolgt von Punkten und Torverhältnis definiert.
	2. Wie ist der Tabellenstand nach dem 10. Spieltag?
	3. Wie ist der Tabellenplatzverlauf des Hamburger SV (Verein 18) über alle 32 Spieltage?
	4. Wer hat die [Rote Laterne](https://de.wikipedia.org/wiki/Lanterne_Rouge) in jeweils der 1., 2. und 3. Liga?

Für die systematische Bearbeitung dieser Fragestellungen sollten Sie für jede dieser Fragestellungen zuerst die folgenden Fragen beantworten:

1. Was sind die **Eingabedaten**, und in welcher Datenstruktur liegen diese vor?
2. Was sind die **Ausgabedaten**, und in welcher Datenstruktur bzw. Form sollen diese vorliegen?
3. Welche Operationen (sortieren, filtern, abbilden, reduzieren) sind nötig, und in welcher Reihenfolge? Was sind die Zwischenprodukte?

Verwenden Sie die Klasse `Bundesliga` sowie die Fabrikmethode `Bundesliga.loadFromResource()`; die Attribute `List<Spiel> spiele` und `Map<Integer, Verein> vereine` sind öffentlich sichtbar (wie bereits den obigen Beispielen zu entnehmen).

Die Musterlösungen zu obigen Aufgaben finden Sie in der Klasse [`ch10.AnalysenTest`](https://github.com/hsro-wif-prg2/hsro-wif-prg2.github.io/blob/master/examples/src/test/java/ch10/AnalysenTest.java) sowie [unten an dieses Kapitel angehängt](#lösungen-zu-den-beispielen).


## Verallgemeinerung der Operationen

> Hinweis: Die folgenden Verallgemeinerungen sind zwar im Prinzip nicht klausurrelevant, machen die Datenverarbeitung aber klarer und strukturierter.
> Sie stellen auch eine gute Überleitung zu den ebenso nicht klausurrelevanten Streams (`java.util.Stream`) dar, welche die Datenverarbeitung aber ebenso deutlich übersichtlicher und damit einfacher machen.


### Sortieren

Die allgemeine Form des Sortierens mit Hilfe von `Comparable<T>` bzw. `Comparator<T>` wurde bereits ausführlich im [Kapitel 9: Sortieren](/09-sortieren/) besprochen.


### Filtern

Beim Filtern fällt auf, dass zunächst iteriert wird, und dann nach einem bestimmten Bedingung in eine neue Liste eingefügt wird.

```java
List<Verein> zweiteLiga = new LinkedList<>();
for (Verein v : b.vereine.values()) {
	// Bedingung prüfen...
	if (v.getLiga() == 2) {
		zweiteLiga.add(v);  // hinzufügen
	}
}
```

Ähnlich zum `Comparator<T>`, welcher den Vergleich beim Sortieren abstrahiert, kann man hier die Bedingung mit einem `Predicate<T>` abstrahieren:

```java
interface Predicate<T> {
	boolean test(T t);
}
```
```java
static <T> List<T> filtern(Collection<T> liste, Predicate<T> pred) {
	// neue Liste erstellen
	List<T> gefiltert = new LinkedList<>();

	// iterieren...
	for (T v : liste) {
		// Bedingung prüfen...
		if (pred.test(v))
			gefiltert.add(v);
	}

	return gefiltert;
}
```

Entsprechend kürzer wird die Anwendung:

```java
List<Verein> zweiteLiga = filtern(b.vereine.values(), new Predicate<Verein>() {
		public boolean test(Verein v) {
			return v.getLiga() == 2;
		}
});
```

### Abbilden

Beim Abbilden erkennen wir, dass zwar sowohl Ein- als auch Ausgabe Listen sind, allerdings sind die Datentypen i.d.R. verschieden!

```java
// neue Liste mit Zieldatentyp
List<Triple<String, String, String>> paarungen = new LinkedList<>();
for (Spiel s : b.spiele) {
	// Verwende Vereinstabelle um ID in Verein aufzulösen
	Verein heim = b.vereine.get(s.getHeim());
	Verein gast = b.vereine.get(s.getGast());

	// Erstelle neues Triple aus Datum sowie Vereinsnamen
	paarungen.add(Triple.of(s.getDatum(), heim.getName(), gast.getName()));
}
```

Hier wird eine `List<Spiel>` auf eine `List<Triple<String, String, String>>` abgebildet.
Es wird wie beim Filtern die gesamte Liste durchlaufen, um dann für jeden Eintrag einen neuen Eintrag in der Zielliste zu erstellen.
Diese Operation kann man als eine Methode generalisieren, welche ein Argument eines Typs `T` entgegennimmt und ein Objekt vom Typ `R` (_return_) zurückgibt.
Analog zum `Comparator` und `Predicate` wird diese Methode im Rahmen eines Interfaces übergeben:

```java
interface Function<T, R> {
	R apply(T t);
}
```
```java
static <T, R> List<R> abbilden(List<T> liste, Function<T, R> func) {
	List<R> abgebildet = new LinkedList<>();

	for (T v : liste)
		abgebildet.add(func.apply(v));

	return abgebildet;
}
```

Auch hier die Anwendung:

```java
List<Triple<String, String, String>> paarungen = abbilden(b.spiele, new Function<Spiel, Triple<String, String, String>>() {
	@Override
	public Triple<String, String, String> apply(Spiel spiel) {
		Verein heim = b.vereine.get(spiel.getHeim());
		Verein gast = b.vereine.get(spiel.getGast());
		return Triple.of(spiel.getDatum(), heim.getName(), gast.getName());
	}
});
```


### Reduzieren

Für die Abstraktion der Reduktion bleiben wir zunächst am einfachen Beispiel:

```java
List<Integer> a = Arrays.asList(1, 2, 3);
Integer summe = 0;
for (Integer v : a)
	summe = summe + a;
```

Es wird also ein Array _reduziert_, indem ein aktueller Zwischenwert mit dem nächsten Element aus dem Array addiert wird.
Die Addition ist dabei ein _binärer Operator_, da er zwei Operatoren entgegennimmt (`summe` und `a`) um das Ergebnis zu berechnen.
Abstrahiert man diese Operation, so kann man die Reduktion allgemein fassen:

```java
interface BinaryOperator<T> {
	T apply(T a, T b);
}
```
```java
static <T> T reduzieren(List<T> liste, T identity, BinaryOperator<T> op) {
	T a = identity;
	for (T t : liste)
		a = op.apply(a, t);
	return a;
}
```
```java
List<Integer> a = Arrays.asList(1, 2, 3);

// Reduzieren, mit "Null"-Element 0 und Addition als Reduzierer
Integer summe = reduzieren(a, 0, new BinaryOperator<Integer>() {
			public Integer apply(Integer a, Integer b) {
				return a + b;
			}
		});
```

Im Eingangsbeispiel hatten wir eine Liste von Spielen auf einen Integerwert (die Gesamtsumme der Tore abgebildet).
Dazu müssen wir die `reduzieren` Methode etwas genauer spezifizieren, so dass beim eigentlichen reduzieren die Datenelemente verwendet, aber ein anderer Datentyp zurückgegeben werden kann:

```java
interface BiFunction<A, B, C> {
	C apply(A a, B b);
}
```
```java
static <T, U> U reduzieren(List<T> liste, U identity, BiFunction<U, T, U> op) {
	U a = identity;
	
	for (T t : liste)
		a = op.apply(a, t);
	
	return a;
	
}
```

Und die Anwendung:

```java
// Reduzieren mit 0 als "Null"-Element und
// einem Reduzierer, welcher den aktuellen Torstand mit den Heim- und Gasttoren addiert.
Integer tore = Abstraktion.reduzieren(b.spiele, 0, new BiFunction<Integer, Spiel, Integer>() {
			@Override
			public Integer apply(Integer integer, Spiel spiel) {
				return integer + spiel.getToreGast() + spiel.getToreHeim();
			}
		});
```


# Datenströme in Java

Die oben erarbeiteten Verallgemeinerungen sind eine Hinführung auf die _funktionale_ Programmierung, in der Funktionen (Methoden) wie normale Objekte verwendet werden können.
Da Java rein nach Sprachspezifikation keine solche _funktionale_ Sprache ist, behilft man sich hier mit sog. _funktionalen Schnittstellen_, also Interfaces welche genau eine (nicht-default) Methode haben, und deren Zweck einzig darin liegt, eine Funktion als Objekt zu übergeben.
Wir hatten dazu `Comparator<T>`, `Predicate<T>`, `Function<T, R>` sowie `BinaryOperator<T>` kennen gelernt.

Wir haben an den obigen Beispielen auch gesehen, dass die Datenverarbeitung oft als Datenfluss mit Modifikatoren bzw. Operationen realisiert wird: Ausgehend von einer Liste werden verschiedene Zwischenergebnisse erstellt und abschließend das eigentliche Ergebnis in Form einer neuen Liste oder eines reduzierten Werts zurück gegeben.
Solche Datenströme können in Java seit der Version 8 mit dem [Streamkonzept](https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html) implementiert werden.
Herzstück dieser Methodik ist die Klasse [`java.util.Stream`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html), welche einen Datenfluss modelliert, welcher nun unter anderem mit den folgenden Methoden bearbeitet werden kann:

- `Stream<T> sorted()` bzw. `Stream<T> sorted(Comparator<T> comparator)` zum Sortieren;
- `Stream<T> filter(Predicate<T> pred)` um einen Stream zu erstellen, welcher nur noch Elemente enthält, welche mit `pred` positiv getestetet wurden;
- `Stream<R> map(Function<T, R> mapper)` um einen Stream von `T` in einen Stream von `R` umzuwandeln; und
- `T reduce(T identity, BinaryOperator<T> op)` bzw. `U reduce(U identity, BiFunction<U, T, U> acc, BinaryOperator<U> comb)` um einen Stream auf einen einzelnen Wert zu reduzieren.

Solche Streams können z.B. von Arrays oder Listen erstellt werden:

```java
Stream<Integer> ints = Stream.of(1, 2, 3, 4, 5);
Stream<Float> floats = Stream.of(new Float[] { 1.0, 2.0, 3.0});

List<String> liste = Arrays.asList("Hans", "Dampf");
Stream<String> strings = liste.stream();
```

Um mit Streams nun den aktuellen Punktestand sowie die Tordifferenz für Stuttgart (Verein 8) auszurechnen, können wir den originalen Datenstrom _Spiele_ (`bl.spiele.stream()`)...

1. ...auf Spiele mit Stuttgart filtern;
2. ...dann auf ein Paar von Punkten und Tordifferenz abbilden (hierzu beachten, ob Stuttgart Gast oder Gastgeber ist);
3. ...den Stream von solchen Paaren nun reduzieren, indem jeweils die linken und rechten Teile der Paare aufaddiert werden.

```java
Bundesliga bl = Bundesliga.loadFromResource();

final int team = 8; // VfB Stuttgart
Pair<Integer, Integer> scores = bl.spiele.stream()
		// nur Spiele mit Stuttgart
		.filter(s -> s.getHeim() == team || s.getGast() == team)
		// bilde ab Spiel --> Paar(Punktgewinn, Tordifferenz) pro Spiel
		.map(new Function<Spiel, Pair<Integer, Integer>> () {
			public Pair<Integer, Integer> apply(Spiel s) {
				boolean heimspiel = (s.getHeim() == team);

				// Punkte: 0-1-3 fuer Niederlage, Unentschieden, Sieg
				int punkte = 0;
				if (s.getToreHeim() == s.getToreGast())
					punkte = 1;
				else if ((heimspiel && s.getToreHeim() > s.getToreGast()) ||
						!heimspiel && s.getToreGast() > s.getToreHeim())
					punkte = 3;

				// Punktedifferenz -- Achtung: ist das Team Host/Gast?
				int diff = heimspiel
						? s.getToreHeim() - s.getToreGast()
						: s.getToreGast() - s.getToreHeim();

				// System.out.println(s.toString(bl.vereine) + " " + punkte + " " +  diff);

				return Pair.of(punkte, diff);
			}
		})
		// Jetzt Punkte (left) und Differenzen (right) aufaddieren, fuer Gesamtpunkte und -differenz.
		.reduce(Pair.of(0, 0),
				(a, b) -> Pair.of(a.getLeft()+b.getLeft(), a.getRight()+b.getRight()),
				(a, b) -> Pair.of(a.getLeft()+b.getLeft(), a.getRight()+b.getRight()));
```

## Erweiterte Operationen

Für Streams gibt es noch weitere nützliche Funktionen:

- `limit(int n)`: terminiert den Stream nach `n` Elementen;
- `skip(int n)`: entfernt die ersten `n` Elemente des Streams;
- `findFirst()` bzw. `findLast()`: Gibt ein `Optional<T>` auf das erste bzw. letzte Element zurück, welches mit `.get()` ausgepackt werden kann.
- `collect(...)`: Sammelt Elemente des Streams ein, z.B. in eine Liste (`Collectors.toList()`) oder Map (`Collectors.toMap(...)`); mit `Collectors.groupingBy(...)` können Elemente nach einem Schlüssel in Listen einsortiert werden; siehe [ausführliche Dokumentation](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html).

...sowie viele mehr, zu finden in der [Dokumentation](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html).


# Zusammenfassung

- Datenverarbeitung besteht meist aus einer Kombination von Sortieren, Filtern, Abbilden und Reduzieren
- _Sortieren_ ändert die Reihenfolge der Objekte, und wird oft mit `Comparator<T>`-Objekten realisiert.
- _Filtern_ entfernt Objekte, welche nicht positiv mit einem `Predicate<T>` getestet werden.
- _Abbilden_ heißt Objekte eines Typs (z.B. `Spiel`) in Objekte eines anderen Typs (z.B. `Pair<Integer, Integer>` für Punkte und Tordifferenz) umzuwandeln.
- _Reduzieren_ heißt Objekte eines Typs (z.B. `Pair<Integer, Integer>`) auf ein Objekt zu akkumulieren, wozu eine entsprechende Operation definiert werden muss.
- Die vier Operationen können explizit durch Listengenerierung und Iteration realisiert werden; abstrahiert man die Operationen, so kann man sich auf die Implementierung der eigentlichen Logik konzentrieren.
- Java Streams sind eine Variante, bei der nur die eigentliche Logik zu implementieren ist; die Iterationslogik ist versteckt.

<p style="text-align: right">&#8718;</p>


### Lösungen zu den Beispielen

1. Torstatistiken.
	1. 2.645
	2. 2.746
	3. 24.968
2. Vereine.
	1. 88
	2. 36
	3. 57
	4. $35-36=-1$
3. 1. Liga.
	1. [Lösung](https://www.fussballdaten.de/bundesliga/2018/32/)
	2. [Lösung](https://www.fussballdaten.de/bundesliga/2018/10/)
	3. 8, 3, 7, 8, 12, 15, 16, 15, 16, 16, 15, 15, 15, 15, 15, 16, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 18, 18, 17, 17, 17, 17
	4. Köln, Kaiserslautern, Erfurt
