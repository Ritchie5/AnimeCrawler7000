import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestCase {
	SearchInput input;
	SearchDetails details;
	
	MALSearch malSearch;
	TwitterSearch twitterSearch;
	
	@BeforeEach
	void setUp() throws Exception {
		input = new SearchInput();
		details = new SearchDetails();
		
		malSearch = new MALSearch("pikachu", "1");
		twitterSearch = new TwitterSearch();
	}
	
	@AfterEach
	void tearDown() throws Exception{
		
	}
	
	@Test
	void testValidAnimeInput() {
		input.setAnimeInput("pikachu");
		assertEquals("pikachu", input.getAnimeInput());
	}

	@Test
	void testInvalidAnimeInput() {
		input.setAnimeInput("!@#");
		assertEquals("", input.getAnimeInput());
	}
	
	@Test
	void testValidSelectedAnime() {
		input.setSelectedAnime("1");
		assertEquals("1", input.getSelectedAnime());
	}
	
	@Test
	/**
	 * Test class variable for MALSearch class
	 */
	void testanimeTitle() {
		input.setSelectedAnimetitle("pikachu");
		assertEquals("pikachu", input.getSelectedAnimetitle());
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
		assertNotEquals("", malSearch.top30());
	}
	
	@Test
	void testValidMALSearchAnime() throws IOException {
		assertNotEquals("", malSearch.searchAnime());
	}
	
	@Test
	void testValidMALSearchAnimeDetails() throws IOException {
		assertNotEquals("", malSearch.animeDetails());
	}
	
	@Test
	void testValidTwitterSearch() {
		assertNotEquals("", twitterSearch.topTweets(0, "animeCrawler7000.csv"));
	}
}
