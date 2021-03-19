public class SearchDetails {
	private String title;
	private String link;
	
	/**
	 * This is a default constructor.
	 */
	public SearchDetails() {

	}
	
	/**
	 * This is a default constructor with parameters
	 */
	public SearchDetails(String title, String link) {
		this.title = title;
		this.link = link;
	}
	
	/**
	 * Get the anime title
	 * @return the string of the anime title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Set the anime title
	 * @param title the string of the anime title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Get the anime link
	 * @return the string of the anime link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Set the anime link
	 * @param link the string of the anime link
	 */
	public void setLink(String link) {
		this.link = link;
	}
}
