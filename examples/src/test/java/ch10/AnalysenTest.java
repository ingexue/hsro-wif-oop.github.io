package ch10;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;
import static sun.jvm.hotspot.HelloWorld.e;

class AnalysenTest {
	/**
	 * Wie viele Tore fallen durchschnittlich in jedem Spiel?
	 */
	@Test
	void torstatistikenToreProSpiel() throws IOException {
		Bundesliga bl = Bundesliga.loadFromResource();

		int anzTore = 0;
		for (Spiel s : bl.spiele)
			anzTore = anzTore + s.getToreGast() + s.getToreHeim();

		double toreProSpiel = (double) anzTore / bl.spiele.size();

		// 2.6458966565349544
		assertEquals(2.645, toreProSpiel, 0.001);
	}

	/**
	 * Wie viele Tore fallen durchschnittlich in einem Spiel der 1. Liga?
	 */
	@Test
	void torstatustikenToreProErstligaspiel() throws IOException {
		Bundesliga bl = Bundesliga.loadFromResource();

		int anzTore = 0;
		int anzSpiele = 0;
		for (Spiel s : bl.spiele) {
			// 18 Teams in der 1. Liga
			if (s.getGast() <= 18 && s.getHeim() <= 18) {
				anzTore = anzTore + s.getToreGast() + s.getToreHeim();
				anzSpiele += 1;
			}
		}

		double toreProSpiel = (double) anzTore / anzSpiele;

		// 2.7465277777777777
		assertEquals(2.746, toreProSpiel, 0.001);
	}

	/**
	 * Wie viele Tore fallen durchschnittlich an einem Spieltag der 2. Liga?
	 */
	@Test
	void torstatistikenToreProSpieltag2teLiga() throws IOException {
		Bundesliga bl = Bundesliga.loadFromResource();

		List<Spiel> spiele = new LinkedList<>();
		for (Spiel s : bl.spiele) {
			// keine Cross-Liga-Spiele...
			if (bl.vereine.get(s.getHeim()).getLiga() == 2)
				spiele.add(s);
		}

		// nach Spieltag sortieren
		spiele.sort(new Comparator<Spiel>() {
			@Override
			public int compare(Spiel o1, Spiel o2) {
				return Integer.compare(o1.getSpieltag(), o2.getSpieltag());
			}
		});

		int tag = -1;
		int anzSpieltage = 0;
		int anzTore = 0;
		for (Spiel s : spiele) {
			if (tag != s.getSpieltag()) {
				anzSpieltage += 1;
				tag = s.getSpieltag();
			}

			anzTore = anzTore + s.getToreHeim() + s.getToreGast();
		}

		double toreProSpieltag = (double) anzTore / anzSpieltage;

		// 24.96875
		assertEquals(24.968, toreProSpieltag, 0.001);
	}

	/**
	 * Stimmt es, dass in den Nachmittagsspielen (15:30:00) im Schnitt mehr Tore fallen, wie in den Abendspielen?
	 */
	@Test
	void torstatistikenToreNachmittagsAbends() throws IOException {
		Bundesliga bl = Bundesliga.loadFromResource();

		int toreNachmittag = 0;
		int spieleNachmittag = 0;
		int toreAbends = 0;
		int spieleAbends = 0;

		for (Spiel s : bl.spiele) {
			// Nachmittagsspiel?
			if (s.getUhrzeit().equals("15:30:00")) {
				toreNachmittag = toreNachmittag + s.getToreGast() + s.getToreHeim();
				spieleNachmittag += 1;
			}else {
				// ansonsten Abend
				toreAbends = toreAbends + s.getToreHeim() + s.getToreGast();
				spieleAbends += 1;
			}
		}

		double tn = (double) toreNachmittag / spieleNachmittag;
		double ta = (double) toreAbends / spieleAbends;

		System.out.println(tn + " " + ta);

		assertTrue(tn > ta);
	}

