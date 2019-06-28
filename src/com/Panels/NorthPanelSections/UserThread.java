package com.Panels.NorthPanelSections;

import com.GUIFrame.GUIFrame;
import com.Panels.CenterPanelSections.SharedSongPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class UserThread extends Thread {
    private OutputStream clientSocketOutputStream;
    private InputStream clientSocketInputStream;
    private PrintWriter clientSocketWriter;
    private Scanner clientSocketReader;

    private String songTitle;
    private String songArtist;
    private String previousTitleReceived;
    private String previousArtistReceived;
    private JPanel userInformationPanel;
    private JPanel connectedServerPanel;
    private JLabel connectedServerName;
    private JPanel serverInformationPanel;
    private JLabel state;
    private ArrayList<SharedSongPanel> sharedSongPanels;
    private boolean gettingSharedSongsList;
    private boolean requestMade;
    private int requestDownloadIndex;
    private String connectedUser;
    private String IPv4;


    UserThread(String IPv4) {
        this.IPv4 = IPv4;
    }

    String getConnectedUser() {
        return connectedUser;
    }

    void setRequestMade(boolean requestMade) {
        this.requestMade = requestMade;
    }

    @Override
    public void run() {
        try {
            Socket clientSocket = new Socket(IPv4, 2019);//making a connection to server
            createIO(clientSocket);//creating IO streams.

            System.out.println("I am Client!");

            connectedUser = clientSocketReader.nextLine();//reading server user name
            clientSocketWriter.println("user Received");//telling server that we received the name
            System.out.println("I connect to:" + connectedUser);

            System.out.println("sending username...");
            clientSocketWriter.println(GUIFrame.getUsername());//sending server our username
            System.out.println("sent");

            if (clientSocketReader.nextLine().equals("user Received")) {//if server received our user
                createConnectedServerPanel();

                while (true) {
                    System.out.println("First of loop");
                    if (gettingSharedSongsList) {//if we suppose to get shared Songs.
                        createAndShowSharedSongs();
                    } else {
                        if (songTitle == null && songArtist == null) {
                            clientSocketWriter.println("nothingPlayed");
                            clientSocketWriter.println("nothingPlayed");
                            System.out.println("Default sent");
                        } else {
                            clientSocketWriter.println(songArtist);
                            clientSocketWriter.println(songTitle);
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
                        if (requestMade) {//if we made a request from one user to get shared songs
                            System.out.println("true value found");
                            clientSocketWriter.println("get Shared Songs");//sending a request
                            requestMade = false;
                            gettingSharedSongsList = true;
                        }
                    }
                    GUIFrame.reload();
                    Thread.sleep(2000);
                }
            }
        } catch (InterruptedException e1) {
            System.err.println("socket ended");
            System.err.println("(client socket)CAN NOT CONNECT.THERE IS A PROBLEM. ");
            state.setIcon(GUIFrame.setIconSize("Icons/red.PNG", 10));
            GUIFrame.reload();
        } catch (IOException e1) {
            System.err.println("socket connecting error");
        }
    }

    private void createAndShowSharedSongs() {
        System.out.println("Trying to get shared songs...");
        sharedSongPanels = new ArrayList<>();
        gettingSharedSongsList = false;
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
            if (firstInput.equals("completed !"))
                break;
            System.out.println(firstInput);
            secondInput = clientSocketReader.nextLine();//expect to be song artist
            System.out.println(secondInput);
            clientSocketWriter.println("song received");
            SharedSongPanel newSharedSong = new SharedSongPanel(defaultImage, firstInput, secondInput, sharedSongPanels, connectedUser);
            sharedSongPanels.add(newSharedSong);
        }
        sharedSongPanels.remove(0);//it's invalid
        GUIFrame.showSharedSongs(sharedSongPanels);
    }

    /**
     * This methods adds a panel in east to show connected user, it's made of:
     * 1)serverInformationPanel: a panel to show information about user and indicate that user is online or not:
     * 2)Songs that user play for last time(it's not set in this method)
     */
    private void createConnectedServerPanel() {
        JPanel connectedServerPanel = new JPanel();//main panel
        connectedServerPanel.setLayout(new BoxLayout(connectedServerPanel, BoxLayout.PAGE_AXIS));
        connectedServerPanel.setBackground(new Color(23, 23, 23));

        serverInformationPanel = new JPanel();
        serverInformationPanel.setBackground(new Color(23, 23, 23));
        serverInformationPanel.setLayout(new BoxLayout(serverInformationPanel, BoxLayout.LINE_AXIS));

        System.out.println("getting connected username...");
        GUIFrame.addConnectedUserNameJCombobox(connectedUser);//setting connected user to show in JCombobox.
        System.out.println("Username connected setted");
        connectedServerName = new JLabel(" " + connectedUser);
        connectedServerName.setForeground(Color.WHITE);

        JLabel connectedServerIcon = new JLabel(GUIFrame.setIconSize("Icons/User.PNG", 20));
        serverInformationPanel.add(connectedServerIcon);
        serverInformationPanel.add(connectedServerName);

        serverInformationPanel.add(Box.createHorizontalStrut(5));
        state = new JLabel();
        state.setIcon(GUIFrame.setIconSize("Icons/green.PNG", 10));
        serverInformationPanel.add(state);

        connectedServerPanel.add(serverInformationPanel);
        GUIFrame.getEastPanel().addToNorth(connectedServerPanel);
        GUIFrame.reload();

    }

    private void createIO(Socket clientSocket) {
        try {
            clientSocketOutputStream = clientSocket.getOutputStream();
        } catch (IOException e) {
            System.err.println("error getting input stream");
        }
        try {
            clientSocketInputStream = clientSocket.getInputStream();
        } catch (IOException e) {
            System.err.println("error getting output stream");
        }
        clientSocketWriter = new PrintWriter(clientSocketOutputStream, true);
        clientSocketReader = new Scanner(clientSocketInputStream);
    }

    void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }
}
