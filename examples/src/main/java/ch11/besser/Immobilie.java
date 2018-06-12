package ch11.besser;

public class Immobilie {
	private String besitzer;
	private String adresse;
	private int baujahr;
	private int verkaufspreis;

	public Immobilie(String besitzer, String adresse, int baujahr, int verkaufspreis) {
		this.besitzer = besitzer;
		this.adresse = adresse;
		this.baujahr = baujahr;
		this.verkaufspreis = verkaufspreis;
	}


	public String getBesitzer() {
		return besitzer;
	}

	public String getAdresse() {
		return adresse;
	}

	public int getBaujahr() {
		return baujahr;
	}

	public int getVerkaufspreis() {
		return verkaufspreis;
	}
}