	/**
	 * Stimmt es, dass Vereine der 3. Liga zuhause im Schnitt mehr Tore schießen als auswärts?
	 */
	@Test
	void torstatistikenToreDaheim() throws IOException {
		Bundesliga bl = Bundesliga.loadFromResource();

		int anzDaheim = 0;
		int anzAuswaerts = 0;
		int anzSpiele = 0;
		for (Spiel s : bl.spiele) {
			// 3. Liga: Vereine 37...56
			if (s.getGast() <= 36)
				continue;

			anzDaheim += s.getToreHeim();
			anzAuswaerts += s.getToreGast();
			anzSpiele += 1;
		}

		double td = (double) anzDaheim / anzSpiele;
		double ta = (double) anzAuswaerts / anzSpiele;

		System.out.println(td + " " + ta);

		assertTrue(td < ta);
	}

	/**
	 * Wie viele Tore hat der FC Bayern München (Verein 1) erzielt?
	 */
	@Test
	void vereineToreVerein1erzielt() throws IOException {
		Bundesliga bl = Bundesliga.loadFromResource();

		int anzTore = 0;
		for (Spiel s : bl.spiele) {
			if (s.getHeim() == 1)
				anzTore += s.getToreHeim();
			else if (s.getGast() == 1)
				anzTore += s.getToreGast();
		}

		assertEquals(88, anzTore);
	}

	/**
	 * Wie viele Tore hat der FC Schalke 04 (Verein 2) erhalten?
	 */
	@Test
	void vereineToreVerein2erhalten() throws IOException {
		Bundesliga bl = Bundesliga.loadFromResource();

		int anzTore = 0;
		for (Spiel s : bl.spiele) {
			if (s.getHeim() == 2)
				anzTore += s.getToreGast();
			else if (s.getGast() == 2)
				anzTore += s.getToreHeim();
		}

		assertEquals(36, anzTore);
	}

	/**
	 * Wie viele Punkte hat der 1. FC Nürnberg (Verein 20)?
	 * Ein Sieg zählt 3 Punkte, ein Unentschieden 1, eine Niederlage 0 Punkte.
	 */
	@Test
	void vereineToreVerein20punkte() throws IOException {
		Bundesliga bl = Bundesliga.loadFromResource();

		int anzPunkte = 0;
		for (Spiel s : bl.spiele) {
			if (s.getHeim() == 20 && s.getToreHeim() > s.getToreGast()
					|| s.getGast() == 20 && s.getToreGast() > s.getToreHeim())
				anzPunkte += 3;
			else if ((s.getHeim() == 20 || s.getGast() == 20) && s.getToreHeim() == s.getToreGast())
				anzPunkte += 1;
		}

		assertEquals(57, anzPunkte);
	}

	/**
	 * Was ist das Torverhältnis des VfL Bochum (Verein 26)?
	 */
	@Test
	void vereineTorverhaeltnis26() throws IOException {
		Bundesliga bl = Bundesliga.loadFromResource();

		int erzielt = 0, erhalten = 0;
		for (Spiel s : bl.spiele) {
			if (s.getHeim() == 26) {
				erzielt += s.getToreHeim();
				erhalten += s.getToreGast();
			} else if (s.getGast() == 26) {
				erzielt += s.getToreGast();
				erhalten += s.getToreHeim();
			}
		}

		int diff = erzielt - erhalten;

		assertEquals(35, erzielt);
		assertEquals(36, erhalten);

		System.out.println(diff);
	}

	/**
	 * Welche drei Vereine haben die meisten Tore zuhause geschossen, und wie viele?
 	 */
	@Test
	void vereineMeisteToreZuhause() throws IOException {
		Bundesliga bl = Bundesliga.loadFromResource();

		Map<Integer, Integer> toreZuhause = new HashMap<>();

		for (Spiel s : bl.spiele)
			toreZuhause.merge(s.getHeim(), 1, (a, b) -> a + b);

		List<Pair<String, Integer>> liste = new LinkedList<>();

		for (Map.Entry<Integer, Integer> e : toreZuhause.entrySet()) {
			Verein v = bl.vereine.get(e.getKey());
			Integer tore = e.getValue();
			liste.add(Pair.of(v.getName(), tore));
		}

		liste.sort(Comparator.comparingInt(Pair<String, Integer>::getRight).reversed());

		List<Pair<String, Integer>> besteDrei = liste.subList(0, 3);

		System.out.println(besteDrei);

		assertEquals(Pair.of("SV Meppen", 34), besteDrei.get(0));
		assertEquals(Pair.of("FC Bayern München", 16), besteDrei.get(1));
		assertEquals(Pair.of("FC Schalke", 16), besteDrei.get(2));
	}

