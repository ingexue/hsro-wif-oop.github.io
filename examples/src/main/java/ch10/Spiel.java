package ch10;

import javax.persistence.Column;
import java.util.Map;

public class Spiel {
	public Spiel(int Spiel_ID, int Spieltag, String Datum, String Uhrzeit, int Heim, int Gast, int Tore_Heim, int Tore_Gast) {
		this.id = Spiel_ID;
		this.spieltag = Spieltag;
		this.isodatum = Datum;
		this.isouhrzeit = Uhrzeit;
		this.heim = Heim;
		this.gast = Gast;
		this.toreHeim = Tore_Heim;
		this.toreGast = Tore_Gast;
	}

	@Column(name="Spiel_ID")
	private int id;

	@Column(name="Spieltag")
	private int spieltag;

	@Column(name="Datum")
	private String isodatum;

	@Column(name="Uhrzeit")
	private String isouhrzeit;

	@Column(name="Heim")
	private int heim;

	@Column(name="Gast")
	private int gast;

	@Column(name="Tore_Heim")
	private int toreHeim;

	@Column(name="Tore_Gast")
	private int toreGast;

	public int getId() {
		return id;
	}

	public int getSpieltag() {
		return spieltag;
	}

	public String getDatum() {
		return isodatum;
	}

	public String getUhrzeit() {
		return isouhrzeit;
	}

	public int getHeim() {
		return heim;
	}

	public int getGast() {
		return gast;
	}

	public int getToreHeim() {
		return toreHeim;
	}

	public int getToreGast() {
		return toreGast;
	}

	public String toString() {
		return new StringBuilder()
				.append(id).append(" ")
				.append(spieltag).append(" ")
				.append(isodatum + " " + isouhrzeit).append(" ")
				.append(heim).append(" gegen ").append(gast).append(" ")
				.append(toreHeim).append(":").append(toreGast).toString();
	}

	String toString(Map<Integer, Verein> vereine) {
		return new StringBuilder()
				.append(id).append(" ")
				.append(spieltag).append(" ")
				.append(isodatum + " " + isouhrzeit).append(" ")
				.append(vereine.get(heim).getName()).append(" gegen ").append(vereine.get(gast).getName()).append(" ")
				.append(toreHeim).append(":").append(toreGast).toString();
	}
}
