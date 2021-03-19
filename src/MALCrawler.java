import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;

//JSoup Library (to parse, extract, and manipulate data stored in HTML documents)
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Connection;

public class MALCrawler {
	// Connects to MAL top airing anime URL to crawl for data
	public static HashMap<Integer, String> topAnimes() throws IOException {
		int number = 0;

		// Connection
		Document doc = Jsoup.connect("https://myanimelist.net/topanime.php?type=airing").timeout(6000).get();
		Elements body = doc.select("tr.ranking-list");

		// HashMap
		HashMap<Integer, String> map = new HashMap<>();

		// Loop through the elements "tr.ranking-list" and selects "tr" element
		for (Element e : body.select("tr")) {
			number++;
			// ================================Title================================
			String title = e.select("td.title.al.va-t.word-break img").attr("alt").replaceAll("&#039;", "'")
					.replaceAll("Anime: ", "");

			map.put(number, title);
		}

		// Sort by key (number) using Stream API
		map.entrySet().stream().sorted(HashMap.Entry.<Integer, String>comparingByKey());

		// Return HashMap<Rank number, Anime Name>
		return map;
	}

	// Connects to MAL search URL to crawl for data
	public static ArrayList<SearchDetails> searchAnime(String searchStr) throws IOException {
		String url = "https://myanimelist.net/anime.php?cat=anime&q=";

		// ArrayList
		ArrayList<SearchDetails> list = new ArrayList<>();

		if(searchStr == null){
			throw new IllegalArgumentException("Input Null!");
		}
		
		try {
			// Get connection response
			Connection.Response response = Jsoup.connect(url + searchStr).userAgent(
					"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
					.timeout(10000).ignoreHttpErrors(true).execute();

			int statusCode = response.statusCode();

			// If successfully connected
			if (statusCode == 200) {
				// Connection
				Document doc = Jsoup.connect(url + searchStr).referrer(url + searchStr).timeout(6000).get();
				Elements body = doc.select("div.js-categories-seasonal.js-block-list.list");

				// Loop through the elements "div.js-categories-seasonal.js-block-list.list" and
				// selects "tr" element
				// Sublist to skip first iteration as it is invalid & pick the first 15 titles
				for (Element e : body.select("tr").subList(1, Math.min(16, body.select("tr").size()))) {
					// ================================Title================================
					String title = e.select("a.hoverinfo_trigger img").attr("alt").replaceAll("&#039;", "'");

					// ================================Link================================
					String link = e.select("a.hoverinfo_trigger").attr("href");

					if (title != null && link != null) {
						SearchDetails result = new SearchDetails();

						result.setTitle(title);
						result.setLink(link);

						list.add(result);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Return ArrayList<Anime Name, Anime Link> if successfully connected else
		// return null
		return list;
	}

	// Gets the link of the selected anime to connect to the page
	public static String selectedAnime(String link) throws IOException {
		String details = "";
		// Connection
		Document doc = Jsoup.connect(link).timeout(6000).get();

		// ================================Title================================
		String title = doc.select("h1.title-name.h1_bold_none").text();
		details += "Title: " + title;

		// Loop through the elements "td.borderClass" and selects "div" element
		Elements body1 = doc.select("td.borderClass");
		for (Element e : body1.select("div")) {
			// Compare string
			switch (e.select("span.dark_text").text()) {
			// ================================Type================================
			case "Type:":
				details += "\n" + e.text();
				break;
			// ================================Episodes================================
			case "Episodes:":
				details += "\n" + e.text();
				break;
			// ================================Status================================
			case "Status:":
				details += "\n" + e.text();
				break;
			// ================================Date================================
			case "Aired:":
				details += "\n" + e.text();
				break;
			// ================================Genres================================
			case "Genres:":
				if (null != e.select("span[itemprop = genre]").first())
					details += "\nGenres: " + e.select("span[itemprop = genre]").text();
				break;
			}
		}

		// Loop through the elements "div.js-scrollfix-bottom-rel" and selects "tr"
		// element
		Elements body2 = doc.select("div.js-scrollfix-bottom-rel");
		for (Element e : body2.select("tr")) {
			// ================================Ratings================================
			String ratings = e.select("div.fl-l.score").text();
			char c = 0x2605;
			String s = String.valueOf(c);
			if (null != e.select("div.fl-l.score").first())
				details += "\nRatings: " + ratings + s;

			// ================================Synopsis================================
			String description = e.select("p[itemprop = description]").text().replace("[Written by MAL Rewrite]", "");
			if (null != e.select("p[itemprop = description]").first())
				details += "\n\nSynopsis: \n" + description;
		}
		
		details += "\n\nRecommendations: \n|| ";
		for (Element e : body2.select("li")) {
			// ================================Recommendations================================
			String recommendations = e.select("li.btn-anime span.title.fs10").text();
			if(null != e.select("li.btn-anime span.title.fs10").first())
			{
				details += recommendations + " || ";
			}
		}
		
		// Return details string
		return details;
	}
}
