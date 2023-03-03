package pl.edu.pw.mini.Panels;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class SearchPanel {

    private JTextField searchField;
    private JButton searchButton;
    private JPanel mainPanel;
    private String searchName;

    public SearchPanel() throws Exception{

        mainPanel = new JPanel();
        Color color = new Color(0x1ED760);
        mainPanel.setBackground(color);

        searchField = new JTextField(20);
        searchField.setBorder(new CompoundBorder(searchField.getBorder(), new EmptyBorder(10, 5, 10, 5)));

        Font font1 = new Font("SansSerif", Font.BOLD, 20);
        searchField.setFont(font1);
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                searchName = ((JTextField)e.getSource()).getText() + e.getKeyChar();
            }
        });

        searchField.setHorizontalAlignment(JTextField.CENTER);

        searchButton = new JButton("Search");

        BufferedImage myPicture = ImageIO.read(new File("src/main/java/pl/edu/pw/mini/logoSearchifyTransparent.png"));
        JLabel logoLabel = new JLabel(new ImageIcon(myPicture));
        logoLabel.setBackground(color);

        mainPanel.add(logoLabel);
        mainPanel.add(searchField, BorderLayout.SOUTH);
        mainPanel.add(searchButton, BorderLayout.AFTER_LAST_LINE);
    }

    public static String stringParser(String string) {
        string = string.toLowerCase().trim();
        string = URLEncoder.encode(string, StandardCharsets.UTF_8);
        return string.replace("+", "%20");
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public String getSearchName() {
        return stringParser(searchName);
    }

    public JTextField getSearchField() {
        return searchField;
    }
}
