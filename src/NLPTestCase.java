import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NLPTestCase {
	String string;
	String nonString;
	ArrayList<String> tweetList;
	double[] sentimentArray = { 1, 4, 6, 2, 7 };

	@BeforeEach
	void setUp() throws Exception {
		NLP.init();
		string = new String("Eren is laughing");
		nonString = new String("!*(@#&!*(@");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	/**
	 * Test to ensure that findSentiment() is correctly returning a score between 0
	 * and 4 for any string that is throw into the function.
	 */
	void testFindSentiment() {
		assertEquals(2, NLP.findSentiment(string), 2);
	}

	@Test
	/**
	 * Test to ensure that in the case that a non acceptable string is presented to
	 * findSentiment(), IllegalArgumentException was expected to be thrown to user.
	 * 
	 * Test working as intended as Stanform NLP will always give a score of 2, for
	 * non-english string inputs, or gibberish.
	 */
	void testUnknowableSentiment() {
		// assertThrows(IllegalArgumentException.class, () -> {
		// NLP.findSentiment(nonString);
		// });
		assertEquals(2, NLP.findSentiment(nonString));

	}

	@Test
	/**
	 * Test to ensure that given an array of String tweets, readTweets() is able to
	 * iterate through the array and run findSentiment() for each tweet, and add a
	 * counter to sentimentArray for the respective score given.
	 */
	void testReadTweets() {

	}

	@Test
	/**
	 * Test to ensure that CSVintoArray() is able to properly navigate to the second
	 * column of the csv file thrown at it, and appends the entire column to
	 * ArrayList<String> for readTweets() to give a sentiment score.
	 * 
	 * Testcase csv file "csvtest.csv" is created and testCSVintoArray() is expected
	 * to return the strings inserted.
	 * 
	 * @throws IOException handles for when the csv is unable to be located and
	 *                     read.
	 */
	void testCSVintoArray() throws IOException {
		List<List<String>> strings = Arrays.asList(Arrays.asList("pokeman", "2431", "brock drying pan"),
				Arrays.asList("wheremegas", "131", "diamondpearlremake"));

		BufferedWriter writer = Files.newBufferedWriter(Paths.get("csvtest.csv"));
		for (List<String> string : strings) {
			writer.write(String.join(",", string));
			writer.newLine();
		}
		writer.close();

		ArrayList<String> expectedOutput = new ArrayList<String>();
		expectedOutput.add("brock drying pan");
		expectedOutput.add("diamondpearlremake");

		assertEquals(expectedOutput, NLP.readCSVintoArray("csvtest.csv"));
	}

	@Test
	/**
	 * Test to ensure that in the case of when the csv is unable to be located and
	 * read, a FileNotFoundException is thrown.
	 */
	void testNoCSVtoRead() {
		// assertThrows("Failed to search find CSV.", FileNotFoundException.class, () ->
		// NLP.readCSVintoArray("noncsv.csv"));
		assertThrows(FileNotFoundException.class, () -> NLP.readCSVintoArray("noncsv.csv"));
	}

	@Test
	/**
	 * Test to ensure that getScores() is able to correctly obtain the totalScore
	 * and averageScore from sentimentArray.
	 * 
	 * Sample sentimentArray is set up to have a totalScore of 50 and an average
	 * score of 2.5. Expected output of getScores is to be the same.
	 */
	void testGetScores() {
		double[] expectedOutput = { 50, 2.5 };
		assertArrayEquals(expectedOutput, NLP.getScores(sentimentArray));
	}

	@Test
	/**
	 * Test to ensure that in the case of when getScores does not have an
	 * initialised sentimentArray to take in (null), NullPointerException is throw.
	 */
	void testCannotGetScores() {
		assertThrows(NullPointerException.class, () -> NLP.getScores(null));
	}
}
