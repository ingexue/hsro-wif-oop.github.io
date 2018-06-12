package ch11.besser;

public class ImmoProgramm {
	public static void main(String[] args) {
		Einfamilienhaus efh = new Einfamilienhaus("Huber", "Hochschulstr. 1", 1984, 300000,
				"Stadthaus", 220, 100, false);

		Geschaeftshaus gh = new Geschaeftshaus("Meier", "Hochschulstr. 2", 1920, 100000,
				24, 3, true, true);

		// Generalisierung!
		Immobilie i = efh;
	}
}
