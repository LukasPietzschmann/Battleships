package gui;

import logic.Launcher;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class LoadingScreen extends JFrame {
    private final JFrame frame;
    private final ImageIcon background;
    private final Icon loadingAnimation;
    private Font font;
    private Color fontColor;
    private final JLabel line1 = new JLabel();
    private final JLabel line2 = new JLabel();
    private final JLabel line3 = new JLabel();
    
    public static final int WAIT_FOR_SHIP_PLACEMENT = 1;
    public static final int WAIT_FOR_CONNECT = 0;

    public LoadingScreen(JFrame frame, int mode){
        this.frame = frame;
        background = loadBackground();
        loadText(mode);
        loadFont();
        loadingAnimation = new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("spinner.gif")).getImage().getScaledInstance(130, 130, Image.SCALE_DEFAULT));
        loadScreen();
    }

    private void loadText(int mode){
        switch (mode){
            case WAIT_FOR_CONNECT:
                line1.setText("Einen Moment noch!");
                line2.setText("Es wird auf den Client gewartet");
                break;
            case WAIT_FOR_SHIP_PLACEMENT:
                line1.setText("Einen Augenblick noch!");
                line2.setText("Der Gegner platziert seine");
                line3.setText(Launcher.themeIdentifierPlural + ".");
                break;
        }
    }

    private ImageIcon loadBackground(){
        ArrayList<ImageIcon> list;
        switch (Launcher.theme){
            case "Battleships":
                ArrayList<ImageIcon> battleshipsImages= new ArrayList<>();
                battleshipsImages.add(new ImageIcon(new ImageIcon("src/res/LS_Battleships_1.jpg").getImage().getScaledInstance(1130, 700, Image.SCALE_SMOOTH)));
                battleshipsImages.add(new ImageIcon(new ImageIcon("src/res/LS_Battleships_2.jpg").getImage().getScaledInstance(1130, 700, Image.SCALE_SMOOTH)));
                battleshipsImages.add(new ImageIcon(new ImageIcon("src/res/LS_Battleships_3.jpg").getImage().getScaledInstance(1130, 700, Image.SCALE_SMOOTH)));
                battleshipsImages.add(new ImageIcon(new ImageIcon("src/res/LS_Battleships_4.jpg").getImage().getScaledInstance(1130, 700, Image.SCALE_SMOOTH)));
                battleshipsImages.add(new ImageIcon(new ImageIcon("src/res/LS_Battleships_5.jpg").getImage().getScaledInstance(1130, 700, Image.SCALE_SMOOTH)));
                battleshipsImages.add(new ImageIcon(new ImageIcon("src/res/LS_Battleships_6.jpg").getImage().getScaledInstance(1130, 700, Image.SCALE_SMOOTH)));
                list = battleshipsImages;
                break;
            case "Battlecars":
                ArrayList<ImageIcon> battlecarsImages= new ArrayList<>();
                battlecarsImages.add(new ImageIcon(new ImageIcon("src/res/LS_Battlecars_1.jpg").getImage().getScaledInstance(1130, 700, Image.SCALE_SMOOTH)));
                battlecarsImages.add(new ImageIcon(new ImageIcon("src/res/LS_Battlecars_2.jpg").getImage().getScaledInstance(1130, 700, Image.SCALE_SMOOTH)));
                battlecarsImages.add(new ImageIcon(new ImageIcon("src/res/LS_Battlecars_3.jpg").getImage().getScaledInstance(1130, 700, Image.SCALE_SMOOTH)));
                list = battlecarsImages;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + Launcher.theme);
        }
        Random ran = new Random();
        int randomNumber = ran.nextInt(list.size());
        return list.get(randomNumber);
    }

    private void loadFont(){
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/res/Krungthep.ttf")));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        font = new Font("Krungthep", Font.PLAIN, 20);
        fontColor = MainMenu.textColor;
    }

    private void loadScreen(){
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

        line1.setFont(font);
        line1.setForeground(fontColor);
        line1.setAlignmentX(Component.CENTER_ALIGNMENT);
        line1.setBackground(new Color(35,35,35,180));
        line1.setOpaque(true);

        line2.setFont(font);
        line2.setForeground(fontColor);
        line2.setAlignmentX(Component.CENTER_ALIGNMENT);
        line2.setBackground(new Color(35,35,35,180));
        line2.setOpaque(true);

        line3.setFont(font);
        line3.setForeground(fontColor);
        line3.setAlignmentX(Component.CENTER_ALIGNMENT);
        line3.setBackground(new Color(35,35,35,180));
        line3.setOpaque(true);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setSize(400, 300);
        panel.setLocation(380, 200);
        panel.setOpaque(false);
        panel.add(Box.createVerticalGlue());
        panel.add(line1);
        panel.add(Box.createVerticalStrut(15));
        panel.add(loadingLabel);
        panel.add(Box.createVerticalStrut(15));
        panel.add(line2);
        panel.add(line3);
        panel.add(Box.createVerticalGlue());

        layeredPane.add(panel, JLayeredPane.POPUP_LAYER);

        frame.add(layeredPane);
        frame.setLocationRelativeTo(frame);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1130, 700);
        new LoadingScreen(frame, WAIT_FOR_CONNECT);
    }
}