package pl.edu.pw.mini.spotifyClasses;

import javax.swing.*;
import java.awt.*;

public class HyperLinkTrack extends Track{

    private String artistName;
    private String trackName;
    private JLabel hyperLink;

    public HyperLinkTrack(Track track) {
        this.artistName = track.getArtists().get(0).getName();
        this.trackName = track.getName();
    }

    public JLabel generateHyperlink(Track track){
        JLabel hyperlink = new JLabel("<html>" +track.getArtists().get(0).getName() + " - " + track.getName() + "<br>");
        hyperlink.setForeground(new Color(0x001FFF));
        hyperlink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Font boldUnderline = new Font("SansSerif",Font.BOLD, 14);
        hyperlink.setFont(boldUnderline);
        this.hyperLink = hyperlink;
        return hyperlink;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getTrackName() {
        return trackName;
    }

    public JLabel getHyperLink() {
        return hyperLink;
    }
}
