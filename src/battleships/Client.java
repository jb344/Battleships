/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author JB
 */
public class Client {

    public static boolean running;
    public static int resultInt;
    public static Socket clientSocket;
    public static PrintWriter out;
    public static BufferedReader in;
    public static ObjectOutputStream objectOut;
    public static String result;
    Scanner keyboard = new Scanner(System.in);

    public static boolean ClientConnect() {

        String portNumber = JOptionPane.showInputDialog("Enter a PORT NUMBER");
        int portNo = Integer.parseInt(portNumber);

        String hostName = JOptionPane.showInputDialog("Enter a HOST NAME");

        if (portNumber == null) {
            System.err.println("PORT NUMBER and HOST NAME must be specified!");
            System.exit(1);
        } else {
            running = true;
        }

        if (running) {
            try {
                clientSocket = new Socket(hostName, portNo);
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                objectOut = new ObjectOutputStream(clientSocket.getOutputStream());
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return clientSocket.isConnected();
    }
    
    public static void Terminate() throws IOException {
        String toServer = "TERMINATE";
        
        out.println(toServer);
        out.flush();
        clientSocket.close();
        System.exit(1);
    }
    
    public static void SendPositions(int[] Pos) throws IOException {
        InetAddress inet = InetAddress.getLocalHost();
        String IP = inet.getHostAddress();
        
        String toServer = "Positions for " + IP;
        System.out.println(toServer + " have been sent");
        out.println(toServer);
        objectOut.writeObject(Pos);
        out.flush();
        objectOut.flush();
    }
    
    public static int AttackPosition(int Pos) throws UnknownHostException, IOException {
        String inputLine;
        InetAddress inet = InetAddress.getLocalHost();
        String IP = inet.getHostAddress();
        
        String toServer = "Attack for " + IP + " = " + Pos;
        System.out.println("Attack co-ordinate " + Pos + " has been sent");
        out.println(toServer);
        
        if ((inputLine = in.readLine()) != null) {
            result = inputLine;
            String resultString = null;
            
            switch (result) {
                case "true":
                    resultInt = 1;
                    resultString = "true";
                    break;
                case "false":
                    resultInt = 2;
                    resultString = "false";
                    break;
                case "victory":
                    resultInt = 3;
                    resultString = "VICTORY";
                    break;
                case "notyourturn":
                    resultInt = 4;
                    resultString = "PATIENCE";
                    break;
            }
            
            System.out.println(resultString);
        }
        
        return resultInt;
    }

}
