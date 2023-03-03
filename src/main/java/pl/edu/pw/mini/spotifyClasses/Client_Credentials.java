package pl.edu.pw.mini.spotifyClasses;

public class Client_Credentials {
	private String access_token;
	private int expires_in;
	public static final String clientId = "b4a4fa3315e44ebda8701c684cc5f43e";
	public static final String clientSecret = "605e78b16bee4a499dbce461cdf57e6d";
	public String getAccess_token() {
		return access_token;
	}

	public int getExpires_in(){
		return expires_in;
	}
}
