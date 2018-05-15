package ch09;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SortierenTest {
	@Test
	void testInsertion() {
		int[] a = {4, 2, 3, 1};
		int[] r = {1, 2, 3, 4};

		Sortieren.isort(a);
		System.out.println(Arrays.toString(a));
		assertArrayEquals(r, a);
	}

	@Test
	void testSelection() {
		int[] a = {4, 2, 3, 1};
		int[] r = {1, 2, 3, 4};

		Sortieren.ssort(a);
		System.out.println(Arrays.toString(a));
		assertArrayEquals(r, a);
	}

	@Test
	void testMerge() {
		int[] a = {4, 2, 3, 1, 5};
		int[] r = {1, 2, 3, 4, 5};

		a = Sortieren.msort(a);
		System.out.println(Arrays.toString(a));
		assertArrayEquals(r, a);
	}

	@Test
	void testQuick() {
		int[] a = {4, 2, 3, 1, 5};
		int[] r = {1, 2, 3, 4, 5};

		Sortieren.qsort(a);
		System.out.println(Arrays.toString(a));
		assertArrayEquals(r, a);
	}
}