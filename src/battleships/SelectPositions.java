package battleships;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author JB
 */
public class SelectPositions extends JFrame implements ActionListener {

    private static final JFrame frame = new JFrame("Select ship positions");
    private static final JPanel panelSouth = new JPanel();
    private static final JPanel panelNorth = new JPanel();
    private static final JButton[] buttons = new JButton[64];
    private static final JButton progress = new JButton("Continue");
    private static final JRadioButton ac = new JRadioButton("Aircraft Carrier");
    private static final JRadioButton mc = new JRadioButton("Mine Clearance");
    private static final JRadioButton f = new JRadioButton("Frigate");
    private static final JRadioButton p = new JRadioButton("Patrol");
    private static ButtonGroup group;
    private static final GridLayout layout = new GridLayout(9, 8);
    private static boolean acFirstClick = true;
    private static boolean mcFirstClick = true;
    private static boolean fFirstClick = true;
    private static boolean pFirstClick = true;
    private static int a;
    private static int b;
    private static int acStart, acStartPlusOne, acStartPlusTwo, acStartPlusThree, acEnd;
    private static int fStart, fStartPlusOne, fStartPlusTwo, fEnd;
    private static int mcStart, mcStartPlusOne, mcEnd;
    private static int pStart, pEnd;

    public SelectPositions() {
        FillArray();
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        panelNorth.setLayout(layout);

        group = new ButtonGroup();
        group.add(ac);
        group.add(mc);
        group.add(f);
        group.add(p);

        ac.addActionListener(this);
        mc.addActionListener(this);
        f.addActionListener(this);
        p.addActionListener(this);
        progress.addActionListener(this);

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].addActionListener(this);
            panelNorth.add(buttons[i]);
        }

        panelSouth.add(ac);
        panelSouth.add(mc);
        panelSouth.add(f);
        panelSouth.add(p);

        frame.add(panelNorth, BorderLayout.NORTH);
        frame.add(panelSouth, BorderLayout.CENTER);
        frame.add(progress, BorderLayout.SOUTH);

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
    
    private void DisableButtonArray() {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setEnabled(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            String but = ((JButton) e.getSource()).getText();
            System.err.println(but);
            if (but.equals("Continue")) {
                if (ac.isEnabled() == false && mc.isEnabled() == false && f.isEnabled() == false
                        && p.isEnabled() == false) {
                    int[] Positions = new int[14];
                    Positions[0] = acStart;
                    Positions[1] = acStartPlusOne;
                    Positions[2] = acStartPlusTwo;
                    Positions[3] = acStartPlusThree;
                    Positions[4] = acEnd;
                    Positions[5] = fStart;
                    Positions[6] = fStartPlusOne;
                    Positions[7] = fStartPlusTwo;
                    Positions[8] = fEnd;
                    Positions[9] = mcStart;
                    Positions[10] = mcStartPlusOne;
                    Positions[11] = mcEnd;
                    Positions[12] = pStart;
                    Positions[13] = pEnd;
                    
                    group.clearSelection();
                    progress.setEnabled(false);
                    DisableButtonArray();
                    try {
                        Client.SendPositions(Positions);
                        SelectAttack f = new SelectAttack();
                    } catch (IOException ex) {
                        Logger.getLogger(SelectPositions.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            

            //CODE FOR PLACING AIRCRAFT CARRIER
//-------------------------------------------------------------
            if (ac.isSelected() && acFirstClick == true) {
                String name = ((JButton) e.getSource()).getName();
                a = Integer.parseInt(name);
                System.out.println("Aircraft Carrier position start = " + a);
                acFirstClick = false;
            } else if (ac.isSelected() && acFirstClick == false) {
                String name = ((JButton) e.getSource()).getName();
                b = Integer.parseInt(name);
                System.out.println("Aircraft Carrier position end = " + b);

                int c = a - b;

                if (c == 4 || c == -4 || c == 32 || c == -32) {
                    if (c == 32 || c == -32) {
                        acStart = a;
                        acStartPlusOne = a + 8;
                        acStartPlusTwo = a + 16;
                        acStartPlusThree = a + 24;
                        acEnd = b;
                        System.out.println("Aircraft Carrier covers these co-ords: \n" + acStart + "\n"
                                + acStartPlusOne + "\n" + acStartPlusTwo + "\n" + acStartPlusThree + "\n"
                                + acEnd);
                        buttons[acStart].setText("AC");
                        buttons[acStartPlusOne].setText("AC");
                        buttons[acStartPlusTwo].setText("AC");
                        buttons[acStartPlusThree].setText("AC");
                        buttons[acEnd].setText("AC");
                        ac.setEnabled(false);
                    } else {
                        acStart = a;
                        acStartPlusOne = a + 1;
                        acStartPlusTwo = a + 2;
                        acStartPlusThree = a + 3;
                        acEnd = b;
                        System.out.println("Aircraft Carrier covers these co-ords: \n" + acStart + "\n"
                                + acStartPlusOne + "\n" + acStartPlusTwo + "\n" + acStartPlusThree + "\n"
                                + acEnd);
                        buttons[acStart].setText("AC");
                        buttons[acStartPlusOne].setText("AC");
                        buttons[acStartPlusTwo].setText("AC");
                        buttons[acStartPlusThree].setText("AC");
                        buttons[acEnd].setText("AC");
                        ac.setEnabled(false);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Aircraft Carrier must span ONLY 5 co-ords");
                    acFirstClick = true;
                }
            }

            //CODE FOR PLACING FRIGATES
//-------------------------------------------------------------
            if (f.isSelected() && fFirstClick == true) {
                String name = ((JButton) e.getSource()).getName();
                a = Integer.parseInt(name);
                System.out.println("Frigate position start = " + a);
                fFirstClick = false;
            } else if (f.isSelected() && fFirstClick == false) {
                String name = ((JButton) e.getSource()).getName();
                b = Integer.parseInt(name);
                System.out.println("Frigate position end = " + b);

                int c = a - b;

                if (c == 3 || c == -3 || c == 24 || c == -24) {
                    if (c == 24 || c == -24) {
                        fStart = a;
                        fStartPlusOne = a + 8;
                        fStartPlusTwo = a + 16;
                        fEnd = b;
                        System.out.println("Frigate covers these co-ords: \n" + fStart + "\n"
                                + fStartPlusOne + "\n" + fStartPlusTwo + "\n" + fEnd);
                        buttons[fStart].setText("F");
                        buttons[fStartPlusOne].setText("F");
                        buttons[fStartPlusTwo].setText("F");
                        buttons[fEnd].setText("F");
                        f.setEnabled(false);
                    } else {
                        fStart = a;
                        fStartPlusOne = a + 1;
                        fStartPlusTwo = a + 2;
                        fEnd = b;
                        System.out.println("Frigate covers these co-ords: \n" + fStart + "\n"
                                + fStartPlusOne + "\n" + fStartPlusTwo + "\n" + fEnd);
                        buttons[fStart].setText("F");
                        buttons[fStartPlusOne].setText("F");
                        buttons[fStartPlusTwo].setText("F");
                        buttons[fEnd].setText("F");
                        f.setEnabled(false);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Frigates must span ONLY 4 co-ords");
                    fFirstClick = true;
                }
            }

            //CODE FOR PLACING MINE CLEARANCE
//-------------------------------------------------------------
            if (mc.isSelected() && mcFirstClick == true) {
                String name = ((JButton) e.getSource()).getName();
                a = Integer.parseInt(name);
                System.out.println("Mine Clearance position start = " + a);
                mcFirstClick = false;
            } else if (mc.isSelected() && mcFirstClick == false) {
                String name = ((JButton) e.getSource()).getName();
                b = Integer.parseInt(name);
                System.out.println("Mine Clearance position end = " + b);

                int c = a - b;

                if (c == 2 || c == -2 || c == 16 || c == -16) {
                    if (c == 16 || c == -16) {
                        mcStart = a;
                        mcStartPlusOne = a + 8;
                        mcEnd = b;
                        System.out.println("Mine Clearance covers these co-ords: \n" + mcStart + "\n"
                                + mcStartPlusOne + "\n" + mcEnd);
                        buttons[mcStart].setText("MC");
                        buttons[mcStartPlusOne].setText("MC");
                        buttons[mcEnd].setText("MC");
                        mc.setEnabled(false);
                    } else {
                        mcStart = a;
                        mcStartPlusOne = a + 1;
                        mcEnd = b;
                        System.out.println("Mine Clearance covers these co-ords: \n" + mcStart + "\n"
                                + mcStartPlusOne + "\n" + mcEnd);
                        buttons[mcStart].setText("MC");
                        buttons[mcStartPlusOne].setText("MC");
                        buttons[mcEnd].setText("MC");
                        mc.setEnabled(false);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Mine Clearance vessels must span ONLY 3 co-ords");
                    mcFirstClick = true;
                }
            }

            //CODE FOR PLACING PATROL
//-------------------------------------------------------------
            if (p.isSelected() && pFirstClick == true) {
                String name = ((JButton) e.getSource()).getName();
                a = Integer.parseInt(name);
                System.out.println("Patrol position start = " + a);
                pFirstClick = false;
            } else if (p.isSelected() && pFirstClick == false) {
                String name = ((JButton) e.getSource()).getName();
                b = Integer.parseInt(name);
                System.out.println("Patrol position end = " + b);

                int c = a - b;

                if (c == 1 || c == -1 || c == 8 || c == -8) {
                    if (c == 8 || c == -8) {
                        pStart = a;
                        pEnd = b;
                        System.out.println("Patrol covers these co-ords: \n" + pStart + "\n" + pEnd);
                        buttons[pStart].setText("P");
                        buttons[pEnd].setText("P");
                        p.setSelected(false);
                        p.setEnabled(false);
                    } else {
                        pStart = a;
                        pEnd = b;
                        System.out.println("Patrol covers these co-ords: \n" + pStart + "\n" + pEnd);
                        buttons[pStart].setText("P");
                        buttons[pEnd].setText("P");
                        p.setSelected(false);
                        p.setEnabled(false);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Patrol vessels must span ONLY 2 co-ords");
                    pFirstClick = true;
                }
            }

        } else if (e.getSource() instanceof JRadioButton) {
            String name = ((JRadioButton) e.getSource()).getText();
            System.out.println(name + " is selected");
        }
    }
}
