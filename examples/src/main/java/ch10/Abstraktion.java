package ch10;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.*;

class Abstraktion {
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
}
