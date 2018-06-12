package ch11.schlecht;

public class Geschaeftshaus {
	private String besitzer;
	private String addresse;
	private int anzahlBueroraeume;
	private int anzahlStockwerke;
	private boolean hatAufzug;
	private boolean hatTiefgarage;
	private String baujahr;
	private int preis;

	public int getPreis() {
		return preis;
	}

	public int getAnzahlBueroraeume() {
		return anzahlBueroraeume;
	}
}
