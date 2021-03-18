
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterCrawler {
	private Twitter twitter;

	public TwitterCrawler() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey("c4DG9NWeXgLMr8Lfvt9D00Br9")
				.setOAuthConsumerSecret("pv5gg7OddQWBhzfH8GkO6fmMyhq2ubdDTNkz2b3Cv4iBD7DYJF")
				.setOAuthAccessToken("1365286008841859074-XpirvqcdNIoYlyNZQG0KyaKAAw0lVC")
				.setOAuthAccessTokenSecret("wz3lyZmKwg7cHoeKK30YKLdF5uOemagC3wDqfXxAJjO2m");
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();

	}
	
	//to search for tweets and add to database. 
	public void query(String searchTerm) throws TwitterException, IOException {
		try {
			File tweetFile = new File("animeCrawler7000.csv");
			if (tweetFile.createNewFile()) {
				System.out.println("File created: " + tweetFile.getName());
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		// set query keyword
		Query query = new Query(searchTerm + " -filter:retweets");

		// set language to English
		query.setLang("en");

		// set number of tweets
		int numTweets = 100;

		long lastID = Long.MAX_VALUE;
		// create arrayList to store tweets
		ArrayList<Status> tweets = new ArrayList<Status>();
		// ArrayList<String> tweetList = new ArrayList<String>();

		// open write
		// csv version
		
		FileWriter writer = new FileWriter("animeCrawler7000.csv");
		// Set csv headers
		writer.write("Username,Favourites,Tweet\n");

		// get tweets
		while (tweets.size() < numTweets) {
			if (numTweets - tweets.size() > 100) {
				query.setCount(100);
			} else {
				query.setCount(numTweets - tweets.size());
			}
			try {
				QueryResult result = twitter.search(query);
				if ((query = result.nextQuery()) == null) {
					break;
				}

				// add tweets to arrayList tweets
				tweets.addAll(result.getTweets());
				System.out.println("Gathered " + tweets.size() + " tweets");

				for (Status t : tweets) {
					if (t.getId() < lastID) {
						lastID = t.getId();
					}
				}

			} catch (TwitterException te) {
				System.out.println("Couldn't connect: " + te);
			}
			query.setMaxId(lastID - 1);

		}
		// Sort tweets according to number of favourites
		Collections.sort(tweets, new Comparator<Status>() {
			@Override
			public int compare(Status status1, Status status2) {
				return Integer.compare(status2.getFavoriteCount(), status1.getFavoriteCount());
			}
		});
		// write to file
		for (Status t : tweets) {
			// csv version
			writer.write(t.getUser().getScreenName() + "," + t.getFavoriteCount() + ","
					+ t.getText().trim().replaceAll("\n|\r|,", " ") + "\n");
		}
		writer.close();

	}

}
