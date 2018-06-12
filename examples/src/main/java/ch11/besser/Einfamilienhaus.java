package ch11.besser;

public class Einfamilienhaus extends Immobilie {
	private String haustyp;
	private int wohnflaeche;
	private int gartenflaeche;
	private boolean hatSchwimmad;

	public Einfamilienhaus(String besitzer, String adresse, int baujahr, int verkaufspreis,
	                       String haustyp, int wohnflaeche, int gartenflaeche, boolean hatSchwimmbad) {
		super(besitzer, adresse, baujahr, verkaufspreis);

		this.haustyp = haustyp;
		this.wohnflaeche = wohnflaeche;
		this.gartenflaeche = gartenflaeche;
		this.hatSchwimmad = hatSchwimmbad;
	}

	public boolean isHatSchwimmad() {
		return hatSchwimmad;
	}

	public int getGartenflaeche() {
		return gartenflaeche;
	}

	public int getWohnflaeche() {
		return wohnflaeche;
	}

	public String getHaustyp() {
		return haustyp;
	}
}
