import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchedAnime {
static String animename = "Please input the number of the anime you want to choose.\n";	
static String searchedanime = "Please input the number of the anime you want to choose.\n";
static WebCrawler Searchanime1 = new WebCrawler();
static String selectedanime = null;
static String animedetails = null;

	
	public static void setAnime(String searchedanime) {
		animename = searchedanime;
	}
	
	public static String getAnime() {
		return animename;
	}
	
	public static void setselectedanime(String selectedanime1) {
		selectedanime = selectedanime1;
	}
	
	public static String getselectedanime() {
		return selectedanime;
	}
	public static String pokemon() throws IOException
	{	
		WebCrawler crawler = new WebCrawler();
		String Top30;
					//TOP AIRING ANIME
					int numberRank = 0;
					Top30 = "Top 30 anime\n";
					for (String anime : crawler.topAnimes().values()) 
					{
						numberRank++;
						if (numberRank < 31)
						{
							if (numberRank < 10) {
								Top30 += numberRank + ":  ";
								Top30 += anime + "\n";
							}
							else {	
								Top30 += numberRank + ": ";
								Top30 += anime + "\n";
								}
						}
					}
					return Top30;				
	}

	public static String Searchanime() throws IOException
	{	
		 if (WebCrawler.searchAnime(animename).isEmpty())  // return invalid if the hashmap is empty
		{
			return "Invalid Input, please try again!";
		}
		else 
		{
			int numberTitle = 0;
			String Anime = "Please input the number of the anime you want to choose.\n";

			//Print out the titles
			for(SearchDetails searchedResult : WebCrawler.searchAnime(animename)) 
			{
				numberTitle++;
				if(numberTitle<31) 
				{
					Anime += " " +  numberTitle + ": " + searchedResult.getTitle() + "\n";
				}
			}

			return Anime;

			//Access selectedAnime and print out the details using the selected anime link	
        	}
	}
	
	public static String anime1() throws IOException
	{
		int numberInput = Integer.valueOf(selectedanime);
		int number = 0;
		
		for(SearchDetails searchedResult : WebCrawler.searchAnime(animename)) 
		{
			number++;
			if(number == numberInput)
			{
				SearchedAnime.setselectedanime(searchedResult.getTitle());
				animedetails = WebCrawler.selectedAnime(searchedResult.getLink());
			}
		}
		return animedetails;
	}
}