	/**
	 * Welcher Verein hat die wenigsten Tore auswärts geschossen, und wie viele?
	 */
	@Test
	void vereineWenigsteToreAuswaerts() throws IOException {
		Bundesliga bl = Bundesliga.loadFromResource();

		Map<Integer, Integer> toreZuhause = new HashMap<>();

		for (Spiel s : bl.spiele)
			toreZuhause.merge(s.getHeim(), 1, (a, b) -> a + b);

		List<Pair<String, Integer>> liste = new LinkedList<>();

		// "klassisch"
//		for (Map.Entry<Integer, Integer> e : toreZuhause.entrySet()) {
//			Verein v = bl.vereine.get(e.getKey());
//			Integer tore = e.getValue();
//			liste.add(Pair.of(v.getName(), tore));
//		}

		// funktional
		liste = Abstraktion.abbilden(toreZuhause.entrySet(), new Function<Map.Entry<Integer, Integer>, Pair<String, Integer>>() {
			@Override
			public Pair<String, Integer> apply(Map.Entry<Integer, Integer> e) {
				Verein v = bl.vereine.get(e.getKey());
				Integer tore = e.getValue();
				return Pair.of(v.getName(), tore);
			}
		});

		liste.sort(Comparator.comparingInt(Pair<String, Integer>::getRight));

		Pair<String, Integer> schlechtester = liste.get(0);

		System.out.println(schlechtester);

		assertEquals(Pair.of("VfR Aalen", 1), schlechtester);
	}

	/**
	 * Wie ist der aktuelle Tabellenstand? Die Tabelle wird als Vereinsname, gefolgt von Punkten und Torverhältnis definiert.
	 */
	@Test
	void ligaTabelle() throws IOException {
		Bundesliga bl = Bundesliga.loadFromResource();

		// vereinsId -> (punkte, erzielt, erhalten)
		Map<Integer, List<Triple<Integer, Integer, Integer>>> nachVerein = new HashMap<>();
		for (Spiel s : bl.spiele) {
			// nur 1. Liga
			if (bl.vereine.get(s.getHeim()).getLiga() != 1)
				continue;


			if (!nachVerein.containsKey(s.getHeim()))
				nachVerein.put(s.getHeim(), new LinkedList<>());
			if (!nachVerein.containsKey(s.getGast()))
				nachVerein.put(s.getGast(), new LinkedList<>());

			nachVerein.get(s.getHeim()).add(result(s, true));
			nachVerein.get(s.getGast()).add(result(s, false));
		}

		// Punkte und Tordifferenz fuer jeden Verein berechnen
		// Map --> (Verein, Punkte, Differenz)
		List<Triple<String, Integer, Integer>> tabelle = new LinkedList<>();
		for (Map.Entry<Integer, List<Triple<Integer, Integer, Integer>>> e : nachVerein.entrySet()) {
			Verein v = bl.vereine.get(e.getKey());

			int punkte = 0, diff = 0;
			for (Triple<Integer, Integer, Integer> t : e.getValue()) {
				punkte += t.getLeft();
				diff += (t.getMiddle() - t.getRight());
			}

			tabelle.add(Triple.of(v.getName(), punkte, diff));
		}

		// sortieren nach Punkten
		tabelle.sort(new Comparator<Triple<String, Integer, Integer>>() {
			@Override
			public int compare(Triple<String, Integer, Integer> o1, Triple<String, Integer, Integer> o2) {
				// bei Punktegleichheit entscheidet das bessere Torverhaeltnis (absteigend!)
				if (o1.getMiddle() == o2.getMiddle())
					return -Integer.compare(o1.getRight(), o2.getRight());
				else
					// absteigend sortieren nach Punkten
					return o2.getMiddle().compareTo(o1.getMiddle());
			}
		});

		// https://www.fussballdaten.de/bundesliga/2018/32/
		assertEquals(Triple.of("FC Bayern München",81,65), tabelle.get(0));
		assertEquals(Triple.of("1. FC Köln" , 22, -30), tabelle.get(17));

		for (Triple<String, Integer, Integer> t : tabelle)
			System.out.println(t);
	}

