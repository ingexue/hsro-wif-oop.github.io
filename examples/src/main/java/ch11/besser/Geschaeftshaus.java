package ch11.besser;

public class Geschaeftshaus extends Immobilie {
	private int anzahlBueroraeume;
	private int anzahlStockwerke;
	private boolean hatAufzug;
	private boolean hatTiefgarage;

	public Geschaeftshaus(String besitzer, String adresse, int baujahr, int verkaufspreis,
	                      int anzahlBueroraeume, int anzahlStockwerke, boolean hatAufzug, boolean hatTiefgarage) {
		super(besitzer, adresse, baujahr, verkaufspreis);

		this.anzahlBueroraeume = anzahlBueroraeume;
		this.anzahlStockwerke = anzahlStockwerke;
		this.hatAufzug = hatAufzug;
		this.hatTiefgarage = hatTiefgarage;
	}

	public int getAnzahlBueroraeume() {
		return anzahlBueroraeume;
	}

	public int getAnzahlStockwerke() {
		return anzahlStockwerke;
	}

	public boolean isHatAufzug() {
		return hatAufzug;
	}

	public boolean isHatTiefgarage() {
		return hatTiefgarage;
	}
}
