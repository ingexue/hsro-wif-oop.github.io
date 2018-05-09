package ch08;

import com.google.gson.Gson;

public class GsonDemo {
	public static void main(String[] args) {
		Gson gson = new Gson();

		Student s = new Student(31337, "Hans Dampf");

		s.vorbereiten();

		System.out.println(gson.toJson(s));

		String serialized = "{\"matrikel\":31337,\"fullname\":\"Hans Dampf\"}";
		Student deserialized = gson.fromJson(serialized, Student.class);

		System.out.println(deserialized);
	}
}
