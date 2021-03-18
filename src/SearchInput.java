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
		this.animeInput = animeInput;
	}

	public String getSelectedAnime() {
		return selectedAnime;
	}

	public void setSelectedAnime(String selectedAnime) {
		this.selectedAnime = selectedAnime;
	}

}
