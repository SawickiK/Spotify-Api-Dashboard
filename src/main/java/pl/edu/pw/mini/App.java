package pl.edu.pw.mini;

import pl.edu.pw.mini.Panels.ResultPanel;
import pl.edu.pw.mini.Panels.SearchPanel;
import pl.edu.pw.mini.spotifyClasses.HyperLinkTrack;
import pl.edu.pw.mini.spotifyClasses.TrackSummary;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class App {
    private String searchName;
    private String artistName;
    private JFrame frame = new JFrame();
    private JPanel mainPanel = new JPanel();
    private JPanel searchPanel;
    private ResultPanel resultPanelClass = new ResultPanel("donda");
    private JPanel resultPanel = resultPanelClass.getMainPanel();
    private CardLayout cardLayout = new CardLayout();
    private JMenuItem saveMenu;
    private boolean isDetailed = false;

    public App() throws Exception {
        frame.setTitle("Searchify");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SearchPanel searchPanel = new SearchPanel();
        this.searchPanel = searchPanel.getMainPanel();

        mainPanel.setLayout(cardLayout);
        mainPanel.add(this.searchPanel, "1");
        mainPanel.add(resultPanel, "2");
        frame.setJMenuBar(searchMenuBar());
        cardLayout.show(mainPanel, "1");
        searchPanel.getSearchButton().addActionListener(e -> {
            searchName = searchPanel.getSearchName();
            try {
                if (isDetailed){
                String s = JOptionPane.showInputDialog(
                    frame,
                    "Nazwa artysty",
                    JOptionPane.PLAIN_MESSAGE);
                    artistName = s;
                    resultPanelClass = new ResultPanel(searchName, SearchPanel.stringParser(s));
                }
                else{ resultPanelClass = new ResultPanel(searchName);}
                resultPanel = resultPanelClass.getMainPanel();
                mainPanel.add(resultPanel, "2");
                cardLayout.show(mainPanel, "2");

                setHyperLinks();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(new JFrame(), "Brak piosenki w bazie!");
                cardLayout.show(mainPanel, "1");}
            searchPanel.getSearchField().setText("");
            frame.setJMenuBar(resultMenuBar());
        });

        frame.add(mainPanel);
        frame.setSize(1000, 1000);
        frame.setVisible(true);
        frame.setResizable(false);
    }
    private JMenuBar searchMenuBar() {

        JMenuBar result = new JMenuBar();
        JMenu changeThemeMenu = new JMenu("Change theme");
        JMenuItem blackTheme = new JMenuItem("Black theme");
        JMenuItem lightTheme = new JMenuItem("Light theme");
        JMenu changeDetailed = new JMenu("Search Type");
        JMenuItem detailed = new JMenuItem("Detailed Search Type");
        JMenuItem random = new JMenuItem("Random Search Type");
        blackTheme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPanel.setBackground(new Color(0x212121));
            }
        });
        lightTheme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPanel.setBackground(new Color(0x1ED760));
            }
        });
        detailed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isDetailed = true;
            }
        });
        random.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isDetailed = false;
            }
        });
        changeThemeMenu.add(blackTheme);
        changeThemeMenu.add(lightTheme);

        changeDetailed.add(detailed);
        changeDetailed.add(random);

        result.add(changeThemeMenu);
        result.add(changeDetailed);
        return result;
    }

    private JMenuBar resultMenuBar() {

        JMenuBar result = new JMenuBar();
        JMenu changeThemeMenu = new JMenu("Change theme");
        JMenuItem blackTheme = new JMenuItem("Black theme");
        JMenuItem lightTheme = new JMenuItem("Light theme");
        blackTheme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color grey = new Color(0x212121);
                resultPanel.getComponent(2).setBackground(grey);
                resultPanel.setBackground(grey);
            }
        });
        lightTheme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color green = new Color(0x1ED760);
                resultPanel.getComponent(2).setBackground(green);
                resultPanel.setBackground(green);
            }
        });
        JMenuItem returnMenu = new JMenuItem("Return");
        returnMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.remove(0);
                mainPanel.add(searchPanel, "2");
                cardLayout.show(mainPanel, "2");
                frame.setJMenuBar(searchMenuBar());
            }
        });

        saveMenu = new JMenuItem("Save results");

        saveMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    TrackSummary track;
                    if (isDetailed){
                    track = new TrackSummary(searchName, SearchPanel.stringParser(artistName));
                }else{
                    track = new TrackSummary(searchName);
                }
                    File f = new File((URLDecoder.decode(track.getTrackName(), StandardCharsets.UTF_8)+".txt").replaceAll("/", " "));
                    FileOutputStream fos = new FileOutputStream(f);
                    PrintWriter pw = new PrintWriter(fos);
                    pw.write(track.toString());
                    pw.flush();
                    fos.close();
                    pw.close();
                    System.out.println("Output Written to file");
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });

        changeThemeMenu.add(blackTheme);
        changeThemeMenu.add(lightTheme);
        result.add(changeThemeMenu);
        result.add(returnMenu);
        result.add(saveMenu);
        return result;
    }

    public void setHyperLinks() {
        for (HyperLinkTrack hlt : resultPanelClass.getHyperlinkList()) {
                hlt.getHyperLink().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            System.out.println(hlt.getTrackName());
                            resultPanelClass = new ResultPanel(SearchPanel.stringParser(hlt.getTrackName()), SearchPanel.stringParser(hlt.getArtistName()));
                            searchName = SearchPanel.stringParser(hlt.getTrackName());
                            artistName = SearchPanel.stringParser(hlt.getArtistName());
                            resultPanel = resultPanelClass.getMainPanel();
                            mainPanel.add(resultPanel, "2");
                            cardLayout.show(mainPanel, "2");
                            saveMenu.addActionListener(e1 -> {
                                try
                                {   TrackSummary track;
                                    if (isDetailed){track = new TrackSummary(searchName, artistName);}
                                    else{track = new TrackSummary(searchName);}
                                    File f = new File((URLDecoder.decode(track.getTrackName(), StandardCharsets.UTF_8)+".txt").replaceAll("/", " "));
                                    FileOutputStream fos = new FileOutputStream(f);
                                    PrintWriter pw = new PrintWriter(fos);
                                    pw.write(track.toString());
                                    pw.flush();
                                    fos.close();
                                    pw.close();
                                    System.out.println("Output Written to file");
                                }
                                catch(Exception ex)
                                {
                                    ex.printStackTrace();
                                }
                            });
                            setHyperLinks();
                        } catch (Exception ignored) {}
                        resultPanel = resultPanelClass.getMainPanel();
                    }
            });
        }
    }
}
