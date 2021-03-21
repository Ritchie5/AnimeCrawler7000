import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Abstract class containing class variables, getter and setter used for MALSearch().
 * @author Team Pikachuuuuuuu
 * @version 1.8
 * @since 1.0
 */
abstract class SearchInput {
	private String animeInput; //user input of anime 
	private String selectedAnime; //user choice of anime 
	private String animeTitle; //anime title of the selected anime 
/**
 * Create empty SearchInput.
 */
	SearchInput(){
		
	}
/**
 * create a new searchInput object with given animeInput and selectedAnime.
 * @param animeInput set anime input that user searched. 
 * @param selectedAnime set select anime of selected anime.
 * @param animetitle set anime title of selected anime.
 */
	public SearchInput(String animeInput, String selectedAnime, String animeTitle) {
		this.animeInput = animeInput;
		this.selectedAnime = selectedAnime;
		this.animeTitle = animeTitle;
	}
/**
 * Title of the searched anime
 * @return the current value of user animeInput
 */
	public String getAnimeInput() {
		return animeInput;
	}
/**
 * 
 * @param animeInput New Value for searchedAnime 
 * Checks for valid searched input
 * changes the value of animeInput to anime title that the user typed.
 * @return false if invalid, return true if valid
 */
	public boolean setAnimeInput(String animeInput) {
		// Regex to check if a string contains only special characters
		String regex = "[^a-zA-Z0-9 ]+";
		// Compile the ReGex
		Pattern p = Pattern.compile(regex);
		// Find match between given string & regular expression
		Matcher m = p.matcher(animeInput);

		// validation
		if (m.find()) {
			this.animeInput = "";
			return false;
		} else if (animeInput == null || animeInput.length() < 3) {
			this.animeInput = "";
			return false;
		}
		else {
			this.animeInput = animeInput;
			return true;
		}
	}
	/**
	 * Gets the animeTitle of this anime.
	 * @return the current title of the selectedAnime used for searching tweets related to it
	 */
	public String getSelectedAnimetitle() {
		return animeTitle;
	}
/**
 * change the animeTitle based on the selected anime.
 * @param animeTitle set the title of the selected anime.
 */
	public void setSelectedAnimetitle(String animeTitle) {
		this.animeTitle = animeTitle;
	}
/**
 * Gets the details of this anime.
 * @return details of the selected Anime
 */
	public String getSelectedAnime() {
		return selectedAnime;
	}
/**
 * 
 * @param selectedAnime takes in the string value(id) that is associated with
 * the searched results. Returns null if the input is invalid, Returns the 
 * the id which will be used to retrieve the anime details.
 */
	public void setSelectedAnime(String selectedAnime) {
		// Regex to check if a string contains only special characters
		String regex = "[^0-9]+";
		// Compile the ReGex
		Pattern p = Pattern.compile(regex);
		// Find match between given string & regular expression
		Matcher m = p.matcher(selectedAnime);

		// validation
		if (m.find()) {
			this.selectedAnime = null;
		}

		else {
			this.selectedAnime = selectedAnime;
		}

	}

}
