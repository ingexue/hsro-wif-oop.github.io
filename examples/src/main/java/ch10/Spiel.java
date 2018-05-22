package ch10;

import javax.persistence.Column;
import java.util.Map;

public class Spiel {
	private Spiel() {

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

	public static Spiel fromCSV(String[] vals) {
		// Spiel_ID;Spieltag;Datum;Uhrzeit;Heim;Gast;Tore_Heim;Tore_Gast;

		Spiel s = new Spiel();
		s.id = Integer.parseInt(vals[0]);
		s.spieltag = Integer.parseInt(vals[1]);
		s.isodatum = vals[2];
		s.isouhrzeit = vals[3];
		s.heim = Integer.parseInt(vals[4]);
		s.gast = Integer.parseInt(vals[5]);
		s.toreHeim = Integer.parseInt(vals[6]);
		s.toreGast = Integer.parseInt(vals[7]);

		return s;
	}
}
