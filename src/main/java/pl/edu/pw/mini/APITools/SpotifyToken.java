package pl.edu.pw.mini.APITools;

import com.google.gson.*;
import pl.edu.pw.mini.spotifyClasses.Client_Credentials;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SpotifyToken {
    public String accessToken;
    private Client_Credentials client_credentials = new Client_Credentials();
    public void get() throws IOException {

        if (client_credentials.getExpires_in() != 0){
        	return;
        }

        URL url = new URL("https://accounts.spotify.com/api/token");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");

        Base64.Encoder encoder = Base64.getEncoder();
        byte[] byteRepr = (Client_Credentials.clientId + ":" + Client_Credentials.clientSecret).getBytes(StandardCharsets.UTF_8);

        String basicAuthorization = "Basic " + encoder.encodeToString(byteRepr);

        connection.setRequestProperty("Authorization", basicAuthorization);
        byte[] out = "grant_type=client_credentials".getBytes(StandardCharsets.UTF_8);
        try (OutputStream os = connection.getOutputStream()) {
            os.write(out);
        }

        String s = new String(connection.getInputStream().readAllBytes());

        client_credentials = new Gson().fromJson(s, Client_Credentials.class);

        accessToken = "Bearer " + client_credentials.getAccess_token();
        connection.disconnect();
    }
}

