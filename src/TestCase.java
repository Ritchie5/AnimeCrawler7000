import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestCase {
	SearchInput input;
	SearchDetails details;
	
	MALSearch malSearch;
	TwitterCrawler twitterCrawler;
	TwitterSearch twitterSearch;
	
	@BeforeEach
	void setUp() throws Exception {
		input = new SearchInput();
		details = new SearchDetails();
		
		malSearch = new MALSearch();
		twitterCrawler = new TwitterCrawler();
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
		assertThrows(IllegalArgumentException.class, () -> {
			input.setAnimeInput("@!%#$");
		});
	}
	
	@Test
	void testValidSelectedAnime() {
		input.setSelectedAnime("Pokemon Pichu to Pikachu");
		assertEquals("Pokemon Pichu to Pikachu", input.getSelectedAnime());
	}
	
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
	void testValidMALSearch() {
		
	}
	
	@Test
	void testInvalidMALSearch() {
		
	}

}
