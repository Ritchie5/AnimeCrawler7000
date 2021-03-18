import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchInput {
	private String animeInput;
	private String selectedAnime;

	public SearchInput() {

	}

	public SearchInput(String animeInput, String selectedAnime) {
		this.animeInput = animeInput;
		this.selectedAnime = selectedAnime;
	}

	public String getAnimeInput() {
		return animeInput;
	}

	public void setAnimeInput(String animeInput) {
		// Regex to check if a string contains only special characters
	    String regex = "[^a-zA-Z0-9]+";
	    // Compile the ReGex
	    Pattern p = Pattern.compile(regex);
	    // Find match between given string & regular expression
	    Matcher m = p.matcher(animeInput);
	    
	    // validation
		if(m.find())
			throw new IllegalArgumentException("Invalid Input!");
		if(animeInput == null || animeInput.length() < 3)
			throw new IllegalArgumentException("Invalid Input!");
		
		this.animeInput = animeInput;
	}

	public String getSelectedAnime() {
		return selectedAnime;
	}

	public void setSelectedAnime(String selectedAnime) {
		this.selectedAnime = selectedAnime;
	}

}
