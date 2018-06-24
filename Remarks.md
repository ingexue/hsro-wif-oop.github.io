# Remarks to Programmieren 2


- 3/21
	+ Spezifikation mit UML; Klassen und Objekte, Sichtbarkeiten und Kapselung
	+ Beispiel: rationale Zahlen
		* Konstruktoren
		* Klassenvariablen
		* Überladung
		* Überdeckung (shadowing), `this`
	+ JavaDoc
	+ Testen mit JUnit
	+ Ausnahmebehandlung
	+ Versionierung mit git
	+ Übung: Einfache Tests; vielleicht Übung vom letzten mal?
- 3/28
	+ Wiederholung: Arrays
	+ Arraylist: reallokiert und verblockt
	+ Interfaces
	+ LinkedList: toString, add, get, remove
	+ Übung: Stack
- 4/4
	+ Set für ints; beginne 1:1 mit LinkedList; refactor for inner class
	+ Binärbaum: contains, add; remove!?
	+ Exkurs: Rekursion für `toString`
	+ Übung: Set von Strings (`compareTo()`)
- 4/11
	+ Generics
	+ Einfach: Liste
	+ Kombiniert: `Comparator<T>` für `Set<T>`
	+ Bounds: `Set<T extends Comparable<T>>`
	+ Ausblick: `super` und Wildcards
- 4/18
	+ Iterator (und Factory)
	+ Für Liste
	+ Für Baum (-> Agenda); Beispiel fuer Loeschen von Elementen?
	+ Übung: Iterator für Liste und Map; Wörter zählen
- 4/25
	+ Wiederholung Iteration, Liste/Tree
	+ Map als generischer assoziativer Container
	+ Interface: put, get, containsKey, delete (?)
	+ Hashingfunktion: (n - 1) & hash; n = 32, 64, ... --> keep lower log(n) bits
	+ via List
	+ via Binärbaum
	+ Übung: Map, Wörter zählen, spezielle abfragen (keine Iteration)
- 5/2
	+ Annotationen
	+ Einführung in die PStA (Contestsystem)
	+ Übung: Contestsystem
- 5/9
	+ Rekursion
	+ ggt, fak, etc.
	+ Baum: toString (via print or printstream)
	+ Liste: insert
	+ Baum: insert
	+ Übung: Summieren
- 5/16:
	+ Sortieren
	+ insert/select
	+ merge
	+ Ausblick: quick
	+ Übung: Comparator und Comparable
- 5/23
	+ Java Bibliothek: List, Set, Map, Iterator, Comparator
	+ filter-map-reduce
	+ forEach
	+ Übung: Trump Tweet
- 5/30
	+ Klassen und UML
	+ Datenstrukturen, Iteratoren
	+ Nochmal Filter/Map/Reduce
	+ Übung: ???
- 6/6 Vererbung
	+ `extends` vs. `implements`
	+ Überschreiben (`@Override`), `super`
	+ Basisklasse `Object`, immer `equals` und `hashCode` ueberschreiben!
	+ Polymorphie
	+ Übung: Auto, Spezialisierung
- 6/13 ABC
	+ Abstrakte Klassen und Interfaces
	+ Musiker/Auto
	+ Übung: shapes, bremsen
- 6/20 Threads
	+ Einfache Threads
	+ Synchronisierung
	+ Future
	+ Consumer/Producer
	+ Übung: Consumer/Producer

Kassiert:
- 6/27 --> 7/4
	+ Design Patterns: Iterator, Factory, Observer, Composite, ...
	+ Composite
	+ Observer
	+ Übung: Android: `OnClick` und Futures? Mensa Plan?


ch-11: schlecht; nächstes Semester neu mit Skript+Folien+besseren Beispielen

## Übungen

Ausser der Zaehlung:
1. Git und IntelliJ
2. Einführung in die PStA
3. Testat (letzte Woche)