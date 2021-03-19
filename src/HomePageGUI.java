
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.TextField;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import twitter4j.TwitterException;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;


/**
 * @author Team Pikachuuuuuuu
 * @version 1.8
 * @since 1.0
 * 
 * 
 * 
 */
public class HomePageGUI extends JFrame{

	/**
	 *  The main method is which make use of demo loadGUI method
	 * @param args Unused
	 */
	public static void main(String[] args) {
		HomePageGUI loadGUI = new HomePageGUI();

	}
	
	/**
	 * count the number of button clicks to differentiate between searching for an anime and selecting an anime
	 */
	public int search;
	/**
	 * store the result of the top 30 anime data crawl from MyAnimeList
	 */
	public String top30anime;
	/**
	 * store the title of the searched anime
	 */
	public String searchedanime;
	/**
	 * store the searched input by user
	 */
	public String animesearched;
	/**
	 * counter used for paging between tweets
	 */
	public int i = 0;
	/**
	 * Constructor for accessing the methods in MALSearch
	 */
	public MALSearch mal = new MALSearch();

	/**
	 * Load the list of top 30 anime from MyAnimeList that will be loaded onto the main Frame
	 * call homeGUI() method 
	 */
	public HomePageGUI() {
		setTop30Anime();
		homeGUI();
	}
	/**
	 * Changes the title of the searched anime
	 * @param searched should include user search input
	 */
	public void setsearchedanime(String searched) {
		this.searchedanime = searched;
	}
	/**
	 * Updates the list of top 30 anime based on the data crawled from MyAnimeList website. 
	 */
	public void setTop30Anime() {
		try {
			this.top30anime = mal.CrawlMALData();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	/**
	 * Load and Display: 
	 * List of top 30 anime 
	 * List of anime based on user search input
	 * Details of Selected Anime
	 * List of recommended anime based on the selected anime 
	 * List of tweets that are related to the anime 
	 * Display the result of the sentimental analysis using BarChart.
	 * 
	 */
	public void homeGUI() {

		//Set the Title, Size, Layout and close operation of the application
		this.setTitle("AnimeCrawler7000");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 700);
		this.getContentPane().setLayout(new BorderLayout());

		// Set the border color of the layout
		Border border1 = BorderFactory.createMatteBorder(0, 10, 0, 10, Color.WHITE);
		Border border = BorderFactory.createMatteBorder(1, 0, 1, 0, Color.BLACK);

		//Set a panel used as navigation bar
		//set the color, layout of the navigation bar
		JPanel navBar = new JPanel();
		// navBar design
		// navBar.setPreferredSize(new Dimension(600, 70));
		navBar.setBackground(new Color(240, 248, 255));
		navBar.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 10)); // horizontal spacing, vertical spacing
		navBar.setBorder(border1);
		
		//set buttons and its design for redirecting to Home.
		// navigation buttons
		JButton homeBtn = new JButton("Home");
		homeBtn.setSize(30, 40);
		homeBtn.setBackground(new Color(204, 153, 204));
		/**
		 * set button and its design for redirecting to Twitter crawl result.
		 */
		JButton twitterBtn = new JButton("Twitter");
		twitterBtn.setSize(30, 40);
		twitterBtn.setBackground(new Color(204, 153, 204));
		
		//set button and its design for paging between the twitter crawl result.
		 
		JButton next = new JButton("next");
		next.setSize(30, 40);
		next.setBackground(new Color(204, 153, 204));
		next.setEnabled(false);
		//set button and its design for paging between the twitter crawl result.
		
		JButton back = new JButton("back");
		back.setSize(30, 40);
		back.setBackground(new Color(204, 153, 204));
		back.setEnabled(false);
		
		// set button and its design for redirecting to Analysis of Tweets.
		JButton analysis = new JButton("analysis");
		analysis.setSize(30, 40);
		analysis.setBackground(new Color(204, 153, 204));
		analysis.setEnabled(false);
		analysis.setVisible(false);

		// set textfield and its design for searching for anime
		JTextField searchTxtField = new JTextField();
		searchTxtField.setPreferredSize(new Dimension(250, 30));

		// set button and button design for activating searchTxtField method
		JButton searchbutton = new JButton("Search");
		searchbutton.setSize(30, 40);
		searchbutton.setBackground(new Color(204, 153, 204));

		//Add declared Buttons to the navigationBar and state its visibility
		navBar.add(homeBtn);
		navBar.add(twitterBtn);
		navBar.add(back);
		navBar.add(next);
		navBar.add(analysis);
		navBar.add(searchTxtField);
		navBar.add(searchbutton);
		navBar.setVisible(true);
		navBar.setBorder(border);

		//prevent user from clicking on homebtn, twitterBtn and nextBtn
		homeBtn.setEnabled(false);
		twitterBtn.setEnabled(false);
		next.setEnabled(false);

