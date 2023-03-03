package pl.edu.pw.mini.spotifyClasses;

public class TrackAudioFeatures {
	
	private int duration_ms;
	private double danceability;
	private double energy;
	private double loudness;
	private double valence;
	private double tempo;
	private String id;
	
	public double getDanceability() {
		return danceability;
	}

	public double getEnergy() {
		return energy;
	}

	public double getLoudness() {
		return loudness;
	}

	public double getValence() {
		return valence;
	}

	public double getTempo() {
		return tempo;
	}

	public String getId() {
		return id;
	}

	public int getDuration_ms() {
		return duration_ms;
	}

	@Override
	public String toString() {
		return "duration_ms= " + duration_ms + "\n" + "danceability= " + danceability + "\n" + "energy= "
				+ energy + "\n" + "loudness= " + loudness + "\n"+ "valence= " + valence + "\n"+ "tempo= " + tempo;
	}
	
	
	
}
