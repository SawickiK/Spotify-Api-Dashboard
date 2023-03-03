package pl.edu.pw.mini.spotifyClasses;

import pl.edu.pw.mini.APITools.FetchTool;

import java.util.ArrayList;
import java.util.List;

public class TrackSummary {
	
	private String urlAlbum;
	private String urlArtist;
	private List<Artist> artists;
	private boolean explicit;
	private String trackName;
	private List<Track> trackRecommendations = new ArrayList<>();
	private List<Track> trackTopSongs = new ArrayList<>();
	private TrackAudioFeatures trackAudioFeatures;
	private String albumName;


	public TrackSummary(String nazwaPiosenki) throws Exception{

		String songId = FetchTool.fetchId(nazwaPiosenki).get(0).get(1);
		String artistId = FetchTool.fetchId(nazwaPiosenki).get(1).get(1);
		String albumId = FetchTool.fetchId(nazwaPiosenki).get(2).get(1);

		Track track = FetchTool.fetchTrack(songId);
		TrackAudioFeatures trackAudioFeatures =  FetchTool.fetchTrackAudioFeatures(songId);

		Album album =  FetchTool.fetchAlbum(albumId);
		Artist artist =  FetchTool.fetchArtist(artistId);

		List<String> recommendations = FetchTool.fetchRecommendation(songId, albumId, trackAudioFeatures);
		List<String> top_songs = FetchTool.fetchTopArtistSongs(artistId);

		for (String string : recommendations){
			this.trackRecommendations.add(FetchTool.fetchTrack(string));
		}

		for (String string : top_songs){
			this.trackTopSongs.add(FetchTool.fetchTrack(string));
		}

		this.trackAudioFeatures = trackAudioFeatures;
		this.urlArtist = artist.getImage();
		this.urlAlbum = album.getImage();
		this.explicit = track.getExplicit();
		this.trackName = track.getName();
		this.artists = track.getArtists();
		this.albumName = album.getName();
	}
	public TrackSummary(String nazwaPiosenki, String nazwaArtysty) throws Exception{

		String songId = FetchTool.fetchexactId(nazwaPiosenki, nazwaArtysty).get(0).get(1);
		String artistId = FetchTool.fetchexactId(nazwaPiosenki, nazwaArtysty).get(1).get(1);
		String albumId = FetchTool.fetchexactId(nazwaPiosenki, nazwaArtysty).get(2).get(1);

		Track track = FetchTool.fetchTrack(songId);
		TrackAudioFeatures trackAudioFeatures =  FetchTool.fetchTrackAudioFeatures(songId);

		Album album =  FetchTool.fetchAlbum(albumId);
		Artist artist =  FetchTool.fetchArtist(artistId);

		List<String> recommendations = FetchTool.fetchRecommendation(songId, albumId, trackAudioFeatures);
		List<String> top_songs = FetchTool.fetchTopArtistSongs(artistId);

		for (String string : recommendations){
			this.trackRecommendations.add(FetchTool.fetchTrack(string));
		}

		for (String string : top_songs){
			this.trackTopSongs.add(FetchTool.fetchTrack(string));
		}

		this.trackAudioFeatures = trackAudioFeatures;
		this.urlArtist = artist.getImage();
		this.urlAlbum = album.getImage();
		this.explicit = track.getExplicit();
		this.trackName = track.getName();
		this.artists = track.getArtists();
		this.albumName = album.getName();
	}
	public String getUrlAlbum() {
		return urlAlbum;
	}

	public String getUrlArtist() {
		return urlArtist;
	}

	public List<Artist> getArtists() {
		return artists;
	}

	public boolean isExplicit() {
		return explicit;
	}

	public String getTrackName() {
		return trackName;
	}

	public List<Track> getTrackRecommendations() {
		return trackRecommendations;
	}

	public List<Track> getTrackTopSongs() {
		return trackTopSongs;
	}

	public String getAlbumName() {
		return albumName;
	}

	public TrackAudioFeatures getTrackAudioFeatures() {
		return trackAudioFeatures;
	}

	@Override
	public String toString() {
		return "Summary for " + trackName +":" + "\n"+
				"urlAlbum= " + urlAlbum + "\n" +
				"urlArtist= " + urlArtist + "\n" +
				"artists= " + artists.get(0).getName() + "\n" +
				"explicit= " + explicit + "\n" +
				"trackRecommendations= " + trackRecommendations.stream().map(SpotifyObject::getName).limit(10).toList()+ "\n" +
				artists.get(0).getName() + " top songs= " + trackTopSongs.stream().map(SpotifyObject::getName).limit(10).toList() + "\n" +
				trackAudioFeatures + "\n" +
				"albumName= " + albumName;
	}
}



