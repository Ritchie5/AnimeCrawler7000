import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * @author Team Pikachuuuuuuu
 * @version 1.8
 * @since 1.0
 */
public class MALSearch extends SearchInput {
	public MALSearch() {
		super();
	}
	/**
	 * Sets the parent class variables, SearchInput()
	 * @param animeInput sets animeInput of parent class 
	 * @param selectedAnime sets selectedAnime of parent class
	 * @param animeTitle set animeTitle of parent class
	 */
	public MALSearch(String animeInput, String selectedAnime, String animeTitle) {
		super(animeInput, selectedAnime, animeTitle);
	}
/**
 * crawl MyAnimeList(MAL) data and get the top 30 anime.
 * @return Top30 anime in MAL. 
 * @throws IOException
 */
	public String CrawlMALData() throws IOException {
		String Top30;

		// TOP AIRING ANIME
		int numberRank = 0;
		Top30 = "Top 30 anime\n";
		for (String anime : MALCrawler.topAnimes().values()) {
			numberRank++;
			if (numberRank < 31) {
					Top30 += numberRank + ": ";
					Top30 += anime + "\n";
			}
		}
		return Top30;
	}
/**
 * Get the anime title and details of selected Anime.
 * @param animeInput numberTile of searchedAnime 
 * @return anime numbering and title. 
 * @throws IOException 
 */
	public String CrawlMALData(String animeInput) throws IOException {
		
		//For Jtestunit
		if (MALCrawler.searchAnime(animeInput).isEmpty())
		{
			throw new IllegalArgumentException("List null!");
		}
		
		if (MALCrawler.searchAnime(animeInput).isEmpty()) // return invalid if the arraylist is empty
		{
			return "Invalid Input, please try again!";
		} else {
			int numberTitle = 0;
			String Anime = "Please input the number of the anime you want to choose.\n";

			// Print out the titles
			for (SearchDetails searchedResult : MALCrawler.searchAnime(animeInput)) {
				numberTitle++;
				if (numberTitle < 31) {
					Anime += " " + numberTitle + ": " + searchedResult.getTitle() + "\n";
				}
			}
			return Anime;

			// Access selectedAnime and print out the details using the selected anime link
		}
	}
/**
 * Get selected Anime details. 
 * @param numberInput new value of the selected anime numberTitle.
 * @return selected anime details. 
 * @throws IOException
 */
	public String CrawlMALData(int numberInput) throws IOException {
		int number = 0;
		String animeDetails = "";
		
		for (SearchDetails searchedResult : MALCrawler.searchAnime(super.getAnimeInput())) {
			number++;
			if (number == numberInput) {
				super.setSelectedAnimetitle(searchedResult.getTitle());
				animeDetails = MALCrawler.selectedAnime(searchedResult.getLink());
			}
		}
		return animeDetails;
	}
}
