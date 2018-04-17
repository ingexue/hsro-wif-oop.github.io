package ch06;

import java.util.*;

class Datenstrukturen {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		System.out.println("How many entries?");
		int howMany = Integer.parseInt(sc.nextLine());

		List<String> entries = new LinkedList<>();
		Set<String> set = new TreeSet<>();
		for (int i = 0; i < howMany; i++) {
			System.out.println("Please enter entry " + (i+1) + ":");
			String e = sc.nextLine();

			if (set.contains(e)) {
				System.out.println("Sorry, already in use; please try again.");
				i--;
				continue;
			}

			entries.add(e);
			set.add(e);
		}

		System.out.println("You entered: " + entries);
	}
}