	static Triple<Integer, Integer, Integer> result(Spiel s, boolean heim) {
		int verein = heim ? s.getHeim() : s.getGast();
		int punkte = 0;
		if (s.getToreHeim() == s.getToreGast())
			punkte = 1;
		else if (heim && s.getToreHeim() > s.getToreGast()
				|| !heim && s.getToreGast() > s.getToreHeim())
			punkte = 3;

		return Triple.of(punkte, heim ? s.getToreHeim() : s.getToreGast(), heim ? s.getToreGast() : s.getToreHeim());
	}

	/**
	 * Wie ist der Tabellenstand nach dem 10. Spieltag?
	 */
	@Test
	void ligaTabelle10terSpieltag() throws IOException {
		Bundesliga bl = Bundesliga.loadFromResource();

		// vereinsId -> (punkte, erzielt, erhalten)
		Map<Integer, List<Triple<Integer, Integer, Integer>>> nachVerein = new HashMap<>();
		for (Spiel s : bl.spiele) {
			// nur 1. Liga
			if (bl.vereine.get(s.getHeim()).getLiga() != 1)
				continue;

			// nur bis 10. Spieltag
			if (s.getSpieltag() > 10)
				continue;


			if (!nachVerein.containsKey(s.getHeim()))
				nachVerein.put(s.getHeim(), new LinkedList<>());
			if (!nachVerein.containsKey(s.getGast()))
				nachVerein.put(s.getGast(), new LinkedList<>());

			nachVerein.get(s.getHeim()).add(result(s, true));
			nachVerein.get(s.getGast()).add(result(s, false));
		}

		// Punkte und Tordifferenz fuer jeden Verein berechnen
		// Map --> (Verein, Punkte, Differenz)
		List<Triple<String, Integer, Integer>> tabelle = new LinkedList<>();
		for (Map.Entry<Integer, List<Triple<Integer, Integer, Integer>>> e : nachVerein.entrySet()) {
			Verein v = bl.vereine.get(e.getKey());

			int punkte = 0, diff = 0;
			for (Triple<Integer, Integer, Integer> t : e.getValue()) {
				punkte += t.getLeft();
				diff += (t.getMiddle() - t.getRight());
			}

			tabelle.add(Triple.of(v.getName(), punkte, diff));
		}

		// sortieren nach Punkten
		tabelle.sort(new Comparator<Triple<String, Integer, Integer>>() {
			@Override
			public int compare(Triple<String, Integer, Integer> o1, Triple<String, Integer, Integer> o2) {
				// bei Punktegleichheit entscheidet das bessere Torverhaeltnis (absteigend!)
				if (o1.getMiddle() == o2.getMiddle())
					return -Integer.compare(o1.getRight(), o2.getRight());
				else
					// absteigend sortieren nach Punkten
					return o2.getMiddle().compareTo(o1.getMiddle());
			}
		});

		// https://www.fussballdaten.de/bundesliga/2018/10/
		assertEquals(Triple.of("FC Bayern München", 23, 17), tabelle.get(0));
		assertEquals(Triple.of("1. FC Köln" , 2, -15), tabelle.get(17));

		for (Triple<String, Integer, Integer> t : tabelle)
			System.out.println(t);
	}


