/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author JB
 */
public class SelectAttack extends JFrame implements ActionListener {
    
    private static final JFrame frame = new JFrame("Select ship positions");
    private static final JPanel panelSouth = new JPanel();
    private static final JPanel panelNorth = new JPanel();
    private static final JButton[] buttons = new JButton[64];
    private static final JButton fire = new JButton("FIRE");
    private static final GridLayout layout = new GridLayout(9, 8);
    private static int selectedPosition;
    private static int successfulShot;
    
    public SelectAttack() {
        FillArray();
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        panelNorth.setLayout(layout);
        
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].addActionListener(this);
            panelNorth.add(buttons[i]);
        }
        
        fire.addActionListener(this);
        fire.setName("FIRE");
        
        frame.add(panelNorth, BorderLayout.NORTH);
        frame.add(fire, BorderLayout.SOUTH);
        
        frame.setSize(520, 350);
        frame.setVisible(true);
    }
    
    private void FillArray() {
        for (int i = 0; i < buttons.length; i++) {
            String name = Integer.toString(i);
            buttons[i] = new JButton(String.valueOf(i));
            buttons[i].setName(name);
            buttons[i].setText("");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            String but = ((JButton) e.getSource()).getName();
            System.err.println(but);
            if (but.equals("FIRE")) {
                try {
                    successfulShot = Client.AttackPosition(selectedPosition);
                } catch (IOException ex) {
                    Logger.getLogger(SelectAttack.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                if (successfulShot == 1) {
                    buttons[selectedPosition].setOpaque(true);
                    buttons[selectedPosition].setBackground(Color.GREEN);
                } else if (successfulShot == 2) {
                    buttons[selectedPosition].setOpaque(true);
                    buttons[selectedPosition].setBackground(Color.RED);
                } else if (successfulShot == 3) {
                    JOptionPane.showMessageDialog(null, "YOU HAVE WON");
                } else if (successfulShot == 4) {
                    JOptionPane.showMessageDialog(null, "WAIT YOUR TURN");
                }
            } else {
                selectedPosition = Integer.parseInt(but);
            }
        }
    }
}
