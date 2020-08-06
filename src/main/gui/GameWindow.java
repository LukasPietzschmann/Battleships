package gui;

import logic.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Die Klasse GameWindow bildet die Nutzeroberfläche für das eigentliche Spielfenster ab, in welchem gespielt wird.
 */
public class GameWindow implements GameEndsListener, GameEventListener, Serializable {
    private final JFrame frame;
    private final String mode;
    private final Logic logic;
    private final LocalPlayer ownPlayer;
    private final Player oppPlayer;

    private JGameCanvas grid1;
    private JGameCanvas grid2;
    private JPanel gridHolder = new JPanel();
    private final JPanel grid1Holder = new JPanel(new GridBagLayout());
    private final JPanel grid2Holder = new JPanel(new GridBagLayout());
    private final JPanel textbarHolder = new JPanel();
    private final JLabel eventLine = new JLabel();
    private final JLabel playersTurnLine = new JLabel();
    private final JPanel statsOptions = new JPanel();
    private final JPanel stats = new JPanel();
    private final JPanel options = new JPanel();
    private JButton saveButton;

    private JPanel mainPanel = new JPanel();

    static Color backgroundColor = MainMenu.backgroundColor;
    static Color textColor = MainMenu.textColor;
    public static Font font = new Font("Krungthep", Font.PLAIN, 20);
    EmptyBorder emptyBorder = new EmptyBorder(3, 3, 3, 3);
    Border activeBorder = BorderFactory.createLineBorder(Color.GREEN, 3);

    /**
     * Konstruktor, erstellt ein GameWindow-Objekt.
     *
     * @param frame Der übergebene Frame des MainWindow
     * @param mode Der Spielmodus
     * @param logic Rückverweis auf die Logik
     */
    public GameWindow(JFrame frame, String mode, Logic logic){
        this.frame = frame;
        this.mode = mode;
        this.logic = logic;
        this.ownPlayer = logic.getOwnPlayer();
        this.oppPlayer = logic.getOppPlayer();
        grid1 = new JGameCanvas(logic.getSize());
        grid2 = new JGameCanvas(logic.getSize());
        ownPlayer.registerGameListener(grid1);
        oppPlayer.registerEnemyGameListener(grid2);
        ownPlayer.registerMakeMove(grid2, grid2.getClickQueue());
        logic.registerGameEndListener(grid2);
        logic.registerGameEndListener(this);
        logic.registerGameEventListener(this);
    }

