import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MALSearch extends SearchInput {
	public MALSearch() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MALSearch(String animeInput, String selectedAnime) {
		super(animeInput, selectedAnime);
		// TODO Auto-generated constructor stub
	}

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

	public String CrawlMALData(String animeInput) throws IOException {
		
		//For Jtestunit
		if (MALCrawler.searchAnime(animeInput).isEmpty())
		{
			throw new IllegalArgumentException("List null!");
		}
		
		if (MALCrawler.searchAnime(animeInput).isEmpty()) // return invalid if the hashmap is empty
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
