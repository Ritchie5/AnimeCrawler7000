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

public class SentimentChart extends JFrame {
	
	public SentimentChart(String appTitle, String animeName) throws IOException {
		super(appTitle);

		// Create Dataset
		CategoryDataset dataset = createDataset();

		// Create chart
		JFreeChart chart = ChartFactory.createBarChart(animeName, // Chart Title
				"Sentiment Analysis", // Category axis
				"Popularity percentage", // Value axis
				dataset, PlotOrientation.VERTICAL, true, true, false);

		ChartPanel panel = new ChartPanel(chart);
		setContentPane(panel);
		setSize(500, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	private CategoryDataset createDataset() throws IOException {
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(sentimentanalysis()[0], "Dislike", "Dislike");

		dataset.addValue(sentimentanalysis()[1], "Netural", "Netural");

		dataset.addValue(sentimentanalysis()[2], "Like", "Like");

		return dataset;
	}
	
	public int[] sentimentanalysis() throws IOException {
		int[] value = {0,0,0};
		/** one-time run to init annotators into pipeline before running findSentiment */
		NLP.init();
		
		/** read csv and store tweet text into ArrayList */
		ArrayList<String> tweets = NLP.readCSVintoArray(); 
		NLP.readTweets(tweets); // go through tweet texts in the ArrayList and assign individual score, appends score into array
		NLP.getScores(NLP.sentimentArray); // get the scores from the sentimentArray and find total score, avr score, total tweets
		NLP.getPercentage(NLP.sentimentArray);
		
		value[0]=(int) NLP.getPercentage(NLP.sentimentArray)[0]; // % of tweets negative in double
		value[1]=(int) NLP.getPercentage(NLP.sentimentArray)[1]; // % of tweets neutral in double
		value[2]=(int) NLP.getPercentage(NLP.sentimentArray)[2]; // % of tweets positive in double
		return value;
	}
}