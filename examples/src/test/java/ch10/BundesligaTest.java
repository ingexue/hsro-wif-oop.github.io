package ch10;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class BundesligaTest {
	@Test
	void loadFromResource() throws IOException, URISyntaxException {
		Bundesliga bl = Bundesliga.loadFromResource();
	}
}