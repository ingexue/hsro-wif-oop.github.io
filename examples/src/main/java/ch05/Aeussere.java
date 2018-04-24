package ch05;

class Aeussere {
	private String attribut = "A";
	private static String ATTRIBUT = "B";

	class Innere {
		String attribut = "X";
		// static String ATTRIBUT = "Y";
		void zugriff() {
			System.out.println(this.attribut);  // "X"
			System.out.println(Aeussere.this.attribut);  // "A"
		}
	}

	static class Statische {
		String attribut = "Z";
		void zugriff() {
			System.out.println(attribut);  // "Z"

			// Compilerfehler!
			// System.out.println(Aeussere.this.attribut);
		}
	}

	public static void main(String[] args) {
		Aeussere a = new Aeussere();


	}
}
