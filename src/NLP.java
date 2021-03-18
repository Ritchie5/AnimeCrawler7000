import java.io.BufferedReader;
import java.io.FileNotFoundException;
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

public class NLP {
	static StanfordCoreNLP pipeline;
	static double tweetCount = 0;
	static double[] sentimentArray = { 0, 0, 0, 0, 0 };
	// static double[] sentimentArray = { 0, 9, 15, 6, 0 };

	public static void startNLP(String[] args) throws IOException {
		/** one-time run to init annotators into pipeline before running findSentiment */
		NLP.init();

		/** read csv and store tweet text into ArrayList */
		ArrayList<String> tweets = NLP.readCSVintoArray();
		NLP.readTweets(tweets); // go through tweet texts in the ArrayList and assign individual score, appends
								// score into array
		// NLP.getScores(sentimentArray); // get the scores from the sentimentArray and find total score, avr score, total
										// tweets
		// NLP.getPercentage(sentimentArray); // get % of negative, neutral and positive tweets; needs getScores() to run
											// first to get total tweetcount

		/** For GUI Team to call for output: */
		// NLP.getScores(sentimentArray)[0]); // total score
		// NLP.getScores(sentimentArray)[1]); // tweet count
		// NLP.getScores(sentimentArray)[2]); // average score

		// NLP.getPercentage(sentimentArray)[0]); // % of tweets negative in double
		// NLP.getPercentage(sentimentArray)[1]); // % of tweets neutral in double
		// NLP.getPercentage(sentimentArray)[2]); // % of tweets positive in double

	}

	/**
	 * init annotators to use for sentiment analysis
	 * 
	 * @param pipeline
	 * @throws IOException
	 */

	public static void init() throws IOException {
		// pipeline = new StanfordCoreNLP("MyPropFile.properties");

		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
		// turn off the warnings and continue to ignore untokenizable characters
		props.setProperty("tokenize.options", "untokenizable=noneDelete");
		pipeline = new StanfordCoreNLP(props);
	}

	/**
	 * sentiment calculating method for a string
	 * 
	 * @param pipeline annotations chosen stored in pipeline to apply in sentiment
	 *                 calculation
	 * @param tweet    string to be used in finding sentiment
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

	/** iterate through tweets array to populate sentimentArray */
	public static void readTweets(ArrayList<String> tweets) {
		for (String tweet : tweets) {
			/** print "score : tweet content" and adds counter to score in sentimentArray */
			System.out.println(findSentiment(tweet) + " : " + tweet);
			sentimentArray[findSentiment(tweet)]++;
		}
	}

	/**
	 * 
	 * @param array takes in sentimentArray and iterates through to obtain scores
	 * @return totalScore, tweetCount, averageScore values in an array
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
	 * takes values from sentimentArray to calculate percentage of tweets that are
	 * positive, negative and neutral
	 * 
	 * @param sentimentArray
	 * @return negPer, neuPer, posPer values in an array
	 */

	// public static double[] getPercentage(double[] sentimentArray, double[]
	// scoreArray) {
	public static double[] getPercentage(double[] sentimentArray) {
		double negPer = sentimentArray[1] / tweetCount * 100;
		double neuPer = sentimentArray[2] / tweetCount * 100;
		double posPer = sentimentArray[3] / tweetCount * 100;
		System.out.println("Percentage Negative: " + negPer + "%");
		System.out.println("Percentage Neutral: " + neuPer + "%");
		System.out.println("Percentage Positive: " + posPer + "%");
		// System.out.println("TweetCount: " + tweetCount);
		return new double[] { negPer, neuPer, posPer };
	}

	public static ArrayList<String> readCSVintoArray() throws IOException {
		ArrayList<String> tweetList = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader("animeCrawler7000.csv"));
			String line;

			while ((line = br.readLine()) != null) {
				String[] cols = line.split(",");
				tweetList.add(cols[2]);
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("Failed to search find CSV.");
			e.printStackTrace();
		}
		return tweetList;
	}
}