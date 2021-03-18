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

	/**
	 * init annotators to use for sentiment analysis
	 * 
	 * @param pipeline
	 */
	public static void init() {
		// pipeline = new StanfordCoreNLP("MyPropFile.properties");

		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
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

	/**
	 * 
	 * @param array takes in sentimentArray and iterates through to obtain scores
	 * @return totalScore, tweetCount, averageScore values in an array
	 */
	public static double[] getScores(double[] array) {
		double score = 0;
		double tweetCount = 0;
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
		return new double[] { totalScore, tweetCount, averageScore };
	}

	/**
	 * takes values from the two arrays to calculate percentage of tweets that are positive, negative and neutral
	 * @param sentimentArray
	 * @param scoreArray 
	 * @return negPer, neuPer, posPer values in an array
	 */
	public static double[] getPercentage(double[] sentimentArray, double[] scoreArray) {
		double negPer = sentimentArray[1] / scoreArray[1] * 100;
		double neuPer = sentimentArray[2] / scoreArray[1] * 100;
		double posPer = sentimentArray[3] / scoreArray[1] * 100;
		System.out.println("Percentage Negative: " + negPer + "%");
		System.out.println("Percentage Neutral: " + neuPer + "%");
		System.out.println("Percentage Positive: " + posPer + "%");
		return new double[] { negPer, neuPer, posPer };
	}
}