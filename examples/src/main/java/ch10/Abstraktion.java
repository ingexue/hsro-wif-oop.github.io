package ch10;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.*;

class Abstraktion {

	static <T> void fuerJedes(Collection<T> coll, Consumer<T> cons) {
		for (T t : coll)
			cons.accept(t);
	}

	static <T> List<T> filtern(List<T> liste, Predicate<T> pred) {
		List<T> gefiltert = new LinkedList<>();

		for (T v : liste) {
			if (pred.test(v))
				gefiltert.add(v);
		}

		return gefiltert;
	}

	static <T, R> List<R> abbilden(Collection<T> liste, Function<T, R> func) {
		List<R> abgebildet = new LinkedList<>();

		for (T v : liste)
			abgebildet.add(func.apply(v));

		return abgebildet;
	}

	static <T> T reduzieren(Collection<T> liste, T identity, BinaryOperator<T> op) {
		T a = identity;
		for (T t : liste)
			a = op.apply(a, t);
		return a;
	}

	static <T, U> U reduzieren(Collection<T> liste, U identity, BiFunction<U, T, U> op) {
		U a = identity;

		for (T t : liste)
			a = op.apply(a, t);

		return a;

	}

	public static void main(String... args) {
		List<Integer> li = Arrays.asList(4, 2, 4, 5, 5, 1, 2, 3);

		List<Integer> duplikatfrei = reduzieren(li, new LinkedList<Integer>(),
				new BiFunction<LinkedList<Integer>, Integer, LinkedList<Integer>>() {
			public LinkedList<Integer> apply(LinkedList<Integer> l, Integer i) {
				if (!l.contains(i))
					l.add(i);
				return l;
			}
		});

		System.out.println(duplikatfrei);

		List<Integer> konseqdupfrei = reduzieren(li, new LinkedList<Integer>(),
				new BiFunction<LinkedList<Integer>, Integer, LinkedList<Integer>> () {
			public LinkedList<Integer> apply(LinkedList<Integer> l, Integer i) {
				// wenn die Liste bisher nicht leer ist,
				// oder das vorherige Element anders war
				if (l.size() == 0 || !l.getLast().equals(i))
					l.add(i);
				return l;
			}
		});

		System.out.println(konseqdupfrei);

		li = Arrays.asList(3, 5, 2, 1, 6, 7);

		int min = reduzieren(li, li.get(0), new BinaryOperator<Integer> () {
			public Integer apply(Integer a, Integer b) {
				return Integer.min(a, b);
			}
		});

		System.out.println(min);

		li = Arrays.asList(4, 2, 1, 5, 3, 6, 8);

		li = filtern(li, i -> i % 2 == 0);    // nur gerade Zahlen behalten
		li = abbilden(li, i -> i * i);               // Zahlen quadrieren
		int s = reduzieren(li, 0, (a, b) -> a + b);  // ...und aufsummieren

		System.out.println(s);
	}
}
