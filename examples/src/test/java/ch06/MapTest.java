package ch06;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapTest {
	@Test
	void testTreeMap() {
		Map<String, Integer> zaehler = new TreeMap<>();

		for (String s : "In Ulm und um Ulm und um Ulm herum".split(" ")) {
			int z = 0;
			// bisherigen Wert holen, sofern vorhanden
			if (zaehler.containsKey(s))
				z = zaehler.get(s);
			zaehler.put(s, z+1);
		}

		assertEquals(3, (int) zaehler.get("Ulm"));
		assertEquals(2, (int) zaehler.get("und"));
		assertEquals(1, (int) zaehler.get("herum"));
	}
}