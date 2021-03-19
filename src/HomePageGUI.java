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

public class HomePageGUI {
	public static int search;

	public static String top30anime;
	public static String searchedanime;
	public static String animesearched;

	public static int i = 0;

	public static MALSearch mal = new MALSearch();

	public HomePageGUI() {
		setTop30Anime();
		homeGUI();
	}

	public static void setsearchedanime(String searched) {
		searchedanime = searched;
	}

	public static void setTop30Anime() {
		try {
			top30anime = mal.top30();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void homeGUI() {
		// frame
		JFrame mainFrame = new JFrame("AnimeCrawler7000");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(800, 700);
		mainFrame.getContentPane().setLayout(new BorderLayout());

		// Borders
		Border border1 = BorderFactory.createMatteBorder(0, 10, 0, 10, Color.WHITE);
		Border border = BorderFactory.createMatteBorder(1, 0, 1, 0, Color.BLACK);

		// panels
		JPanel navBar = new JPanel();
		// navBar design
		// navBar.setPreferredSize(new Dimension(600, 70));
		navBar.setBackground(new Color(240, 248, 255));
		navBar.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 10)); // horizontal spacing, vertical spacing
		navBar.setBorder(border1);

		// navigation button -- can remove if no need
		JButton homeBtn = new JButton("Home");
		homeBtn.setSize(30, 40);
		homeBtn.setBackground(new Color(204, 153, 204));

		JButton twitterBtn = new JButton("Twitter");
		twitterBtn.setSize(30, 40);
		twitterBtn.setBackground(new Color(204, 153, 204));

		JButton next = new JButton("next");
		next.setSize(30, 40);
		next.setBackground(new Color(204, 153, 204));
		next.setEnabled(false);

		JButton back = new JButton("back");
		back.setSize(30, 40);
		back.setBackground(new Color(204, 153, 204));
		back.setEnabled(false);
		
		JButton analysis = new JButton("analysis");
		analysis.setSize(30, 40);
		analysis.setBackground(new Color(204, 153, 204));
		analysis.setEnabled(false);
		analysis.setVisible(false);

		// search field
		JTextField searchTxtField = new JTextField();
		searchTxtField.setPreferredSize(new Dimension(250, 30));

		// search button
		JButton searchbutton = new JButton("Search");
		searchbutton.setSize(30, 40);
		searchbutton.setBackground(new Color(204, 153, 204));

		navBar.add(homeBtn);
		navBar.add(twitterBtn);
		navBar.add(back);
		navBar.add(next);
		navBar.add(analysis);
		navBar.add(searchTxtField);
		navBar.add(searchbutton);
		navBar.setVisible(true);
		navBar.setBorder(border);

		homeBtn.setEnabled(false);
		twitterBtn.setEnabled(false);
		next.setEnabled(false);

		// center text area
		JTextArea center = new JTextArea(top30anime);
		center.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 13));
		center.setBackground(Color.WHITE);
		center.setEditable(false);
		center.setBorder(border1);
		center.setLineWrap(true);
		center.setWrapStyleWord(true);

		// Bottom Logo
		JTextField logo = new JTextField("ANIME CRAWLER 7000");
		logo.setBackground(Color.WHITE);
		logo.setHorizontalAlignment(JTextField.CENTER);
		logo.setFont(new Font("Segoe UI Symbol", Font.BOLD, 25));
		logo.setBorder(border);
		logo.setEditable(false);
		logo.setPreferredSize(new Dimension(280, 40));

		// buttons actionListener
		searchbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == searchbutton) {
					search++;
					if (search == 1) {
						animesearched = searchTxtField.getText();
						boolean isEmpty = mal.setAnimeInput(animesearched);
						if (isEmpty == false) {
							search = 0;
							searchTxtField.setText(null);
						} else {
							try {
								animesearched = mal.searchAnime();
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
						center.setText(null);
						String input = searchTxtField.getText();
						String selectedanime = null;
						try {
							mal.setSelectedAnime(input);
							selectedanime = mal.animeDetails();
						} catch (IOException e1) {

							e1.printStackTrace();
						}
						if (selectedanime != null) {
							center.setText(selectedanime);
							twitterBtn.setEnabled(true);
							String temp = mal.getSelectedAnimetitle();
							System.out.print(temp);
							setsearchedanime(temp);
							searchbutton.setEnabled(false);
							searchTxtField.setText(null);
						} else {
							searchTxtField.setText(null);
							center.setText(animesearched);
							search++;
						}
					}
				}

			}
		});

		homeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				homeBtn.setEnabled(false);
				twitterBtn.setEnabled(false);
				next.setEnabled(false);
				back.setEnabled(false);
				center.setText(top30anime);
				searchbutton.setEnabled(true);
				searchTxtField.setText(null);
				search = 0;
				analysis.setEnabled(false);
				analysis.setVisible(false);
			}
		});

		twitterBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Use searchedanime variable. Contains the anime name that was searched.
				TwitterCrawler getTweets = new TwitterCrawler();
				TwitterSearch getTweets1 = new TwitterSearch();
				try {
					center.setText("Fetching data... Please wait...");
					center.paintImmediately(center.getVisibleRect());
					getTweets.query(searchedanime); 
					String result = getTweets1.topTweets(i,"animeCrawler7000.csv");
					System.out.print(result);
					center.setText(result);
					center.paintImmediately(center.getVisibleRect());
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

		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				i += 5;
				TwitterSearch getTweets = new TwitterSearch();
				String result = getTweets.topTweets(i, "animeCrawler7000.csv");
				center.setText(result);
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
		
		analysis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					center.setText("Fetching data... Please wait...Process could take a while");
					center.paintImmediately(center.getVisibleRect());
					SentimentChart sentimentbar = new SentimentChart("Sentiment Chart");
				} catch (IOException e1) {
					e1.printStackTrace();
				} 	
			}
		});
		
		// add panel to frame
		mainFrame.getContentPane().add(navBar, BorderLayout.NORTH);
		mainFrame.getContentPane().add(center, BorderLayout.CENTER);
		mainFrame.getContentPane().add(logo, BorderLayout.SOUTH);
		mainFrame.setVisible(true);
	}
}
