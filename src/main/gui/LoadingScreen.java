package gui;

import logic.Launcher;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class LoadingScreen extends JFrame {
    private final JFrame frame;
    private ImageIcon background;
    private final Icon loadingAnimation;
    private final Font font;
    private final Color fontColor;

    public LoadingScreen(JFrame frame){
        this.frame = frame;
        switch(Launcher.theme){
            case "Battleships":
                background = new ImageIcon(new ImageIcon("src/res/LS_Battleships.jpg").getImage().getScaledInstance(1130, 700, Image.SCALE_SMOOTH));
                break;
            case "Battlecars":
                background = new ImageIcon(new ImageIcon("src/res/LS_Battlecars.jpg").getImage().getScaledInstance(1130, 700, Image.SCALE_SMOOTH));
                break;
        }
        loadingAnimation = new ImageIcon(new ImageIcon("src/res/spinner.gif").getImage().getScaledInstance(130, 130, Image.SCALE_DEFAULT));
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/res/Krungthep.ttf")));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        font = new Font("Krungthep", Font.PLAIN, 20);
        fontColor = MainMenu.textColor;
        loadScreen();
    }

    public void loadScreen(){
        JLayeredPane layeredPane = new JLayeredPane();

        JLabel label = new JLabel();
        label.setSize(1130, 700);
        label.setIcon(background);
        label.setLocation(0, 0);
        layeredPane.add(label, JLayeredPane.DEFAULT_LAYER);

        JLabel loadingLabel = new JLabel();
        loadingLabel.setSize(200, 200);
        loadingLabel.setIcon(loadingAnimation);
        loadingLabel.setLocation(465, 200);
        loadingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel text1 = new JLabel();
        text1.setText("Einen Augenblick noch!");
        text1.setFont(font);
        text1.setForeground(fontColor);
        text1.setAlignmentX(Component.CENTER_ALIGNMENT);
        text1.setBackground(new Color(35,35,35,180));
        text1.setOpaque(true);

        JLabel text2 = new JLabel();
        text2.setText("Der Gegner platziert seine");
        text2.setFont(font);
        text2.setForeground(fontColor);
        text2.setAlignmentX(Component.CENTER_ALIGNMENT);
        text2.setBackground(new Color(35,35,35,180));
        text2.setOpaque(true);

        JLabel text3 = new JLabel();
        text3.setText(Launcher.themeIdentifierPlural + ".");
        text3.setFont(font);
        text3.setForeground(fontColor);
        text3.setAlignmentX(Component.CENTER_ALIGNMENT);
        text3.setBackground(new Color(35,35,35,180));
        text3.setOpaque(true);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setSize(400, 300);
        panel.setLocation(380, 200);
        panel.setOpaque(false);
        panel.add(Box.createVerticalGlue());
        panel.add(text1);
        panel.add(Box.createVerticalStrut(15));
        panel.add(loadingLabel);
        panel.add(Box.createVerticalStrut(15));
        panel.add(text2);
        panel.add(text3);
        panel.add(Box.createVerticalGlue());

        layeredPane.add(panel, JLayeredPane.POPUP_LAYER);

        frame.add(layeredPane);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(1130, 700);
        new LoadingScreen(frame);
    }
}