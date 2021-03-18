import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import twitter4j.TwitterException;

public class Brain {
	public static void main(String[] args) throws IOException, TwitterException {
		// String topic = "horimiya";

		/**
		 * run TweetManager.getTweets to start collating tweet messages into arrayList
		 * tweetList(tweets)
		 */
		// ArrayList<String> tweets = TweetManager.getTweets(topic);
		// ArrayList<String> tweets = twitterCrawlMain.scan(args);
		ArrayList<String> tweets = readCSVintoArray();

		/**
		 * init annotators once into pipeline before beginning findSentiment
		 */
		NLP.init();

		/**
		 * initialise array to store sentiment score from tweets scores range from 0-4,
		 * hence requiring an array of size 5
		 */
		double[] sentimentArray = { 0, 0, 0, 0, 0 };

		/** iterate through tweets array to populate sentimentArray */
		
		for (String tweet : tweets) {
			/** skipping tweets that just have an image link */
			// if (tweet.contains("https://t.co/")) {
			// 	continue;
			// }
			/** ignoring retweet duplication */
			if (tweet.contains("RT @")) {
				continue;
			}
			/** print "score : tweet content" and adds counter to score in sentimentArray */
			System.out.println(NLP.findSentiment(tweet) + " : " + tweet);
			sentimentArray[NLP.findSentiment(tweet)]++;
		}

		/** run score calculation */
		NLP.getPercentage(sentimentArray, NLP.getScores(sentimentArray));
	}

	public static ArrayList<String> readCSVintoArray() throws IOException {
		ArrayList<String> tweetList = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader("animeCrawler7000v2.csv"));
			String line;

			while ((line = br.readLine()) != null) {
				String[] cols = line.split(",");
				tweetList.add(cols[2]);
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tweetList;
	}

	/**
	 * switch (mainSentiment) { case 0: return "Very Negative";z case 1: return
	 * "Negative"; case 2: return "Neutral"; case 3: return "Positive"; case 4:
	 * return "Very Positive"; default: return ""; }
	 */
}
