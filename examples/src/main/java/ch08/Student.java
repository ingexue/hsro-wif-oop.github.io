package ch08;

import com.google.gson.annotations.SerializedName;

class Student {
	private int matrikel;

	@Deprecated
	void vorbereiten() {
		System.out.println("Fleissig fleissig");
	}

	@SerializedName("fullname")
	private String name;

	Student(int matrikel, String name) {
		this.matrikel = matrikel;
		this.name = name;
	}

	@Override
	public String toString() {
		return String.format("%s (%d)", name, matrikel);
	}
}
