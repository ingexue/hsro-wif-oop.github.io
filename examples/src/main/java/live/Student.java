package live;

class Student {
	private int matrikel;
	private String name;

	Student(int matrikel, String name) {
		this.matrikel = matrikel;
		this.name = name;
	}

	public String toString() {
		return String.format("%s (%d)", name, matrikel);
	}
}
