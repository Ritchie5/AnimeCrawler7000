import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestCase {
	SearchDetails details;
	
	MALSearch malSearch;
	TwitterSearch twitterSearch;
	
	@BeforeEach
	void setUp() throws Exception {
		details = new SearchDetails();
		
		malSearch = new MALSearch("pikachu", "1");
		twitterSearch = new TwitterSearch();
	}
	
	@AfterEach
	void tearDown() throws Exception{
		
	}
	
	@Test
	void testValidAnimeInput() {
		malSearch.setAnimeInput("pikachu");
		assertEquals("pikachu", malSearch.getAnimeInput());
	}

	@Test
	void testInvalidAnimeInput() {
		malSearch.setAnimeInput("!@#");
		assertEquals("", malSearch.getAnimeInput());
	}
	
	@Test
	void testValidSelectedAnime() {
		malSearch.setSelectedAnime("1");
		assertEquals("1", malSearch.getSelectedAnime());
	}
	
	@Test
	/**
	 * Test class variable for MALSearch class
	 */
	void testValidSeletedAnimeTitle() {
		malSearch.setSelectedAnimetitle("pikachu");
		assertEquals("pikachu", malSearch.getSelectedAnimetitle());
	}
	
	/**
	 * Test class variable for MALCrawler class
	 */
	@Test
	void testValidAnimeTitle() {
		details.setTitle("Pokemon: Pichu to Pikachu");
		assertEquals("Pokemon: Pichu to Pikachu", details.getTitle());
	}

	
	@Test
	void testValidAnimeLink() {
		details.setLink("https://myanimelist.net/anime/4793/Pokemon__Pichu_to_Pikachu");
		assertEquals("https://myanimelist.net/anime/4793/Pokemon__Pichu_to_Pikachu", details.getLink());
	}
	
	@Test
	void testValidMALCrawlerList() throws IOException {
		ArrayList<SearchDetails> actualList = new ArrayList<>(MALCrawler.searchAnime("Pokemon"));
		ArrayList<SearchDetails> expectedList = new ArrayList<>(MALCrawler.searchAnime("Pokemon"));
		
		assertEquals(expectedList.get(0).getTitle(), actualList.get(0).getTitle());
	}
	
	@Test
	void testInvalidMALCrawlerList() {
		assertThrows(IllegalArgumentException.class, () -> {
			MALCrawler.searchAnime(null);
		});
	}
	
	@Test
	void testValidMALSearchTop30() throws IOException {
		assertNotEquals("", malSearch.CrawlMALData());
	}
	
	@Test
	void testValidMALSearchAnime() throws IOException {
		assertNotEquals("", malSearch.CrawlMALData("pikachu"));
	}
	
	@Test
	void testValidMALSearchAnimeDetails() throws IOException {
		assertNotEquals("", malSearch.CrawlMALData(1));
	}
	
	@Test
	void testValidTwitterSearch() {
		assertNotEquals("", twitterSearch.topTweets(0, "animeCrawler7000.csv"));
	}
}