	/**
	 * Wie ist der Tabellenplatzverlauf des Hamburger SV (Verein 18) über alle Spieltage?
	 */
	@Test
	void ligaTabellenplatzVerlaufVerein18() throws IOException {
		Bundesliga bl = Bundesliga.loadFromResource();

		// erstmal alle Spiele der ersten Liga
		List<Spiel> spiele = new LinkedList<>();
		for (Spiel s : bl.spiele) {
			// nur 1. Liga
			if (bl.vereine.get(s.getHeim()).getLiga() != 1)
				continue;
			spiele.add(s);
		}

		// sortieren nach Spieltag
		spiele.sort(new Comparator<Spiel>() {
			@Override
			public int compare(Spiel o1, Spiel o2) {
				return Integer.compare(o1.getSpieltag(), o2.getSpieltag());
			}
		});

		// jetzt ueber die Spiele gehen, und immer bei Spieltagwechsel die Tabelle berechnen
		int spieltag = spiele.get(0).getSpieltag();

		// vereinsId --> (Punkte, Erzielt, Erhalten)
		Map<Integer, Triple<Integer, Integer, Integer>> nachVerein = new HashMap<>();
		List<Integer> listenplaetze = new LinkedList<>();
		for (Spiel s : spiele) {
			// Spieltagwechsel!
			if (s.getSpieltag() != spieltag) {
				// aktuelle Punktestaende
				listenplaetze.add(listenPlatz(nachVerein));
				spieltag = s.getSpieltag();
			}

			// sicherstellen, dass Map "Null"-Werte hat
			if (!nachVerein.containsKey(s.getHeim()))
				nachVerein.put(s.getHeim(), Triple.of(0, 0, 0));
			if (!nachVerein.containsKey(s.getGast()))
				nachVerein.put(s.getGast(), Triple.of(0, 0, 0));

			// Gastgeber
			Triple<Integer, Integer, Integer> alt1 = nachVerein.get(s.getHeim());
			Triple<Integer, Integer, Integer> delta1 = result(s, true);
			nachVerein.put(s.getHeim(), Triple.of(alt1.getLeft() + delta1.getLeft(),
					alt1.getMiddle() + delta1.getMiddle(),
					alt1.getRight() + delta1.getRight()));

			// Gastgeber
			Triple<Integer, Integer, Integer> alt2 = nachVerein.get(s.getGast());
			Triple<Integer, Integer, Integer> delta2 = result(s, false);
			nachVerein.put(s.getGast(), Triple.of(alt2.getLeft() + delta2.getLeft(),
					alt2.getMiddle() + delta2.getMiddle(),
					alt2.getRight() + delta2.getRight()));
		}

		// noch den letzten.
		listenplaetze.add(listenPlatz(nachVerein));

		System.out.println(listenplaetze);
	}

	private static int listenPlatz(Map<Integer, Triple<Integer, Integer, Integer>> nachVerein) {
		List<Map.Entry<Integer, Triple<Integer, Integer, Integer>>> tabelle = new LinkedList<>(nachVerein.entrySet());

		// sortiere nach Punkten und Tordiff absteigend
		tabelle.sort(new Comparator<Map.Entry<Integer, Triple<Integer, Integer, Integer>>>() {
			@Override
			public int compare(Map.Entry<Integer, Triple<Integer, Integer, Integer>> o1, Map.Entry<Integer, Triple<Integer, Integer, Integer>> o2) {
				// bei Punktegleichstand: Tordifferenz!
				if (o1.getValue().getLeft() == o2.getValue().getLeft())
					return -Integer.compare(o1.getValue().getMiddle()-o1.getValue().getRight(),
							o2.getValue().getMiddle()-o2.getValue().getRight());
				else
					return -Integer.compare(o1.getValue().getLeft(), o2.getValue().getLeft());
			}
		});

		// finde Position von Verein 18
		int p = 1;
		for (Map.Entry<Integer, Triple<Integer, Integer, Integer>> e : tabelle) {
			if (e.getKey() == 18)
				return p;
			p++;
		}

		// nie ausgefuehrt
		return 0;
	}

