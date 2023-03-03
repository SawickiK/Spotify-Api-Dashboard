package pl.edu.pw.mini.APITools;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import com.google.gson.Gson;

import pl.edu.pw.mini.spotifyClasses.*;

public class FetchTool {
    public static URLparser urlParser= new URLparser();
    public static class URLparser {
        public final SpotifyToken spotifyToken = new SpotifyToken();
        public final Gson gson = new Gson();

        public String URL2JSON(URL url) throws IOException {
            spotifyToken.get();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", spotifyToken.accessToken);

            InputStream result = null;

            try {
                result = connection.getInputStream();
            } catch (IOException e) {
            }
            String s = new String(result.readAllBytes());
            return s;
        }

        public Object URL2Object(URL url, Class clazz) {
            String s = null;
            try {
                s = URL2JSON(url);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return gson.fromJson(s, clazz);
        }
    }

    public static class JSONparser {
        private final static Gson gson = new Gson();
        //Tworzymy klasy odpowiadające kolejnym poziomom zagnieżdżeń
        //JSON'a wynikowanego otrzymanego po zapytaniu search
        public class Response {
            private Tracks tracks;
            public Tracks getTracks(){
                return tracks;
            }
        }
        public class Tracks {
            private String href;
            private List<Item> items;
            private int limit;
            private String offset;
            private boolean previous;
            private int total;

            public List<Item> getItems() {
                return items;
            }
        }
        public class Item {
            private ParseAlbum album;
            private List<ParseArtist> artists;
            private String id;
            private String name;

            public ParseAlbum getAlbum() {
                return album;
            }

            public List<ParseArtist> getArtists() {
                return artists;
            }

            public String getId() {
                return id;
            }

            public String getName() {
                return name;
            }
        }
        public class ParseAlbum {
            private String id;
            private String name;

            public String getId() {
                return id;
            }

            public String getName() {
                return name;
            }
        }
        public class ParseArtist {
            private String id;
            private String name;

            public String getId() {
                return id;
            }

            public String getName() {
                return name;
            }
        }

        public static List<List<String>> JSON2IdList(String json){
            Response response = gson.fromJson(json, Response.class);
            String trackName = response.getTracks().getItems().get(0).getName();
            String trackId = response.getTracks().getItems().get(0).getId();
            String artistName = response.getTracks().getItems().get(0).getArtists().get(0).getName();
            String artistId = response.getTracks().getItems().get(0).getArtists().get(0).getId();
            String albumName = response.getTracks().getItems().get(0).getAlbum().getName();
            String albumId = response.getTracks().getItems().get(0).getAlbum().getId();

            List<List<String>> res = new ArrayList<>();
            res.add(Arrays.asList(trackName, trackId));
            res.add(Arrays.asList(artistName, artistId));
            res.add(Arrays.asList(albumName,albumId));
            return res;
        }
        public static List<String> JSON2Artists(String json){
            Response response = gson.fromJson(json, Response.class);
            List<String> lista = new ArrayList<>();
            for (Item item : response.getTracks().getItems()){
                lista.add(item.getArtists().get(0).getName());
            }
            return lista;
        }
    }

    //Zwraca listę id track, id Artysty, id Albumu
    public static List<List<String>> fetchId (String q) throws MalformedURLException, IOException {
        URL url = new URL("https://api.spotify.com/v1/search?" + "type=track&limit=1&q=" + q + "%20track:" + q);
        String dane = urlParser.URL2JSON(url);

        return JSONparser.JSON2IdList(dane);
    }
    public static List<List<String>> fetchexactId (String q, String k) throws MalformedURLException, IOException {
        URL url = new URL("https://api.spotify.com/v1/search?" + "type=track&limit=1&q=" + q + "%20track:" + q + "%20artist:" + k);
        String dane = urlParser.URL2JSON(url);

        return JSONparser.JSON2IdList(dane);
    }
    public static List<String> fetchAllArtist (String q) throws IOException{
        URL url = new URL("https://api.spotify.com/v1/search?" + "type=track&limit=1&q=" + q + "%20track:" + q);
        String dane = urlParser.URL2JSON(url);
        return JSONparser.JSON2Artists(dane);
    }

    public static Track fetchTrack(String id) throws IOException, InterruptedException {
		URL url = new URL("https://api.spotify.com/v1/tracks/" + id);
		return (Track) urlParser.URL2Object(url, Track.class);
	}
    
    public static Artist fetchArtist(String id) throws IOException, InterruptedException {
		URL url = new URL("https://api.spotify.com/v1/artists/" + id);
		return (Artist) urlParser.URL2Object(url, Artist.class);
	}
    
    public static Album fetchAlbum(String id) throws IOException, InterruptedException {
		URL url = new URL("https://api.spotify.com/v1/albums/" + id);
		return (Album) urlParser.URL2Object(url, Album.class);
	}
    
    public static TrackAudioFeatures fetchTrackAudioFeatures(String id) throws IOException, InterruptedException {
		URL url = new URL("https://api.spotify.com/v1/audio-features/" + id);
		return (TrackAudioFeatures) urlParser.URL2Object(url, TrackAudioFeatures.class);
	}

    public static List<String> fetchRecommendation(String idPiosenki, String idArtysty, TrackAudioFeatures taf) throws MalformedURLException {

        String min_dance = Double.toString(Math.min(0.0, taf.getDanceability()-0.1));
        String max_dance = Double.toString(Math.max(1.0, taf.getDanceability()+0.1));
        String min_energy = Double.toString(Math.min(0.0, taf.getEnergy()-0.1));
        String max_energy = Double.toString(Math.max(1.0, taf.getEnergy()+0.1));
        String min_loudness = Double.toString(taf.getLoudness()-1);
        String max_loudness = Double.toString(taf.getLoudness()+1);
        String min_tempo = Double.toString(taf.getTempo()-6);
        String max_tempo = Double.toString(taf.getTempo()+6);
        String min_valence = Double.toString(Math.min(0.0, taf.getValence()-0.1));
        String max_valence = Double.toString(Math.max(1.0, taf.getValence()+0.1));

        URL url = new URL("https://api.spotify.com/v1/recommendations?limit=5" //limit domyslnie na 5
                + "&seed_artists="+ idArtysty
                + "&seed_tracks=" + idPiosenki
                + "&min_danceability=" + min_dance
                + "&max_danceability=" + max_dance
                + "&min_energy" + min_energy
                + "&max_energy" + max_energy
                + "&min_loudness=" + min_loudness
                + "&max_loudness=" + max_loudness
                + "&min_tempo=" + min_tempo
                + "&max_tempo=" + max_tempo
                + "&min_valence=" + min_valence
                + "&max_valence=" + max_valence);
        Recommendation recommendation = (Recommendation) urlParser.URL2Object(url, Recommendation.class);
        List<String> res = new ArrayList<>();
        for (Track track : recommendation.getTracks()) {
            res.add(track.getId());
        }
        return res;
    }
    public static List<String> fetchTopArtistSongs(String id) throws MalformedURLException {
        URL url = new URL("https://api.spotify.com/v1/artists/"+ id +"/top-tracks?market=PL");
        Recommendation recommendation = (Recommendation) urlParser.URL2Object(url, Recommendation.class);
        List<String> lista = new ArrayList<>();
        for (Track track : recommendation.getTracks()){
            lista.add(track.getId());
        }
        return lista;
    }

}