		/*
		 Text area to display the results of:
		  -Top 30 anime 
		  -Anime based on user search input
		  -Details of Selected Anime
		  -Recommended anime based on the selected anime 
		  -Tweets that are related to the anime 
		 */
		JTextArea center = new JTextArea(top30anime);
		center.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 13));
		center.setBackground(Color.WHITE);
		center.setEditable(false);
		center.setBorder(border1);
		center.setLineWrap(true);
		center.setWrapStyleWord(true);
		/*
		Display the application Logo at the bottom of the frame
		*/
		JTextField logo = new JTextField("ANIME CRAWLER 7000");
		logo.setBackground(Color.WHITE);
		logo.setHorizontalAlignment(JTextField.CENTER);
		logo.setFont(new Font("Segoe UI Symbol", Font.BOLD, 25));
		logo.setBorder(border);
		logo.setEditable(false);
		logo.setPreferredSize(new Dimension(280, 40));
		
		/*
		 * Take User input: 
		 * search and display the list of anime based on the search
		 * select and display the details of the selected anime
		 * ExceptionHandling are implemented to prevent null or invalid search
		 */
		searchbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == searchbutton) {
					search++;
					if (search == 1) {
						animesearched = searchTxtField.getText();
						boolean isEmpty = mal.setAnimeInput(animesearched);
						if (isEmpty == false) {
							JOptionPane.showMessageDialog(null, "Please enter an anime title that have more than 3 words!");
							search = 0;
							searchTxtField.setText(null);
						} else {
							try {
								animesearched = mal.CrawlMALData(mal.getAnimeInput());
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							if (animesearched != null) {
								center.setText(animesearched);
								homeBtn.setEnabled(true);
								searchTxtField.setText(null);
							}
						}
					}

					if (search >= 2) {
						String input = searchTxtField.getText();
						String selectedanime = null;
						mal.setSelectedAnime(input);
						String checker = mal.getSelectedAnime();
						if (checker != null) {
							try {
								selectedanime = mal.CrawlMALData(Integer.valueOf(checker));
							} catch (IOException e1) {

								e1.printStackTrace();
							}
							center.setText(selectedanime);
							twitterBtn.setEnabled(true);
							String temp = mal.getSelectedAnimetitle();
							setsearchedanime(temp);
							searchbutton.setEnabled(false);
							searchTxtField.setText(null);
							search=0;
						} else {
							searchTxtField.setText(null);
							search++;
						}
					}
				}

			}
		});
		/*
		 * enable/disable buttons, enable/disable textarea and textfield
		 */
		homeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				homeBtn.setEnabled(false);
				twitterBtn.setEnabled(false);
				next.setEnabled(false);
				back.setEnabled(false);
				analysis.setEnabled(false);
				analysis.setVisible(false);
				searchTxtField.setText(null);
				center.setText(top30anime);
				searchbutton.setEnabled(true);

			}
		});
		/*
		 * Use selectedAnime as the parameter which is passed to query method in TwitterCrawler class. 
		 * query method will then return the list of tweets which will be displayed on the textarea.  
		 */
		twitterBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Use searchedanime variable. Contains the anime name that was searched.
				TwitterCrawler getTweets = new TwitterCrawler();
				TwitterSearch getTweets1 = new TwitterSearch();
				try {
					center.setText("Fetching data... Please wait...");
					center.paintImmediately(center.getVisibleRect());
					getTweets.query(searchedanime);
					String result = getTweets1.topTweets(i, "animeCrawler7000.csv");
					System.out.print(result);
					center.setText(result);
					next.setEnabled(true);
					back.setEnabled(true);
					analysis.setEnabled(true);
					analysis.setVisible(true);

				} catch (TwitterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				twitterBtn.setEnabled(false);
			}
		});
		/*
		 * next and back allow paging between the list of top related tweets. 
		 */
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				i += 5;
				TwitterSearch getTweets = new TwitterSearch();
				String result = getTweets.topTweets(i, "animeCrawler7000.csv");
				if(result == null) 
				{
					i -= 5;
					if (i < 0) {
						i = 0;
					}
					result = getTweets.topTweets(i, "animeCrawler7000.csv");
					center.setText(result);
				}
				else 
				{
					center.setText(result);
				}
			}
		});

		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				i -= 5;
				if (i < 0) {
					i = 0;
				}
				TwitterSearch getTweets = new TwitterSearch();
				String result = getTweets.topTweets(i, "animeCrawler7000.csv");
				center.setText(result);
			}
		});
		/*
		 * display sentimentChart
		 */
		analysis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String temp = center.getText();
					center.setText("Fetching data... Please wait...Process could take a while");
					center.paintImmediately(center.getVisibleRect());
					SentimentChart sentimentbar = new SentimentChart("Sentiment Chart", mal.getSelectedAnimetitle());
					center.setText(temp);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});


		// add panel to frame */
		
		this.getContentPane().add(navBar, BorderLayout.NORTH);
		this.getContentPane().add(center, BorderLayout.CENTER);
		this.getContentPane().add(logo, BorderLayout.SOUTH);
		this.setVisible(true);
	}		

}
