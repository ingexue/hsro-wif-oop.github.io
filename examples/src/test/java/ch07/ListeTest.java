package ch07;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ListeTest {
	@Test
	void testListe() {
		Liste<Integer> li = new Liste<>();

		System.out.println(li.size());
		li.add(1);
		li.add(1);
		li.add(1);

		System.out.println(li.size());
	}
}