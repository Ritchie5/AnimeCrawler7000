import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations.SentimentAnnotatedTree;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

/**
 * To obtain a general sentiment of the topic searched for, by reading the CSV output file of tweets and through Stanford’s CoreNLP.
 * @author Team Pikachuuuuuuu
 * @version 1.8
 * @since 1.0
 */
public class NLP {

	/**
	 * Static variables to ensure that they are global to allow NLP functions to
	 * work even when called from other classes.
	 */
	static StanfordCoreNLP pipeline;
	static double tweetCount = 0;
	static double[] sentimentArray = { 0, 0, 0, 0, 0 };
	static String csv = "animeCrawler7000.csv";
	// static double[] sentimentArray = { 0, 9, 15, 6, 0 };

	/**
	 * Reference function of the order in which they have to be run. Formerly the
	 * 'main' function of NLP during development and testing of NLP functions.
	 * 
	 * @param args Unused
	 * @throws IOException for readCSVintoArray in the case of which csv is not
	 *                     found or cannot be read.
	 */
	public static void startNLP(String[] args) throws IOException {
		/**  */
		NLP.init();
		ArrayList<String> tweets = NLP.readCSVintoArray(csv);
		NLP.readTweets(tweets);
		NLP.getScores(sentimentArray);
		NLP.getPercentage(sentimentArray);
	}

	/**
	 * Prerequisite function to run before the other NLP functions can work.
	 * One-time run to init annotators into pipeline before findSentiment() can
	 * work.
	 * 
	 * 
	 * props    allows properties such as annotators to use for sentiment
	 *          analysis. Setting untokenizable=noneDelete disables warning
	 *          of unicode that NLP is able to handle correctly
	 * pipeline holds all the annotations set in props and passes it into
	 *          CoreMap later on
	 */

	public static void init() {
		// pipeline = new StanfordCoreNLP("MyPropFile.properties");
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
		// props.setProperty("annotators", "tokenize, ssplit, pos, depparse, sentiment");
		props.setProperty("tokenize.options", "untokenizable=noneDelete");
		pipeline = new StanfordCoreNLP(props);
	}

	/**
	 * sentiment calculating method for a string
	 * 
	 * pipeline annotations chosen stored in pipeline to apply in sentiment
	 *          calculation
	 * @param tweet    string to be used in finding sentiment score
	 * @return mainSentiment: sentiment score determined for tweet
	 */
	public static int findSentiment(String tweet) {
		int mainSentiment = 0;
		if (tweet != null && tweet.length() > 0) {
			int longest = 0;
			Annotation annotation = pipeline.process(tweet);
			for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
				Tree tree = sentence.get(SentimentAnnotatedTree.class);
				int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
				String partText = sentence.toString();
				if (partText.length() > longest) {
					mainSentiment = sentiment;
					longest = partText.length();
				}
			}
		}
		return mainSentiment;
	}

	/**
	 * Iterate through tweets ArrayList and running findSentiment for that tweet to
	 * obtain a score scores range from 0 to 4, with 0 being the most negative and 4
	 * being the most positive. For each score, add a counter to the respective
	 * index of the array.
	 * 
	 * @param tweets single string to find sentiment score.
	 */
	public static void readTweets(ArrayList<String> tweets) {
		for (String tweet : tweets) {
			/** print "score : tweet content" and adds counter to score in sentimentArray */
			System.out.println(findSentiment(tweet) + " : " + tweet);
			sentimentArray[findSentiment(tweet)]++;
		}
	}

	/**
	 * Takes in sentimentArray and iterates through its indexes to calculate
	 * totalScore, tweetCount and averageScore, and returns them.
	 * 
	 * @param array        takes in sentimentArray and iterates through to obtain
	 *                     scores for each index.
	 * score -      Counter initialised as 0, incremented for index parsed in
	 *              sentimentArray
	 * totalScore - Each iteration will check the index of the score and then
	 *              multiplying the index of the score with the value in that
	 *              index.
	 * tweetCount - With each iteration, the counter for total tweets is also
	 *              incremented.
	 * @param array (averageScore Obtained by dividing totalScore by tweetCount)
	 * @return totalScore, averageScore values in an array
	 */
	public static double[] getScores(double[] array) {
		double score = 0;
		// double tweetCount = 0;
		double totalScore = 0;
		for (double i : array) {
			System.out.println("Score " + (int) score + ": " + i);
			totalScore += i * score;
			tweetCount += i;
			score++;
		}
		double averageScore = totalScore / tweetCount;
		System.out.println("Total Score: " + (int) totalScore);
		System.out.println("Total Tweets: " + (int) tweetCount);
		System.out.println("Average Score: " + averageScore);
		return new double[] { totalScore, averageScore };
	}

	/**
	 * Going through the indexes from sentimentArray to calculate percentage of
	 * tweets that are positive, negative and neutral.
	 * 
	 * Scores 0 and 1 are negative, 2 are neutral, 3 and 4 are positive. For each
	 * negative, neutral and positive, a percentage (already multiplied to be a
	 * whole number value from 0-100) will be returned.
	 * 
	 * @param sentimentArray iterated through once again its indexes to obtain
	 * @return negPer, neuPer, posPer values in an array
	 */

	// public static double[] getPercentage(double[] sentimentArray, double[]
	// scoreArray) {
	public static double[] getPercentage(double[] sentimentArray) {
		double negPer = (sentimentArray[0] + sentimentArray[1]) / tweetCount * 100;
		double neuPer = sentimentArray[2] / tweetCount * 100;
		double posPer = (sentimentArray[3] + sentimentArray[4]) / tweetCount * 100;
		System.out.println("Percentage Negative: " + negPer + "%");
		System.out.println("Percentage Neutral: " + neuPer + "%");
		System.out.println("Percentage Positive: " + posPer + "%");
		// System.out.println("TweetCount: " + tweetCount);
		return new double[] { negPer, neuPer, posPer };
	}

	/**
	 * Reads the csv file generated by the twittercrawler (animecrawler7000.csv in
	 * this case), and appends the column with tweet text into the ArrayList<String>
	 * tweetList to be read by readTweets().
	 * 
	 * @param csv new ArrayList<String> initialised to store the list of
	 *                  tweets to go through sentiment analysis.
	 * @return tweetList (ArrayList<String> of tweet text in tweetList for readTweets() to
	 *         analyse.)
	 * @throws IOException to handle situations where the csv could not be found and
	 *                     read.
	 */
	public static ArrayList<String> readCSVintoArray(String csv) throws IOException {
		ArrayList<String> tweetList = new ArrayList<String>();
		// try {
		BufferedReader br = new BufferedReader(new FileReader(csv));
		String line;

		while ((line = br.readLine()) != null) {
			String[] cols = line.split(",");
			tweetList.add(cols[2]);
		}
		br.close();
		// } catch (FileNotFoundException e) {
		// System.out.println("Failed to search find CSV.");
		// e.printStackTrace();
		// }
		return tweetList;
	}
}