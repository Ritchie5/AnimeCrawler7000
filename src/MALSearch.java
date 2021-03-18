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

	public static String top30() throws IOException {
		MALCrawler crawler = new MALCrawler();
		String Top30;

		// TOP AIRING ANIME
		int numberRank = 0;
		Top30 = "Top 30 anime\n";
		for (String anime : crawler.topAnimes().values()) {
			numberRank++;
			if (numberRank < 31) {
				if (numberRank < 10) {
					Top30 += numberRank + ":  ";
					Top30 += anime + "\n";
				} else {
					Top30 += numberRank + ": ";
					Top30 += anime + "\n";
				}
			}
		}
		return Top30;
	}

	public String searchAnime() throws IOException {
		if (MALCrawler.searchAnime(super.getAnimeInput()).isEmpty())
		{
			throw new IllegalArgumentException("List null!");
		}
		
		if (MALCrawler.searchAnime(super.getAnimeInput()).isEmpty()) // return invalid if the hashmap is empty
		{
			return "Invalid Input, please try again!";
		} else {
			int numberTitle = 0;
			String Anime = "Please input the number of the anime you want to choose.\n";

			// Print out the titles
			for (SearchDetails searchedResult : MALCrawler.searchAnime(super.getAnimeInput())) {
				numberTitle++;
				if (numberTitle < 31) {
					Anime += " " + numberTitle + ": " + searchedResult.getTitle() + "\n";
				}
			}
			return Anime;

			// Access selectedAnime and print out the details using the selected anime link
		}
	}

	public String animeDetails() throws IOException {
		String checkInput = super.getSelectedAnime();
		if(checkInput == null) {
			return null;
		}
		else {
		int numberInput = Integer.valueOf(checkInput);
		int number = 0;
		String animeDetails = "";
		
		for (SearchDetails searchedResult : MALCrawler.searchAnime(super.getAnimeInput())) {
			number++;
			if (number == numberInput) {
				super.setSelectedAnime(searchedResult.getTitle());
				animeDetails = MALCrawler.selectedAnime(searchedResult.getLink());
			}
		}
		return animeDetails;
		}
	}
}
