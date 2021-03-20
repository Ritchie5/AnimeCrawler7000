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

/**
 * @author Team Pikachuuuuuuu
 * @version 1.8
 * @since 1.0
 */

public class TwitterCrawler {
	private Twitter twitter;

	/**
	 * Constructs TwitterCrawler instance configured with access tokens for Twitter
	 * API
	 */
	public TwitterCrawler() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey("c4DG9NWeXgLMr8Lfvt9D00Br9")
				.setOAuthConsumerSecret("pv5gg7OddQWBhzfH8GkO6fmMyhq2ubdDTNkz2b3Cv4iBD7DYJF")
				.setOAuthAccessToken("1365286008841859074-XpirvqcdNIoYlyNZQG0KyaKAAw0lVC")
				.setOAuthAccessTokenSecret("wz3lyZmKwg7cHoeKK30YKLdF5uOemagC3wDqfXxAJjO2m");
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();

	}

	/**
	 * This method will search and collect tweets according to a search term
	 * Use twitter4J to connect to twitter API to crawl for tweets
	 * Query object is created based on the searchTerm and is set to filter out retweets and to only collect English tweets
	 * ArrayList is created to store the search results
	 * As the twitter API limits the crawler to collecting 100 tweets at a time, a while loop is used to bypass it and
	 * collect 100 tweets at a time until 1000 tweets are collected
	 * Collections and Comparator from java util is used to sort the ArrayList by favourites count
	 * CSVFileWrite() is called to write data from ArrayList into a csv file
	 * @param query (searchTerm to search for tweets on Twitter)
	 * @throws TwitterException
	 * @throws IOException 
	 */

	// to search for tweets and add to database.
	public void query(String searchTerm) throws TwitterException, IOException {
		// For JTestUnit
		if (searchTerm == null || searchTerm.isEmpty()) {
			throw new IllegalArgumentException("Empty search term!");
		}

		// set query keyword, filtering out retweets
		Query query = new Query(searchTerm + " -filter:retweets");

		// set language to English
		query.setLang("en");

		// set number of tweets to be collected to be 1000
		int numTweets = 1000;

		long lastID = Long.MAX_VALUE;
		// create arrayList to store tweets
		ArrayList<Status> tweets = new ArrayList<Status>();

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
				// Print out current number of collected tweets in terminal
				System.out.println("Collected " + tweets.size() + " tweets");
				// save current tweet count for next iteration
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
		//Call CSV writer 
		CSVFileWrite(tweets);
	}
	
	/**
	 * This method will create a CSV file and write tweet data into it, CSV file is stored on local machine in main java file path
	 * Data that will be contained in the CSV file are: Username(twitter handles), favourites and the tweet
	 * tweets are cleaned using replaceAll() and trim() to remove line breaks, leading/trailing spaces and commas to conform to a CSV friendly format
	 * @param tweets (ArrayList containing crawled tweets to write into CSV file)
	 * @throws IOException
	 */

	public void CSVFileWrite(ArrayList<Status> tweets) throws IOException {
		try {
			File tweetFile = new File("animeCrawler7000.csv");
			if (tweetFile.createNewFile()) {
				System.out.println("File created: " + tweetFile.getName());
			} else {
				System.out.println("File already exists!");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		// open writer
		FileWriter writer = new FileWriter("animeCrawler7000.csv");
		// Set csv headers
		writer.write("Username,Favourites,Tweet\n");

		// write to file
		for (Status t : tweets) {
			// clean tweets before writing to CSV file
			writer.write(t.getUser().getScreenName() + "," + t.getFavoriteCount() + ","
					+ t.getText().trim().replaceAll("\n|\r|,", " ") + "\n");
		}
		// close writer
		writer.close();
	}
}