	/**
	 * Wer hat die [Rote Laterne](https://de.wikipedia.org/wiki/Lanterne_Rouge) in jeweils der 1., 2. und 3. Liga?
	 */
	@Test
	void ligaRoteLaternen() throws IOException {
		Bundesliga bl = Bundesliga.loadFromResource();

		// Lösung: Koeln, Kaiserslautern, Erfurt
		Integer[] laternen = {17, 36, 55};

		// jede Liga separat
		for (int liga = 1; liga <= 3; liga++) {
			// vereinsId --> (Punkte, Erzielt, Erhalten)
			Map<Integer, Triple<Integer, Integer, Integer>> nachVerein = new HashMap<>();
			List<Integer> listenplaetze = new LinkedList<>();
			for (Spiel s : bl.spiele) {
				if (bl.vereine.get(s.getHeim()).getLiga() != liga)
					continue;

				// sicherstellen, dass Map "Null"-Werte hat
				if (!nachVerein.containsKey(s.getHeim()))
					nachVerein.put(s.getHeim(), Triple.of(0, 0, 0));
				if (!nachVerein.containsKey(s.getGast()))
					nachVerein.put(s.getGast(), Triple.of(0, 0, 0));

				// Gastgeber
				Triple<Integer, Integer, Integer> alt1 = nachVerein.get(s.getHeim());
				Triple<Integer, Integer, Integer> delta1 = result(s, true);
				nachVerein.put(s.getHeim(), Triple.of(alt1.getLeft() + delta1.getLeft(),
						alt1.getMiddle() + delta1.getMiddle(),
						alt1.getRight() + delta1.getRight()));

				// Gastgeber
				Triple<Integer, Integer, Integer> alt2 = nachVerein.get(s.getGast());
				Triple<Integer, Integer, Integer> delta2 = result(s, false);
				nachVerein.put(s.getGast(), Triple.of(alt2.getLeft() + delta2.getLeft(),
						alt2.getMiddle() + delta2.getMiddle(),
						alt2.getRight() + delta2.getRight()));
			}

			// sortieren, diesmal aufsteigend
			List<Map.Entry<Integer, Triple<Integer, Integer, Integer>>> tabelle = new LinkedList<>(nachVerein.entrySet());

			// sortiere nach Punkten und Tordiff absteigend
			tabelle.sort(new Comparator<Map.Entry<Integer, Triple<Integer, Integer, Integer>>>() {
				@Override
				public int compare(Map.Entry<Integer, Triple<Integer, Integer, Integer>> o1, Map.Entry<Integer, Triple<Integer, Integer, Integer>> o2) {
					// bei Punktegleichstand: Tordifferenz!
					if (o1.getValue().getLeft().equals(o2.getValue().getLeft()))
						return Integer.compare(o1.getValue().getMiddle()-o1.getValue().getRight(),
								o2.getValue().getMiddle()-o2.getValue().getRight());
					else
						return Integer.compare(o1.getValue().getLeft(), o2.getValue().getLeft());
				}
			});

			// der Tabellen"erste" ist jetzt also der mit der roten Laterne...
			System.out.println(bl.vereine.get(tabelle.get(0).getKey()) + " " + tabelle.get(0).getValue());

			assertEquals(laternen[liga-1], tabelle.get(0).getKey());
		}
	}

