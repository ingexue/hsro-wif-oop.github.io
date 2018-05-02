package ch07;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaumTest {
	@Test
	void testBaum() {
		Baum<Integer> b = new Baum<>();

		System.out.println(b.size());
		b.add(1);
		b.add(2);
		b.add(3);

		System.out.println(b.size());
	}

}