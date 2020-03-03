class: title-slide  

# Modul - Objektorientierte Programmierung
### Bachelor Wirtschaftsinformatik

## 05 - Factory, Iterator und Klassen
### Prof. Dr. Marcel Tilly
Fakultät für Informatik, Cloud Computing

---

# Factory Method

.center[![:scale 70%](./factory-method.svg)]

- Methode gibt Instanz zu Interface zurück
- Aufrufer der Methode kennt nur das _Interface_ als Rückgabetyp bekannt ist, nicht aber die der Instanz zu Grunde liegende _implementierende Klasse_.
- Fabrikmethode ist ein Erstellungsmuster (_creational pattern_).

---

# Iterator

.center[![:scale 70%](./iterator.svg)]

- Modellierung des sequenziellen Zugriffs auf eine Containerstruktur
- Setzt `Iterable` und `Iterator` in Beziehung
- Verhaltensmuster (_behavioral pattern_)
- Verwendet dabei das Factory Method Pattern.

---

# Verwendung

Regulär, mit `while` Schleife

```java
Iterator<Integer> it = intset.iterator();
while (it.hasNext())
	System.out.println(it.next());
```

Mit `for-each` Schleife (erfordert `implements Iterable<T>`):

```java
for (int i : intset)
	System.out.println(i);
```

---

# Syntax: Klassen

In Java sind folgende Klassen möglich:

- (normale) Klasse

- _innere_ Klasse

- _statische innere_ Klasse

- _anonyme innere_ Klasse

---

# (Normale) Klassen

```java
class MeineKlasse {

}
```

- **Eine** normale, nicht-statische Klasse pro `.java` Datei
- Klassenname muss gleich dem Dateinamen sein; Übersetzung von `MeineKlasse.java` in `MeineKlasse.class` (Bytecode).
- Kann beliebig viele innere Klassen enthalten

---

# Innere Klassen

```java
class MeineKlasse {
	`private MeineInnere` {
		
	}

	`private static MeineStatischeInnere` {

	}
}
```

- Beliebig viele innere Klassen
- Sichtbarkeit und Gültigkeit analog zu Attributen
- **Innerhalb** einer normalen Klasse, **ausserhalb** von Methoden
- `Innere` kann **nur** in Instanz von `Aeussere` existieren
- Im Prinzip beliebig schachtelbar (innere in inneren in inneren, ...)

---

# (Nicht-statische) Innere Klasse

```java
class Aeussere {
	private String attribut = "A";
	private static String ATTRIBUT = "B";

	class Innere {
		String attribut = "X";
		
		// Compilerfehler! Innere können keine static haben
		static String ATTRIBUT = "Y";
		
		void zugriff() {
			System.out.println(this.attribut);  // "X"
			System.out.println(Aeussere.this.attribut);  // "A"
		}
	}	
}
```

---

# (Nicht-statische) Innere Klasse

- Zugriff auf alle Attribute der `Aeussere` Instanz
- Bei Namenskonflikten mit `<KlassenName>.this.*` disambiguieren
- keine `static` Attribute in inneren Klassen

---

# Statische Innere Klasse

```java
class Aeussere {
	Strint attribut = "A";
	static class Statische {
		String attribut = "Z";
		void zugriff() {
			System.out.println(attribut);  // "Z"
			// Compilerfehler! Statische Innere hat keinen Aeussere-Scope
			System.out.println(Aeussere.this.attribut);
		}
	}
}
```

- Können ohne Instanz der äußeren Klasse verwendet werden

```java
Aeussere.Statische inst = new Aeussere.Statische();
```

- Folglich kein direkter Zugriff auf die Attribute der äußeren Klasse


---

# Anonyme Innere Klasse

```java
interface Intf {
	void methode();
}
```
```java
final int aussen = 10;
Intf inst = new Intf() {
	int attr;
	{
		// Konstruktor, wenn nötig
		attr = aussen;  // Zugriff nur auf quasi-final!
	}
	public void methode() {
		System.out.println(attr);
	}
};
```

---

# Anonyme Innere Klasse

- Erstellt ein Objekt das ein Interface implementiert
- Klassendefinition aber zur Laufzeit unbekannt ("anonym")
- Zugriff auf äußere Attribute nur, wenn diese quasi-final sind.

---

# Zusammenfassung

- Die **Iteration** für Containerstrukturen (wie z.B. Listen oder Sets) ist eine Abstraktion, welche dem Benutzer sequenziellen Zugriff auf die enthaltenen Elemente gibt, ohne die innere Struktur zu kennen.
- Der **Iterator** als **Verhaltensmuster** (behavioral pattern) beschreibt dabei den Zusammenhang der Interfaces `Iterator<T>` und `Iterable<T>`.
- Die **Fabrikmethode** (factory method) ist ein **Erstellungsmuster** (creation pattern) welches sich auch im Iterator Muster wiederfindet.
- **Iteratoren für sequenzielle Datenstrukturen** sind im Allgemeinen einfach zu implementieren: sie erinnern die aktuelle Position.
- **Iteratoren für Baumstrukturen**, also Datenstrukturen deren Elemente mehr als einen Nachfolger haben, verwenden hingegen eine **Agenda:** eine Liste von noch zu besuchenden Elementen.
- Es gibt _normale_, _innere_, _statische innere_ und _anonyme innere_ Klassen

---

<div style="margin-top: 30%"></div>

# Fragen?
