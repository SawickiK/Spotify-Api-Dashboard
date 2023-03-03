package pl.edu.pw.mini.Panels;

import pl.edu.pw.mini.spotifyClasses.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ResultPanel {

    private Color color = new Color(0x1ED760);
    private JPanel mainPanel;

    private JLabel resultsLabel = new JLabel();
    private JLabel albumImage;
    private JLabel artistImage;
    private JLabel albumName;
    private JLabel artistsName;
    private JLabel topSongs;
    private JPanel trackRecommendations;
    private JLabel trackInfo;
    private JLabel title;
    private List<HyperLinkTrack> hyperlinkList = new ArrayList<>();
    private int imageWidth = 350;
    private int imageHeight = 350;

    public ResultPanel(String name) throws Exception {
        mainPanel = new JPanel();
        mainPanel.setBackground(color);
        TrackSummary trackSummary = new TrackSummary(name);
        ArrayList<Object> list = labelMaker(trackSummary);
        albumImage = (JLabel) list.get(0);
        artistImage = (JLabel) list.get(1);
        artistsName = (JLabel) list.get(2);
        albumName = (JLabel) list.get(3);
        topSongs = (JLabel) list.get(4);
        trackRecommendations = (JPanel) list.get(5);
        trackInfo = (JLabel) list.get(6);
        title = (JLabel) list.get(7);
        //Ponizsza kolejnosc wazna, nie zmieniać
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 55;
        c.insets = new Insets(0,0,0,90);
        c.gridwidth = 2;
        mainPanel.add(title, c);

        c.ipady = 0;
        c.gridy = 1;
        mainPanel.add(trackInfo, c);

        c.gridy = 2;
        mainPanel.add(trackRecommendations, c);

        c.gridy = 3;
        mainPanel.add(topSongs, c);


        c.gridx = 3;
        c.gridy = 0;
        c.insets = new Insets(0,0,0,0);
        mainPanel.add(artistsName, c);

        c.gridy = 1;
        mainPanel.add(artistImage, c);

        c.gridy = 2;
        mainPanel.add(albumName, c);

        c.gridy = 3;
        mainPanel.add(albumImage, c);
    }
    public ResultPanel(String trackName, String artistName) throws Exception {
        mainPanel = new JPanel();

        mainPanel.setBackground(color);
        TrackSummary trackSummary = new TrackSummary(trackName, artistName);
        ArrayList<Object> list = labelMaker(trackSummary);
        albumImage = (JLabel) list.get(0);
        artistImage = (JLabel) list.get(1);
        artistsName = (JLabel) list.get(2);
        albumName = (JLabel) list.get(3);
        topSongs = (JLabel) list.get(4);
        trackRecommendations = (JPanel) list.get(5);
        trackInfo = (JLabel) list.get(6);
        title = (JLabel) list.get(7);
        //Ponizsza kolejnosc wazna, nie zmieniać
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 55;
        c.insets = new Insets(0,0,0,90);
        c.gridwidth = 2;
        mainPanel.add(title, c);

        c.ipady = 0;
        c.gridy = 1;
        mainPanel.add(trackInfo, c);

        c.gridy = 2;
        mainPanel.add(trackRecommendations, c);

        c.gridy = 3;
        mainPanel.add(topSongs, c);


        c.gridx = 3;
        c.gridy = 0;
        c.insets = new Insets(0,0,0,0);
        mainPanel.add(artistsName, c);

        c.gridy = 1;
        mainPanel.add(artistImage, c);

        c.gridy = 2;
        mainPanel.add(albumName, c);

        c.gridy = 3;
        mainPanel.add(albumImage, c);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }


    private ArrayList<Object> labelMaker(TrackSummary trackSummary) throws Exception{
        ArrayList<Object> list = new ArrayList<>();
        Font font = new Font("SansSerif", Font.BOLD, 16);
        Font fontBig = new Font("SansSerif", Font.BOLD, 20);
        //Album index 0
        String pathAlbum = trackSummary.getUrlAlbum();
        URL urlAlbum = new URL(pathAlbum);
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(ImageIO.read(urlAlbum)).getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT));
        JLabel imageAlbum = new JLabel(imageIcon);
        list.add(imageAlbum);

        //Artist index 1
        String pathArtist = trackSummary.getUrlArtist();
        URL urlArtist = new URL(pathArtist);
        ImageIcon imageIcon2 = new ImageIcon(new ImageIcon(ImageIO.read(urlArtist)).getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT));
        JLabel imageArtist = new JLabel(imageIcon2);
        list.add(imageArtist);

        //Artists name index 2
        JLabel artistsName = new JLabel("Artist: " + trackSummary.getArtists().get(0).getName(), SwingConstants.CENTER);
        list.add(artistsName);

        //Album name index 3
        JLabel albumName = new JLabel("Album: " + trackSummary.getAlbumName(), SwingConstants.CENTER);
        list.add(albumName);

        //Artist top songs index 4
        List<String> topArtistsongs = trackSummary.getTrackTopSongs().stream().map(SpotifyObject::getName).limit(5).collect(Collectors.toList());
        JLabel toptracks = new JLabel(convertToMultiline("Best songs from " + trackSummary.getArtists().get(0).getName() + "\n" + String.join("\n",topArtistsongs)));
        list.add(toptracks);

        //Track recommendations index 5
        List<Track> trackList = trackSummary.getTrackRecommendations();
        JPanel panel = new JPanel();
        JLabel jl = new JLabel("You may also like: ");

        jl.setFont(fontBig);
        jl.setForeground(Color.white);
        panel.add(jl);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for (Track track : trackList){
            HyperLinkTrack hyperLinkTrack = new HyperLinkTrack(track);
            hyperlinkList.add(hyperLinkTrack);
            panel.add(hyperLinkTrack.generateHyperlink(track));
        }

        panel.setBackground(mainPanel.getBackground());
        list.add(panel);

        //Track info index 6
        TrackAudioFeatures info = trackSummary.getTrackAudioFeatures();
        String explicitString = "";
        if (trackSummary.isExplicit()){
            explicitString = trackSummary.getTrackName() + " includes language that is generally deemed violent or offensive in nature";
        }
        JLabel tracksinfo = new JLabel(convertToMultiline(trackSummary.getTrackName() + " audio features:" + "\n" + info.toString() + "\n" + explicitString));
        list.add(tracksinfo);
        list.stream().filter(k -> k instanceof JLabel).forEach( (jLabel) -> ((JLabel) jLabel).setForeground(Color.white));
        list.stream().filter(k -> k instanceof JLabel).forEach( (jLabel) -> ((JLabel) jLabel).setFont(font));
        //Title index 7
        JLabel title = new JLabel("Results for: " + trackSummary.getTrackName(), SwingConstants.CENTER);
        title.setForeground(Color.white);
        title.setFont(new Font("SansSerif", Font.BOLD, (int) Math.min(40, 70/Math.log(trackSummary.getTrackName().length()+10))));
        list.add(title);


        return list;
    }

    public static String convertToMultiline(String orig)
    {
        return "<html>" + orig.replaceAll("\n", "<br>");
    }

    public List<HyperLinkTrack> getHyperlinkList() {
        return hyperlinkList;
    }
}
