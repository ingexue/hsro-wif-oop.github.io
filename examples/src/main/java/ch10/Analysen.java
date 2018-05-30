package ch10;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Analysen {
	public static void sortieren() throws IOException {
		Bundesliga b = Bundesliga.loadFromResource();

		System.out.println("Nach Einlesen:");
		for (Map.Entry<Integer, Verein> e : b.vereine.entrySet())
			System.out.println(e.getValue());

		List<Verein> nachName = new LinkedList<>(b.vereine.values());
		nachName.sort(new Comparator<Verein>() {
			@Override
			public int compare(Verein o1, Verein o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		System.out.println("Nach Sortieren:");
		for (Verein v : nachName)
			System.out.println(v);

		List<Verein> nachLigaName = new LinkedList<>(b.vereine.values());
		nachLigaName.sort(new Comparator<Verein>() {
			@Override
			public int compare(Verein o1, Verein o2) {
				if (o1.getLiga() == o2.getLiga())
					return o1.getName().compareTo(o2.getName());
				else
					return Integer.compare(o1.getLiga(), o2.getLiga());
			}
		});

		System.out.println("Nach Sortieren:");
		for (Verein v : nachLigaName)
			System.out.println(v);
	}

	public static void filtern() throws IOException {
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

		for (Verein v : zweiteLiga) {
			System.out.println(v);
		}
	}

	public static void abbilden() throws IOException {
		Bundesliga b = Bundesliga.loadFromResource();

		List<Triple<String, String, String>> paarungen = new LinkedList<>();
		for (Spiel s : b.spiele) {
			Verein heim = b.vereine.get(s.getHeim());
			Verein gast = b.vereine.get(s.getGast());
			paarungen.add(Triple.of(s.getDatum(), heim.getName(), gast.getName()));
		}

		for (Triple<String, String, String> p : paarungen) {
			System.out.println(p);
		}
	}

	public static void reduzieren() throws IOException {
		Bundesliga b = Bundesliga.loadFromResource();

		int tore = 0;
		for (Spiel s : b.spiele) {
			tore = tore + s.getToreGast() + s.getToreHeim();
		}

		System.out.println("Es fielen insgesamt " + tore + " Tore in " + b.spiele.size() + " Spielen");
	}

	public static void tabellenStandStuttgartStreams() throws IOException {
		Bundesliga bl = Bundesliga.loadFromResource();

		final int team = 8; // VfB Stuttgart
		Pair<Integer, Integer> scores = bl.spiele.stream()
				// nur Spiele mit Stuttgart
				.filter(s -> s.getHeim() == team || s.getGast() == team)
				// bilde ab: Spiel --> Paar von Punktgewinn und Tordifferenz (pro Spiel)
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

		System.out.println(scores);
	}
}
