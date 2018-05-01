package ch07;

import java.util.HashMap;
import java.util.Map;

class Rekursion {
	static int fak(int n) {
		if (n == 1)
			return 1;
		else
			return n * fak(n-1);
	}

	static int fib(int n) {
		if (n == 0)
			return 0;
		else if (n == 1)
			return 1;
		else
			return fib(n-1) + fib(n-2);
	}

	static int ggT(int a, int b) {
		if (a == b)
			return a;
		else if (a < b)
			return ggT(a, b-a);
		else
			return ggT(a - b, b);
	}

	static private Map<Integer, Integer> cache = new HashMap<>();

	static int fibCached(int n) {
		if (n == 0)
			return 0;
		else if (n == 1)
			return 1;
		else if (cache.containsKey(n))
			return cache.get(n);
		else {
			int a = fibCached(n-1);
			int b = fibCached(n-2);

			if (!cache.containsKey(n-1))
				cache.put(n-1, a);
			if (!cache.containsKey(n-2))
				cache.put(n-2, b);

			return a + b;
		}
	}

	static int fibBesser(int n) {
		return fibHilf(n, 0, 1);
	}

	private static int fibHilf(int n, int a, int b) {
		if (n == 0)
			return a;
		else
			return fibHilf(n-1, b, a+b);
	}

	static int ggTit(int a, int b) {
		while (b != 0) {
			if (a > b)
				a = a - b;
			else
				b = b - a;
		}

		return a;
	}

	static boolean istPalindrom(String s) {
		for (int i = 0; i < s.length()/2; i++)
			if (s.charAt(i) != s.charAt(s.length()-1-i))
				return false;
		return true;
	}

	static boolean istPalindromRek(String s) {
		if (s.length() < 2)
			return true;
		else if (s.charAt(0) != s.charAt(s.length() - 1))
			return false;
		else
			return istPalindrom(s.substring(1, s.length() - 1));
	}

	static <T extends Comparable<T>> int binarySearch(T[] sorted, T value) {
		return binarySearchHelp(sorted, value, 0, sorted.length);
	}

	private static <T extends Comparable<T>> int binarySearchHelp(T[] sorted, T value, int from, int to) {
		if (from >= to)
			return -1;
		int p = from + (to - from) / 2;
		int c = value.compareTo(sorted[p]);

		if (c == 0)
			return p;
		if (c < 0)
			return binarySearchHelp(sorted, value, from, p);
		else
			return binarySearchHelp(sorted, value, p+1, to);
	}

	private static <T extends Comparable<T>> int binarySearchIt(T[] sorted, T value) {

		int from = 0;
		int to = sorted.length;
		while (from < to) {
			int p = from + (to - from) / 2;
			int c = value.compareTo(sorted[p]);

			if (c == 0)
				return p;

			if (c < 0)
				to = p;
			else
				from = p + 1;
		}

		return -1;
	}

	public static void main(String[] args) {
		System.out.println(fak(4));
		System.out.println(fak(7));

		for (int i = 0; i < 5; i++)
			System.out.println(fib(i));

		// System.out.println(fib(70));
		System.out.println(fibCached(70));
		System.out.println(fibBesser(70));

		System.out.println(ggTit(12, 24));
		System.out.println(ggTit(16, 38));

		System.out.println(ggT(12, 24));
		System.out.println(ggT(16, 38));

		System.out.println(istPalindromRek("einnegermitgazellezagtimregennie"));
		System.out.println(istPalindromRek("ana"));
		System.out.println(istPalindromRek("a"));


		Integer[] vals = {1, 2, 5, 6, 8, 9};
		System.out.println(binarySearch(vals, 2));
		System.out.println(binarySearch(vals, 9));
		System.out.println(binarySearchIt(vals, 5));
	}
}
