package ch06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Datenstrukturen {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		List<String> entries = new LinkedList<>();
		Set<String> set = new TreeSet<>();
		while (true) {
			System.out.print("Eingabe: ");
			String line = br.readLine();

			if (line == null)
				break;

			if (set.contains(line)) {
				System.out.println("Sorry, already in use; please try again.");
				continue;
			}

			entries.add(line);
			set.add(line);
		}

		System.out.println("\nYou entered: " + entries);
	}
}
