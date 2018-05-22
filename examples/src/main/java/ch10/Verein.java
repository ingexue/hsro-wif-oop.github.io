package ch10;

import javax.persistence.Column;

public class Verein {
	private Verein() {

	}

	@Column(name="V_ID")
	private int id;

	@Column(name="Name")
	private String name;

	@Column(name="Liga")
	private int liga;

	public int getId() { return id; }
	public String getName() { return name; }
	public int getLiga() { return liga; }

	public String toString() {
		return id + " " + name + " (" + liga + ")";
	}

	public static Verein fromCSV(String[] vals) {
		// V_ID;Name;Liga;

		Verein v = new Verein();

		v.id = Integer.parseInt(vals[0]);
		v.name = vals[1];
		v.liga = Integer.parseInt(vals[2]);

		return v;
	}
}