	@Test
	void ligaTabellenFuehrung() throws IOException {
		Bundesliga bl = Bundesliga.loadFromResource();

		// erstmal alle Spiele der ersten Liga
		List<Spiel> spiele = Abstraktion.filtern(bl.spiele, new Predicate<Spiel>() {
			@Override
			public boolean test(Spiel spiel) {
				return bl.vereine.get(spiel.getHeim()).getLiga() == 1;
			}
		});

		// sortieren nach Spieltag
		spiele.sort(new Comparator<Spiel>() {
			@Override
			public int compare(Spiel o1, Spiel o2) {
				return Integer.compare(o1.getSpieltag(), o2.getSpieltag());
			}
		});

		// jetzt ueber die Spiele gehen, und immer bei Spieltagwechsel die Tabelle berechnen
		int spieltag = spiele.get(0).getSpieltag();

		// vereinsId --> (Punkte, Erzielt, Erhalten)
		Map<Integer, Triple<Integer, Integer, Integer>> nachVerein = new HashMap<>();

		List<Integer> tabellenFuehrer = new LinkedList<>();

		BinaryOperator<Map.Entry<Integer, Triple<Integer, Integer, Integer>>> max = new BinaryOperator<Map.Entry<Integer, Triple<Integer, Integer, Integer>>>() {
			@Override
			public Map.Entry<Integer, Triple<Integer, Integer, Integer>> apply(Map.Entry<Integer, Triple<Integer, Integer, Integer>> a, Map.Entry<Integer, Triple<Integer, Integer, Integer>> b) {
				// nach Punkten
				int c = Integer.compare(a.getValue().getLeft(), b.getValue().getLeft());

				if (c < 0)
					return b;
				else if (c > 0)
					return a;

				// nach Torverhaeltnis
				c = Integer.compare(a.getValue().getMiddle()-a.getValue().getRight(), b.getValue().getMiddle()-b.getValue().getRight());
				if (c < 0)
					return b;
				else if (c > 0)
					return a;

				// nach erzielten Toren
				c = Integer.compare(a.getValue().getMiddle(), b.getValue().getMiddle());
				if (c < 0)
					return b;
				else if (c > 0)
					return a;

				// Muenzwurf...
				if (new Random().nextBoolean())
					return a;
				else
					return b;
			}
		};

		for (Spiel s : spiele) {
			// Spieltagwechsel, berechne Tabelle
			if (s.getSpieltag() != spieltag) {
				// aktuelle Punktestaende
				List<Map.Entry<Integer, Triple<Integer, Integer, Integer>>> tabelle = new LinkedList<>(nachVerein.entrySet());

				// Reduktion auf besten (--> maximumsuche)
				Map.Entry<Integer, Triple<Integer, Integer, Integer>> tf = Abstraktion.reduzieren(tabelle, tabelle.get(0), max);

				tabellenFuehrer.add(tf.getKey());

				spieltag = s.getSpieltag();
			}

			// sicherstellen, dass Map "Null"-Werte hat
			if (!nachVerein.containsKey(s.getHeim()))
				nachVerein.put(s.getHeim(), Triple.of(0, 0, 0));
			if (!nachVerein.containsKey(s.getGast()))
				nachVerein.put(s.getGast(), Triple.of(0, 0, 0));

			// Gastgeber
			Triple<Integer, Integer, Integer> alt1 = nachVerein.get(s.getHeim());
			Triple<Integer, Integer, Integer> delta1 = result(s, true);
			nachVerein.put(s.getHeim(), Triple.of(alt1.getLeft() + delta1.getLeft(),
					alt1.getMiddle() + delta1.getMiddle(),
					alt1.getRight() + delta1.getRight()));

			// Gastgeber
			Triple<Integer, Integer, Integer> alt2 = nachVerein.get(s.getGast());
			Triple<Integer, Integer, Integer> delta2 = result(s, false);
			nachVerein.put(s.getGast(), Triple.of(alt2.getLeft() + delta2.getLeft(),
					alt2.getMiddle() + delta2.getMiddle(),
					alt2.getRight() + delta2.getRight()));
		}

		// noch den letzten.
		List<Map.Entry<Integer, Triple<Integer, Integer, Integer>>> tabelle = new LinkedList<>(nachVerein.entrySet());
		Map.Entry<Integer, Triple<Integer, Integer, Integer>> tf = Abstraktion.reduzieren(tabelle, tabelle.get(0), max);
		tabellenFuehrer.add(tf.getKey());

		System.out.println(tabellenFuehrer);
		List<Integer> uniq = Abstraktion.reduzieren(tabellenFuehrer, new LinkedList<>(), new BiFunction<LinkedList<Integer>, Integer, LinkedList<Integer>>() {
			@Override
			public LinkedList<Integer> apply(LinkedList<Integer> objects, Integer integer) {
				if (objects.size() == 0 || !objects.getLast().equals(integer))
					objects.add(integer);
				return objects;
			}
		});

		System.out.println(uniq);
	}
}