    /**
     * Erstellt die grafische Benutzeroberfläche für das Spielfenster.
     */
    public void setUpGameWindow(){

        // mainPanel Panel Settings
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setOpaque(true);
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30 , 30, 30, 30));

        // grid1
        grid1.setOpaque(false);
        grid1.setSize(grid1.getPreferredSize());
        grid1.setBorder(activeBorder);
        grid1Holder.setOpaque(false);
        grid1Holder.add(grid1);

        // grid2
        grid2.setOpaque(false);
        grid2.setSize(grid2.getPreferredSize());
        grid2.setBorder(emptyBorder);
        grid2Holder.setOpaque(false);
        grid2Holder.add(grid2);

        // gridHolder
        gridHolder.setLayout(new BoxLayout(gridHolder, BoxLayout.X_AXIS));
        gridHolder.setOpaque(false);

        gridHolder.add(Box.createHorizontalGlue());
        gridHolder.add(grid1Holder);
        gridHolder.add(Box.createHorizontalStrut(30));
        gridHolder.add(grid2Holder);
        gridHolder.add(Box.createHorizontalGlue());

        // mainPanel Layout
        mainPanel.add(textbarHolder, BorderLayout.NORTH);
        mainPanel.add(gridHolder, BorderLayout.CENTER);
        mainPanel.add(statsOptions, BorderLayout.SOUTH);

        // textlines
        eventLine.setText(" ");
        eventLine.setForeground(textColor);
        eventLine.setFont(font);
        eventLine.setOpaque(false);
        eventLine.setSize(new Dimension(200, 200));
        eventLine.setAlignmentX(Component.CENTER_ALIGNMENT);

        playersTurnLine.setText("Aktueller Spieler: Spieler X ist an der Reihe.");
        playersTurnLine.setForeground(textColor);
        playersTurnLine.setFont(font);
        playersTurnLine.setOpaque(false);
        playersTurnLine.setSize(new Dimension(200, 200));
        playersTurnLine.setAlignmentX(Component.CENTER_ALIGNMENT);
        playersTurnLine.setBorder(new EmptyBorder(0, 0, 20, 0));

        textbarHolder.setLayout(new BoxLayout(textbarHolder, BoxLayout.PAGE_AXIS));

        textbarHolder.add(eventLine);
        textbarHolder.add(playersTurnLine);
        textbarHolder.setOpaque(false);

        // stats Elements
        JLabel platzhalter1 = new JLabel("Platzerhalter 1");
        JLabel platzhalter2 = new JLabel("Platzhalter 2");
        JLabel platzhalter3 = new JLabel("Platzerhalter 3");
        JLabel platzhalter4 = new JLabel("Platzhalter 4");

        stats.add(Box.createVerticalStrut(30));
        stats.add(platzhalter1);
        stats.add(platzhalter2);
        stats.add(platzhalter3);
        stats.add(platzhalter4);
        stats.add(Box.createVerticalGlue());

        // buttonHolder Elemente
        JPanel buttonsHolder = new JPanel();
        buttonsHolder.setLayout(new BoxLayout(buttonsHolder, BoxLayout.X_AXIS));
        buttonsHolder.setOpaque(false);
        buttonsHolder.setBorder(null);

        // saveButton
        saveButton = new JButton("Spiel speichern");
        ImageIcon loadIcon = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("loadSaveIcon.png"))).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        saveButton.setIcon(loadIcon);
        saveButton.setHorizontalAlignment(SwingConstants.LEFT);
        saveButton.setBorder(null);
        saveButton.setContentAreaFilled(false);
        saveButton.setToolTipText("Spiel speichern");
        saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        saveButton.setMinimumSize(new Dimension(50, 50));
        saveButton.setMaximumSize(new Dimension(50, 50));
        saveButton.setPreferredSize(new Dimension(50, 50));
       saveButton.addActionListener(arg0 -> {
           String filename = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
           SaveData data = new SaveData();
           data.setMode(logic.getMODE());
           data.setGridSize(logic.getSize());
           data.setMap1(ownPlayer.getMap());
           data.setOwnPlayer(ownPlayer);
           data.setOppPlayer(oppPlayer);
           data.setID(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));

           if (logic.getMODE() == 3) data.setMap2(((LocalPlayer)oppPlayer).getMap());
           try {
               ResourceManager.save(data, filename);
           }
           catch (Exception e){
               System.out.println("Speichern fehlgeschlagen: " + e.getMessage());
           }
           
            JOptionPane.showMessageDialog(mainPanel, "Spiel wurde erfolgreich gespeichert unter:\n" +
                            System.getProperty("user.home") + "\\Documents\\Battleships_Spielstände\\" + filename + ".savegame\n\n" +
                            "Das Spiel kann nun im Hauptmenü wieder geladen werden.",
                    "Spiel wurde gespeichert", JOptionPane.PLAIN_MESSAGE);
           backToMenu();
       });

        // soundButton Button Settings
        JButton soundButton = new JButton();
        soundButton.setText("Lautstärke anpassen");
        Icon soundOnIcon = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("soundOnIcon.png"))).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        Icon soundOffIcon = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("soundOffIcon.png"))).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        if(Launcher.soundPlaying) {
            soundButton.setIcon(soundOnIcon);
        }else {
            soundButton.setIcon(soundOffIcon);
        }
        soundButton.setBorder(null);
        soundButton.setContentAreaFilled(false);
        soundButton.setToolTipText("Lautstärke anpassen");
        soundButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        soundButton.setMinimumSize(new Dimension(50, 50));
        soundButton.setMaximumSize(new Dimension(50, 50));
        soundButton.setPreferredSize(new Dimension(50, 50));
        soundButton.setFocusable(false);
        soundButton.addActionListener(arg0 -> {
            if(Launcher.soundPlaying) {
                soundButton.setIcon(soundOffIcon);
                soundButton.setBorder(null);
                MainWindow.music.stopMusic();
                Launcher.soundPlaying = false;
            }else {
                soundButton.setIcon(soundOnIcon);
                soundButton.setBorder(null);
                MainWindow.music.restartMusic();
                Launcher.soundPlaying = true;
            }
        });

        buttonsHolder.add(Box.createHorizontalGlue());
        buttonsHolder.add(saveButton);
        buttonsHolder.add(Box.createHorizontalStrut(20));
        buttonsHolder.add(soundButton);
        buttonsHolder.add(Box.createHorizontalGlue());

        // statsOptions
        statsOptions.setLayout(new BorderLayout());
        statsOptions.setOpaque(false);
        statsOptions.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 20));
        stats.setLayout(new BoxLayout(stats, BoxLayout.Y_AXIS));
        stats.setOpaque(false);
        options.setLayout(new BoxLayout(options, BoxLayout.X_AXIS));
        options.setOpaque(false);

        JLabel statisticsTitle = new JLabel();
        statisticsTitle.setText("Statistiken");
        statisticsTitle.setForeground(textColor);
        statisticsTitle.setFont(font);

//        statsOptions.add(statisticsTitle, BorderLayout.NORTH);
//        statsOptions.add(stats, BorderLayout.CENTER);
        statsOptions.add(options, BorderLayout.EAST);

        options.add(buttonsHolder);

        // frame Settings
        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
        frame.setResizable(true);
    }

    public void backToMenu(){
        mainPanel.remove(gridHolder);
        frame.remove(mainPanel);
        MainMenu menu = new MainMenu(frame);
        menu.setUpMenu();
    }

    @Override
    public void OnGameEnds(Player winningPlayer) {
        if (winningPlayer == ownPlayer){
            new EndWindow(EndWindow.WIN, frame, this).setUpMainWindow();
        } else {
            new EndWindow(EndWindow.LOSE, frame, this).setUpMainWindow();
        }
    }
    
    @Override
    public void OnOpponentLeft() {
        new EndWindow(EndWindow.OPP_LEFT, frame, this).setUpMainWindow();
    }
    
    @Override
    public void OnEventFired(int event) {
        switch(event){
            case HIT:
                eventLine.setText("Treffer!");
                break;
            case HIT_DEAD:
                eventLine.setText(Launcher.themeIdentifierSingular + " zerstört!");
                break;
            case MISS:
                eventLine.setText("Daneben!");
                break;
        }
    }
    
    @Override
    public void OnPlayersTurn(Player player) {
        if (player == ownPlayer){
            playersTurnLine.setText("Du bist an der Reihe.");
            grid1.setBorder(emptyBorder);
            grid2.setBorder(activeBorder);
            saveButton.setEnabled(true);
        } else {
            playersTurnLine.setText("Gegner ist an der Reihe");
            grid1.setBorder(activeBorder);
            grid2.setBorder(emptyBorder);
            saveButton.setEnabled(false);
        }

    }
}
