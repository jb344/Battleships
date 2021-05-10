package battleships;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author JB
 */
public class MainMenu implements ActionListener {

    private static final JFrame frame = new JFrame("Battleships");
    private static final JButton play = new JButton("Play");
    private static final JButton close = new JButton("Close");
    public static boolean connected = true; //SHOULD BE INITIALISED TO FALSE

    public MainMenu() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        play.setActionCommand("Connect");
        play.addActionListener(this);

        close.setActionCommand("Close");
        close.addActionListener(this);

        frame.getContentPane().add(play, BorderLayout.CENTER);
        frame.getContentPane().add(close, BorderLayout.SOUTH);
        frame.setSize(200, 200);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == play) {
            connected = Client.ClientConnect();

            if (connected) {
                SelectPositions f = new SelectPositions();
            }
        } else if (e.getSource() == close) {
            if (connected) {
                try {
                    Client.Terminate();
                    frame.setVisible(false);
                    System.exit(1);
                } catch (IOException ex) {
                    Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
