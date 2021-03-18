import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NLPTestCase {
	String string;
	String nonString;
	ArrayList<String> tweetList;
	double[] sentimentArray;

	@BeforeEach
	void setUp() throws Exception {
		NLP.init();
		string = new String("Eren is laughing");
		nonString = new String("!*(@#&!*(@");
		double[] sentimentArray = { 1, 4, 6, 2, 7 };

	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testFindSentiment() {
		assertEquals(2, NLP.findSentiment(string), 2);
	}

	@Test
	void testNonSentiment() {
		assertThrows(IllegalArgumentException.class, () -> {
			NLP.findSentiment(nonString);
		});
	}

	@Test
	void testGetScores() {
		double[] expectedOutput = {48, 19, 2.5};
		assertEquals(expectedOutput, NLP.getScores(sentimentArray));
	}

	@Test
	void testCSVintoArray() {
		// assertEquals(ArrayList<String>, Brain.readCSVintoArray());
	}

	@Test
	void testNoCSVtoRead() throws IOException {
		assertEquals("Failed to search find CSV.", Brain.readCSVintoArray());
	}

}
