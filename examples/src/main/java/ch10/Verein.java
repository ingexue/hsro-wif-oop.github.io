package ch10;

import javax.persistence.Column;

public class Verein {
	public Verein(int V_ID, String name, int liga) {
		this.id = V_ID;
		this.name = name;
		this.liga = liga;
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
}
