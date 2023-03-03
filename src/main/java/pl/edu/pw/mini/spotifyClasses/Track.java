package pl.edu.pw.mini.spotifyClasses;

import java.util.List;

public class Track extends SpotifyObject{
	
	private List<Artist> artists;
	private boolean explicit;

	public boolean getExplicit() {
		return explicit;
	}

	public List<Artist> getArtists() {
		return artists;
	}

	public boolean isExplicit() {
		return explicit;
	}



}
