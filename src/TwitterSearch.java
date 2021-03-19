import java.io.File;
import java.io.FileReader;

import com.opencsv.CSVReader;

/**
 * Opens CSV files and stores 5 tweets into a string for GUI to display.
 * @author Team Pikachuuuuuuu
 * @version 1.8
 * @since 1.0
 */

public class TwitterSearch {
	/**
	 * topTweets opens CSV file created by TwitterCrawler(), reads CSV file line by
	 * line and stores 5 tweets into a string. The 5 tweets to be stored depends on
	 * the parameter x.
	 * 
	 * @param x   sets which 5 tweets are to be stored.
	 * @param csv sets the filepath to open the CSV file.
	 * @return String containing the 5 tweets.
	 */
	public String topTweets(int x, String csv) {
		String Anime = "Most Favorited Tweets\n"; // String that stores the 5 tweets
		int counter = 0; // Counter variable

		CSVReader reader = null; // Initialize Reader variable
		try {
			reader = new CSVReader(new FileReader(csv));// Create file reader for CSV
			String[] nextLine; // Initialize String array, used to store one line of csv
			while ((nextLine = reader.readNext()) != null && counter < (6 + x)) { // nextline stores next line of csv

				// Start storing tweets only when x is smaller than counter
				if (x < counter) {
					// Format the string for GUI display
					Anime += nextLine[0] + ": ";
					Anime += nextLine[2] + " ";
					Anime += "\nFavorited:" + nextLine[1];
					Anime += "\n\n";
				}
				counter++;
			}
		} catch (Exception e) {
			System.out.println("cannot read topTweets"); // Prints out cannot read topTweets if error occurs
			e.printStackTrace();
		}
		// Checks if there was any changes, if none return null
		if (Anime == "Most Favorited Tweets\n") {
			return null;
		}
		// Return Anime for GUI to display
		return Anime;
	}
}
