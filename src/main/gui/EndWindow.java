package gui;

import javax.swing.*;
import java.awt.*;

public class EndWindow {
    JFrame frame;
    GameWindow gw;
    JDialog dialog = new JDialog();
    JPanel mainPanel = new JPanel();
    JPanel rightPanel = new JPanel();

    ImageIcon icon = new ImageIcon();
    JLabel message = new JLabel();

    EndWindow(int result, JFrame frame, GameWindow gw){
        this.frame = frame;
        this.gw = gw;
        switch (result) {
            case (0):
                icon = new ImageIcon(new ImageIcon("src/res/win.png").getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH));
                message.setText("<html>Glückwunsch! <p/>Du hast gewonnen!</html>");
                break;
            case (1):
                icon = new ImageIcon(new ImageIcon("src/res/lose.png").getImage().getScaledInstance(190, 180, Image.SCALE_SMOOTH));
                message.setText("<html>Schade! <p/>Du hast verloren!</html>");
                break;
        }
    }

    public void setUpMainWindow(){

        dialog.setSize(500, 300);
        dialog.setResizable(false);
        dialog.getContentPane().setBackground(MainMenu.backgroundColor);
        dialog.setUndecorated(true);
        dialog.setLocationRelativeTo(frame);
        dialog.setAlwaysOnTop(true);
        dialog.getRootPane().
                setBorder(BorderFactory.createLineBorder(MainMenu.textColor, 2, true));

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.setOpaque(false);
        mainPanel.setBackground(MainMenu.backgroundColor);

        JLabel iconHolder = new JLabel();
        iconHolder.setIcon(icon);

        mainPanel.add(Box.createHorizontalStrut(40));
        mainPanel.add(iconHolder);
        mainPanel.add(Box.createHorizontalStrut(30));
        mainPanel.add(rightPanel);
        mainPanel.add(Box.createHorizontalStrut(30));

        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(MainMenu.backgroundColor);

        message.setForeground(MainMenu.textColor);
        message.setFont(MainWindow.font);

        ImageIcon menu = new ImageIcon(new ImageIcon("src/res/hauptmenue.png").getImage().getScaledInstance(160, 60, Image.SCALE_SMOOTH));
        JButton button = new JButton();
        button.setIcon(menu);
        button.setBorder(null);
        button.setToolTipText("Zurück zum Hauptmenü");
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addActionListener(arg0 -> {
            dialog.dispose();
            gw.backToMenu();
        });

        rightPanel.add(message);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(button);

        dialog.add(mainPanel);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setVisible(true);
    }

}