package com.Panels.NorthPanelSections;

import com.GUIFrame.GUIFrame;
import com.Panels.CenterPanelSections.SharedSongPanel;
import com.Panels.GeneralPanels.WestPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class EastPanelClientThread extends Thread {
    private String songTitle;
    private String songArtist;
    private String previousTitleReceived;
    private String previousArtistReceived;
    private JLabel connectedServerName;
    private JPanel serverInformationPanel;
    private JLabel state;
    private HashMap<String, Boolean> showSharedSongs;
    private ArrayList<SharedSongPanel> sharedSongPanels;
    private boolean gettingSharedSongs;

    public EastPanelClientThread() {
        showSharedSongs = new HashMap<>();//this HashMap helps for each thread to send
    }

    public void setShowSharedSongs(String user) {
        System.out.println("setShowSharedSongs called");
        showSharedSongs.put(user, true);
    }

    @Override
    public void run() {
        JTextField hostNameField = new JTextField(10);
        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Host Name:"));
        myPanel.add(hostNameField);
        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please Enter a Host Name", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                //GUIFrame.getMainThread().interrupt();
                Socket clientSocket = new Socket(hostNameField.getText(), 2019);
                OutputStream clientSocketOutputStream = clientSocket.getOutputStream();
                InputStream clientSocketInputStream = clientSocket.getInputStream();
                PrintWriter clientSocketWriter = new PrintWriter(clientSocketOutputStream, true);
                Scanner clientSocketReader = new Scanner(clientSocketInputStream);

                System.out.println("I am Client!");

                String connectedUser = clientSocketReader.nextLine();
                clientSocketWriter.println("user Received");
                System.out.println("I connect to:" + connectedUser);

                System.out.println("sending username...");
                clientSocketWriter.println(GUIFrame.getUsername());
                System.out.println("sent");
                if (clientSocketReader.nextLine().equals("user Received")) {
                    JPanel connectedServerPanel = new JPanel();//main panel
                    connectedServerPanel.setLayout(new BoxLayout(connectedServerPanel, BoxLayout.PAGE_AXIS));
                    connectedServerPanel.setBackground(new Color(23, 23, 23));

                    serverInformationPanel = new JPanel();
                    serverInformationPanel.setLayout(new BoxLayout(serverInformationPanel, BoxLayout.LINE_AXIS));
                    serverInformationPanel.setBackground(new Color(23, 23, 23));

                    System.out.println("getting connected username...");
                    GUIFrame.addConnectedUserNameJCombobox(connectedUser);//setting connected user to show in JCombobox.
                    connectedServerName = new JLabel(" " + connectedUser);
                    System.out.println("Username connected setted");
                    connectedServerName.setForeground(Color.WHITE);

                    JLabel connectedServerIcon = new JLabel(WestPanel.setIconSize("Icons/User.PNG", 20));
                    serverInformationPanel.add(connectedServerIcon);
                    serverInformationPanel.add(connectedServerName);

                    serverInformationPanel.add(Box.createHorizontalStrut(5));
                    state = new JLabel();
                    state.setIcon(WestPanel.setIconSize("Icons/green.PNG", 10));
                    serverInformationPanel.add(state);

                    connectedServerPanel.add(serverInformationPanel);
                    GUIFrame.getEastPanel().addToNorth(connectedServerPanel);
                    GUIFrame.reload();
                    while (true) {
                        System.out.println("First of loop");

                        if (gettingSharedSongs) {//if we suppose to get shared Songs.
                            System.out.println("Trying to get shared songs...");
                            sharedSongPanels = new ArrayList<>();
                            gettingSharedSongs = false;
                            String firstInput;
                            String secondInput;
                            BufferedImage defaultImage = null;
                            try {
                                defaultImage = ImageIO.read(new File("RadioSongs/defaultImage.png"));
                            } catch (IOException e) {
                                JOptionPane.showMessageDialog(null, "Error reading shared default image", "An Error Occurred", JOptionPane.ERROR_MESSAGE);
                            }
                            System.out.println("image loaded");
                            while (true) {
                                firstInput = clientSocketReader.nextLine();//expect to be song title
                                if(firstInput.equals("completed !"))
                                    break;
                                System.out.println(firstInput);
                                secondInput = clientSocketReader.nextLine();//expect to be song artist
                                System.out.println(secondInput);
                                clientSocketWriter.println("song received");
                                SharedSongPanel newSharedSong = new SharedSongPanel(defaultImage, firstInput, secondInput, sharedSongPanels);
                                sharedSongPanels.add(newSharedSong);
                            }
                            sharedSongPanels.remove(0);//it's invalid
                            GUIFrame.showSharedSongs(sharedSongPanels);
                        } else {
                            if (songTitle == null && songArtist == null) {
                                clientSocketWriter.println("nothingPlayed");
                                clientSocketWriter.println("nothingPlayed");
                                clientSocketWriter.flush();
                                System.out.println("Default sent");
                            } else {
                                clientSocketWriter.println(songArtist);
                                clientSocketWriter.println(songTitle);
                                clientSocketWriter.flush();
                            }
                            String serverSongName = clientSocketReader.nextLine();
                            System.out.println("First input received:" + serverSongName);
                            String serverSongArtist = clientSocketReader.nextLine();
                            JLabel titleLabel = new JLabel(serverSongName);
                            JLabel artistLabel = new JLabel(serverSongArtist);

                            if (!serverSongName.equals("nothingPlayed") && !serverSongArtist.equals("nothingPlayed")) {
                                if (previousArtistReceived == null || (!previousTitleReceived.equals(serverSongName) && !previousArtistReceived.equals(serverSongArtist))) {
                                    previousArtistReceived = serverSongArtist;
                                    previousTitleReceived = serverSongName;

                                    connectedServerPanel.removeAll();
                                    connectedServerPanel.add(serverInformationPanel);
                                    connectedServerPanel.add(Box.createVerticalStrut(10));
                                    connectedServerPanel.add(titleLabel);
                                    connectedServerPanel.add(Box.createVerticalStrut(10));
                                    connectedServerPanel.add(artistLabel);
                                }
                            }
                            if (showSharedSongs.values().contains(true)) {//if we made a request from one user to get shared songs
                                System.out.println("true value found");
                                for (String user : showSharedSongs.keySet())//finding that user
                                    if (showSharedSongs.get(user)) {//after finding:
                                        clientSocketWriter.println("get Shared Songs");//sending a request
                                        showSharedSongs.put(user, false);//finishing that request
                                        gettingSharedSongs = true;
                                        break;//finish searching
                                    }
                            }
                        }
                        GUIFrame.reload();
                        Thread.sleep(2000);
                    }
                }
            } catch (Exception e) {
                //this.interrupt();
                System.err.println("(client socket)CAN NOT CONNECT.THERE IS A PROBLEM. ");
                state.setIcon(WestPanel.setIconSize("Icons/red.PNG", 10));
                GUIFrame.reload();
            }
        }
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }
}
