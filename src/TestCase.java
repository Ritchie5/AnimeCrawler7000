import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
* Test case classes: SearchDetails, MALSearch, MALCrawler, SearchDetails, SearchInput, TwitterSearch 
*/
class TestCase {
	SearchDetails details;
	MALSearch malSearch;
	TwitterSearch twitterSearch;
	
	@BeforeEach
	/**
	 * Instantiates new objects; SearchDetails(), MALSearch(), TwitterSearch().
	 * Set MALSearch constructor's parameter.
	 */
	void setUp() throws Exception {
		details = new SearchDetails();
		malSearch = new MALSearch("pikachu", "1", "Pokemon: Pichu to Pikachu");
		twitterSearch = new TwitterSearch();
	}
	
	@AfterEach
	void tearDown() throws Exception{
		
	}
	
	@Test
	/**
	 * Test valid user input.
	 * Set anime input to "pikachu" using setAnimeInput method in MALSearch().
	 * The actual value will be obtain from the getAnimeInput method in MALSearch().
	 * This method will then check if the actual value equals to the expected value "pikachu".
	 */
	void testValidAnimeInput() {
		malSearch.setAnimeInput("pikachu");
		assertEquals("pikachu", malSearch.getAnimeInput());
	}

	@Test
	/**
	 * Test invalid user input.
	 * Set anime input to "!@#" using setAnimeInput method in SearchInput() from MALSearch().
	 * The actual value will be obtain from the getAnimeInput method.
	 * MALSearch get method will return an empty string if it acquires an invalid input from the user.
	 * This method will then check if the actual value equals to the expected value "".
	 */
	void testInvalidAnimeInput() {
		malSearch.setAnimeInput("!@#");
		assertEquals("", malSearch.getAnimeInput());
	}
	
	@Test
	/**
	 * Test valid input to select a specific anime from the list of animes shown.
	 * Set selected anime input to "1" using setSelectedAnime method in SearchInput() from MALSearch().
	 * The actual value will be obtain from the getSelectedAnime method.
	 * This method will then check if the actual value equals to the expected value "1".
	 */
	void testValidSelectedAnime() {
		malSearch.setSelectedAnime("1");
		assertEquals("1", malSearch.getSelectedAnime());
	}
	
	@Test
	/**
	 * Test valid input to save the selected anime title once user selected the specific anime.
	 * Set selected anime title input to "Pokemon: Pichu to Pikachu" using setSelectedAnimetitle method in SearchInput() from MALSearch().
	 * The actual value will be obtain from the getSelectedAnimetitle method.
	 * This method will then check if the actual value equals to the expected value "Pokemon: Pichu to Pikachu".
	 */
	void testValidSeletedAnimeTitle() {
		malSearch.setSelectedAnimetitle("Pokemon: Pichu to Pikachu");
		assertEquals("Pokemon: Pichu to Pikachu", malSearch.getSelectedAnimetitle());
	}
	
	@Test
	/**
	 * Test valid anime title when SearchDetails method is called.
	 * Set anime title to "Pokemon: Pichu to Pikachu" using setTitle method in SearchDetails().
	 * The actual value will be obtain from the getTitle method.
	 * This method will then check if the actual value equals to the expected value "Pokemon: Pichu to Pikachu".
	 */
	void testValidAnimeTitle() {
		details.setTitle("Pokemon: Pichu to Pikachu");
		assertEquals("Pokemon: Pichu to Pikachu", details.getTitle());
	}

	
	@Test
	/**
	 * Test valid anime link when SearchDetails method is called.
	 * Set anime link to "https://myanimelist.net/anime/4793/Pokemon__Pichu_to_Pikachu" using setLink method in SearchDetails().
	 * The actual value will be obtain from the getLink method.
	 * This method will then check if the actual value equals to the expected value "https://myanimelist.net/anime/4793/Pokemon__Pichu_to_Pikachu".
	 */
	void testValidAnimeLink() {
		details.setLink("https://myanimelist.net/anime/4793/Pokemon__Pichu_to_Pikachu");
		assertEquals("https://myanimelist.net/anime/4793/Pokemon__Pichu_to_Pikachu", details.getLink());
	}
	
	@Test
	/**
	 * Test valid MAL crawler string input.
	 * Instantiates and initialize 2 ArrayList for SearchDetails; 1 for actual, 1 for expected.
	 * This method will check if the first element from the actual list is equals to the first element from the expected list if user inputs a valid search string.
	 */
	void testValidMALCrawlerList() throws IOException {
		ArrayList<SearchDetails> actualList = new ArrayList<>(MALCrawler.searchAnime("Pokemon"));
		ArrayList<SearchDetails> expectedList = new ArrayList<>(MALCrawler.searchAnime("Pokemon"));
		
		assertEquals(expectedList.get(0).getTitle(), actualList.get(0).getTitle());
	}
	
	@Test
	/**
	 * Test invalid MAL crawler string input.
	 * This method will throw IllegalArgumentException if string inputed to searchAnime method in MALCrawler() is null.
	 * @exception IllegalArgumentException if search string is null
	 */
	void testInvalidMALCrawlerList() {
		assertThrows(IllegalArgumentException.class, () -> {
			MALCrawler.searchAnime(null);
		});
	}
	
	@Test
	/**
	 * Test valid top 30 anime string when CrawlMALData method is called.
	 * This method will check if string returned from crawlMALData method in MALSearch() is not equals to empty. 
	 */
	void testValidMALSearchTop30() throws IOException {
		assertNotEquals("", malSearch.CrawlMALData());
	}
	
	@Test
	/**
	 * Test valid anime title string when CrawlMALData method is called.
	 * This method will check if string returned from crawlMALData method in MALSearch() is not equals to empty. 
	 */
	void testValidMALSearchAnime() throws IOException {
		assertNotEquals("", malSearch.CrawlMALData("pikachu"));
	}
	
	@Test
	/**
	 * Test valid selected anime details string when CrawlMALData method is called.
	 * This method will check if string returned from crawlMALData method in MALSearch() is not equals to empty. 
	 */
	void testValidMALSearchAnimeDetails() throws IOException {
		assertNotEquals("", malSearch.CrawlMALData(1));
	}
	
	@Test
	/**
	 * Test valid twitter search string.
	 * This method will check if string returned from topTweets method in twitterSearch() is not equals to empty. 
	 */
	void testValidTwitterSearch() {
		assertNotEquals("", twitterSearch.topTweets(0, "animeCrawler7000.csv"));
	}
}
