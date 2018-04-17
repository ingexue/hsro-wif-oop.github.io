---
title: "Map"
permalink: /06-map/
mathjax: true
---

# Datenstrukturen

Wir haben nun mit der [Liste](/02-linked-list/) als _sequenziellen_ und dem [Set bzw. Binärbaum](/03-tree-set/) als _duplikatfreien_ Container bereits zwei der drei wichtigsten und grundlegenden Datenstrukturen der Informatik kennengelernt.
Beide hatten wir mit verschiedenen Beispielen veranschaulicht.

Da die _Liste_, im Gegensatz zu einem Array, dynamisch wachsen und schrumpfen kann, ist sie immer dann die richtige Wahl, wenn zur Entwicklungszeit nicht bekannt ist, wie viele Elemente gespeichert werden müssen.
Ein typisches Beispiel dafür ist die Arbeit mit Benutzereingaben:

```java
Scanner sc = new Scanner(System.in);

System.out.println("Wie viele Einträge?");
int howMany = Integer.parseInt(sc.nextLine());

List<String> entries = new LinkedList<>();
for (int i = 0; i < howMany; i++) {
	System.out.println("Bitte Eintrag " + (i+1) + " eingeben:");
	entries.add(sc.nextLine());
}

System.out.println("Sie haben eingegeben: " + entries);
```

Wenn wir obendrein wissen wollten, ob ein Element einzigartig ist, so konnten wir zusätzlich ein _Set_ zu Rate ziehen:

```java
Set<String> set = new TreeSet<>();
for (int i = 0; i < howMany; i++) {
	// ...
	if (set.contains(entry)) {
		System.out.println("Entschuldigung, der Eintrag ist bereits vergeben.");
		i--;
		continue;
	} else {
		set.add(entry);
	}
	// ...
}
```


Aber was nun, wenn man in obigen Beispiel Duplikate nicht verhindern möchte, sondern im Gegenteil _mitzählen_ möchte, wie oft ein bestimmter Eintrag vor kam?
Man bräuchte also eine Datenstruktur, welche in obigen Beispiel zu einem `String` (einem Eintrag) einen `Integer` Wert (die Anzahl) speichert.


# Map: ein assoziatives Datenfeld

Heute fügen wir eben damit die dritte (und für dieses Semester letzte) Datenstruktur hinzu: das assoziative Datenfeld (engl. _map_), welches zu einem Schlüsselobjekt genau ein Wertobjekt speichert bzw. liefert.

In Java ist diese Datenstruktur als generisches Interface definiert:

```java
interface Map<K, V> {
	void put(K key, V value);
	V get(K key);
	boolean containsKey(K key);
	int size();
}
```

Auffallend ist dabei, dass die `Map` über **zwei** Typvariablen verfügt: `K` für den Schlüsseltyp (_key_) und `V` für den Wertetyp (_value_).
Eine Map ähnelt einem Set dahin gehend, dass es eine Methode zum Hinzufügen von Assoziationen verfügt (`put`), sowie eine um von einem Schlüssel auf den Wert aufzulösen (`get`).
Die `contains` Methode ist hier mit `containsKey` etwas spezifischer, um Missverständnissen vorzubeugen.

> Hinweis: Andere Sprachen wie z.B. python, C\# oder JavaScript haben assoziative Speicher als integriertes Sprachelement.
> In Java wird diese Funktionalität von der Java API bereit gestellt.
> Das "offizielle" Java Interface `java.util.Map` hat zusätzlich noch weitere Methoden, auf die wir später noch kurz eingehen werden.

Hätten wir nun eine Implementierung einer solchen `Map`, so könnten wir für eine Liste von Strings recht einfach auszählen, welcher String wie oft vorkommt:

```java
Map<String, Integer> zaehler = ???;  // dazu später
for (String s : liste) {
	int z = 0;
	if (zaehler.containsKey(s))
		z = zaehler.get(s);
	zaehler.put(s, z+1);
}
```

## Implementierung

Wir starten mal beim Set/Tree, aber brauchen ein anderes Element, welches neben key auch payload hat.

.equals ueberladen, implements Comparable


## EntrySet und Iteration

Entry als sub-interface


## Erweiterte Methoden

+ java.util.map: entrySet, keySet, values, containsvalue

## Exkurs: Effizienz durch Hashing

+ .hashCode
+ Hashingfunktion: (n - 1) & hash; n = 32, 64, ... --> keep lower log(n) bits
+ List in Bins; Ausblick auf trees
+ HashMap


# Zusammenfassung

+ Map als assoziative Datenstruktur
+ .equals implementieren
+ wenn mit Tree realisiert: Comparable<T> implementieren, oder Comparator<T> verwenden
+ .hashCode implementieren, wenn mit HashMap verwenden
+ Übung: Map, Wörter zählen, spezielle abfragen (keine Iteration)

