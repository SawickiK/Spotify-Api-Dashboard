package pl.edu.pw.mini.spotifyClasses;
import java.util.List;

public class Album extends SpotifyObject{

	private String album_type;
	private List<Artist> artists;
	private String release_date;
	private List<Image> images;


	public String getAlbum_type() {
		return album_type;
	}


	public List<Artist> getArtists() {
		return artists;
	}


	public String getRelease_date() {
		return release_date;
	}


	@Override
	public String toString() {
		return "Album [albumType=" + album_type + ", artists=" + artists + ", id=" + this.getId() + ", name=" + this.getName()
				+ ", releaseDate=" + release_date + "]";
	}

	public String getImage(){
		return images.get(0).getUrl();
	}
	
}
