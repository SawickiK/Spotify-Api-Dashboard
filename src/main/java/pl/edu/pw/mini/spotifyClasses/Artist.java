package pl.edu.pw.mini.spotifyClasses;

import java.util.List;

public class Artist extends SpotifyObject{

	private List<String> genres;
	private List<Image> images;

	public List<String> getGenres() {
		return genres;
	}

	@Override
	public String toString() {
		return "Artist [genres=" + genres + ", id=" + this.getId() + ", name=" + this.getName() + "]";
	}

	public String getImage(){
		return images.get(0).getUrl();
	}

}
