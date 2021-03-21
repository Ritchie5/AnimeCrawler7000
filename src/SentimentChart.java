import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *Displays a bar chart containing sentiment analysis data.
 * @author Team Pikachuuuuuuu
 * @version 1.8
 * @since 1.0
 */

public class SentimentChart extends JFrame {
	/**
	 * SentimentChart creates a Jframe displaying a barchart. Barchart displays
	 * dislikes, neutral, likes percentages. Sets Jframe size, close operation, and
	 * appends the barchart into the Jframe.
	 * 
	 * @param appTitle  sets the Jframe title.
	 * @param animeName sets the Chart Title for the bar chart.
	 * @throws IOException throws error
	 */
	public SentimentChart(String appTitle, String animeName) throws IOException {
		// Sets the Jframe title
		super(appTitle);

		// Create Dataset
		CategoryDataset dataset = createDataset(); // Dataset contains dataset

		// Create chart
		JFreeChart chart = ChartFactory.createBarChart(animeName, // Chart Title
				"Sentiment Analysis", // Category axis
				"Popularity percentage", // Value axis
				dataset, // Dataset to be display
				PlotOrientation.VERTICAL, // Sets orientation of the graph
				true, true, false);

		ChartPanel panel = new ChartPanel(chart); // Instantiate the barchart
		setContentPane(panel); // Appends barchart into Jframe
		setSize(500, 400); // Set size
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // Close operation
		setVisible(true); // Set the Jframe to visible
	}

	/**
	 * CategoryDataset creates a data set based on the data collected from sentiment
	 * analysis. Calls sentiment analysis for data and stores into a
	 * CatergoryDataset.
	 * 
	 * @return Dataset containing Dislike, Neutral, Like percentages.
	 * @throws IOException throws error
	 */
	private CategoryDataset createDataset() throws IOException {

		DefaultCategoryDataset dataset = new DefaultCategoryDataset(); // Instantiate dataset
		dataset.addValue(sentimentanalysis()[0], "Dislike", "Dislike"); // Gets Dislike percentage
		dataset.addValue(sentimentanalysis()[1], "Neutral", "Neutral"); // Gets Neutral percentage
		dataset.addValue(sentimentanalysis()[2], "Like", "Like"); // Gets Like percentage
		return dataset; // Returns dataset
	}

	/**
	 * Sentimentanalysis calls NLP() to perform sentiment analysis. Percentages
	 * of the sentiment analysis are stored into an array.
	 * 
	 * @return Int array containing Dislike, Neutral, Like percentages.
	 * @throws IOException throws error
	 */
	public int[] sentimentanalysis() throws IOException {
		int[] value = { 0, 0, 0 };
		/* one-time run to init annotators into pipeline before running findSentiment */
		NLP.init();

		/* read csv and store tweet text into ArrayList */
		ArrayList<String> tweets = NLP.readCSVintoArray(NLP.csv);
		NLP.readTweets(tweets); // go through tweet texts in the ArrayList and assign individual score, appends
								// score into array
		NLP.getScores(NLP.sentimentArray); // get the scores from the sentimentArray and find total score, avr score,
											// total tweets
		NLP.getPercentage(NLP.sentimentArray); // get the sentimentarr from the sentimentArray

		value[0] = (int) NLP.getPercentage(NLP.sentimentArray)[0]; // % of tweets negative in double
		value[1] = (int) NLP.getPercentage(NLP.sentimentArray)[1]; // % of tweets neutral in double
		value[2] = (int) NLP.getPercentage(NLP.sentimentArray)[2]; // % of tweets positive in double
		return value;
	}
}