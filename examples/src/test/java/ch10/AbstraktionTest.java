package ch10;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class AbstraktionTest {

	@Test
	void testFiltern() throws IOException {
		Bundesliga b = Bundesliga.loadFromResource();
		List<Verein> zweiteLiga = Abstraktion.filtern(new LinkedList<>(b.vereine.values()), new Predicate<Verein>() {
			public boolean test(Verein v) {
				return v.getLiga() == 2;
			}
		});

		System.out.println(zweiteLiga);
	}

	@Test
	void testAbbilden() throws IOException {
		Bundesliga b = Bundesliga.loadFromResource();

		List<Triple<String, String, String>> paarungen = Abstraktion.abbilden(b.spiele, new Function<Spiel, Triple<String, String, String>>() {
			@Override
			public Triple<String, String, String> apply(Spiel spiel) {
				Verein heim = b.vereine.get(spiel.getHeim());
				Verein gast = b.vereine.get(spiel.getGast());
				return Triple.of(spiel.getDatum(), heim.getName(), gast.getName());
			}
		});

		System.out.println(paarungen);
	}

	@Test
	void testReduzieren() {
		List<Integer> a = Arrays.asList(1, 2, 3);

		Integer summe = Abstraktion.reduzieren(a,
				0, // was ist das "Null"-Element?
				new BinaryOperator<Integer>() {
					public Integer apply(Integer a, Integer b) {
						return a + b;
					}
				});

		System.out.println(summe);
	}

	@Test
	void testReduzieren2() throws IOException {
		Bundesliga b = Bundesliga.loadFromResource();

		Integer tore = Abstraktion.reduzieren(b.spiele, 0, new BiFunction<Integer, Spiel, Integer>() {
					@Override
					public Integer apply(Integer integer, Spiel spiel) {
						return integer + spiel.getToreGast() + spiel.getToreHeim();
					}
				});

		System.out.println("Es fielen insgesamt " + tore + " Tore in " + b.spiele.size() + " Spielen");
	}
}