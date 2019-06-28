package com.Panels.NorthPanelSections;

import com.GUIFrame.GUIFrame;
import com.Panels.CenterPanelSections.SharedSongPanel;
import com.Panels.CenterPanelSections.SongPanel;
import com.Panels.GeneralPanels.WestPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerThread extends Thread {
    private Socket connectedSocket;
    private OutputStream serverSocketOutputStream;
    private InputStream serverSocketInputStream;
    private PrintWriter serverSocketWriter;
    private Scanner serverSocketReader;

    private String songTitle;
    private String songArtist;
    private String previousTitleReceived;
    private String previousArtistReceived;
    private JPanel userInformationPanel;
    private JPanel connectedClientPanel;
    private JLabel connectedClientName;
    private JLabel state;
    private ArrayList<SharedSongPanel> sharedSongPanels;
    private boolean gettingSharedSongsList;
    private boolean requestMade;
    private int requestDownloadIndex;
    private String connectedUser;

    ServerThread(){
        try {
            ServerSocket mainSocket = new ServerSocket(2019);
            connectedSocket = mainSocket.accept();

        } catch (IOException e) {
            System.err.println("error connecting to server");
        }
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
            System.out.println("I am Server!,someone connected.");
            createIO(connectedSocket);//creating IO streams.

            System.out.println("sending username..");
            serverSocketWriter.println(GUIFrame.getUsername());
            System.out.println("sent");

            System.out.println("trying to receive...");
            if (serverSocketReader.nextLine().equals("user Received")) {
                System.out.println("client received my messaage");

                createConnectedClientPanel();

                while (true) {
                    System.out.println("start of loop");
                    if (gettingSharedSongsList) {//if we suppose to get shared Songs.
                        createAndShowSharedSongs();
                    } else {
                        if (songTitle != null && songArtist != null) {
                            serverSocketWriter.println(songTitle);
                            serverSocketWriter.println(songArtist);
                            serverSocketWriter.flush();
                        } else {
                            serverSocketWriter.println("nothingPlayed");
                            serverSocketWriter.println("nothingPlayed");
                            serverSocketWriter.flush();
                            System.out.println("Default sent");
                        }
                        String firstInputSocket = serverSocketReader.nextLine();//expected to be song title
                        System.out.println("First input received:" + firstInputSocket);

                        if (firstInputSocket.equals("get Shared Songs")) {
                            for (SongPanel sharedSong : GUIFrame.getSharedSongs()) {
                                System.out.println("Sending a shared songs.......");
                                serverSocketWriter.println(sharedSong.getMp3Info().getTitle());
                                serverSocketWriter.println(sharedSong.getMp3Info().getArtist());
                                if (!serverSocketReader.nextLine().equals("song received"))
                                    System.err.println("an error occurred");

                            }
                            serverSocketWriter.println("completed !");
                            System.out.println("Done sending shared songs!");
                        } else {
                            String secondInputSocket = serverSocketReader.nextLine();//expected to be song artist
                            System.out.println("First input received:" + secondInputSocket);

                            JLabel titleLabel = new JLabel(firstInputSocket);
                            JLabel artistLabel = new JLabel(secondInputSocket);
                            if (!firstInputSocket.equals("nothingPlayed") && !secondInputSocket.equals("nothingPlayed")) {
                                if (previousArtistReceived == null || (!previousTitleReceived.equals(firstInputSocket) && !previousArtistReceived.equals(secondInputSocket))) {
                                    previousArtistReceived = secondInputSocket;
                                    previousTitleReceived = firstInputSocket;

                                    connectedClientPanel.removeAll();
                                    connectedClientPanel.add(userInformationPanel);
                                    // connectedUserPanel.add(Box.createVerticalStrut(10));
                                    connectedClientPanel.add(titleLabel);
                                    //connectedUserPanel.add(Box.createVerticalStrut(10));
                                    connectedClientPanel.add(artistLabel);
                                }
                            }
                            if (requestMade) {//if we made a request from one user to get shared songs
                                System.out.println("a request made");
                                serverSocketWriter.println("get Shared Songs");//sending a request
                                requestMade = false;
                                gettingSharedSongsList = true;
                            }
                        }
                    }
                    GUIFrame.reload();
                    Thread.sleep(2000);
                }

            }
        }catch (InterruptedException e1) {
            System.err.println("socket ended");
            System.err.println("(client socket)CAN NOT CONNECT.THERE IS A PROBLEM. ");
            state.setIcon(GUIFrame.setIconSize("Icons/red.PNG", 10));
            GUIFrame.reload();
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
            firstInput = serverSocketReader.nextLine();//expect to be song title
            if (firstInput.equals("completed !"))
                break;
            System.out.println(firstInput);
            secondInput = serverSocketReader.nextLine();//expect to be song artist
            System.out.println(secondInput);
            serverSocketWriter.println("song received");
            SharedSongPanel newSharedSong = new SharedSongPanel(defaultImage, firstInput, secondInput, sharedSongPanels, connectedUser);
            sharedSongPanels.add(newSharedSong);
        }
        sharedSongPanels.remove(0);//it's invalid
        GUIFrame.showSharedSongs(sharedSongPanels);
    }

    /**
     * This methods adds a panel in east to show connected user, it's made of:
     * 1)userInformationPanel: a panel to show information about user and indicate that user is online or not:
     * 2)Songs that user play for last time(it's not set in this method)
     */
    private void createConnectedClientPanel() {
        connectedClientPanel = new JPanel();//main panel
        connectedClientPanel.setLayout(new BoxLayout(connectedClientPanel, BoxLayout.PAGE_AXIS));
        connectedClientPanel.setBackground(new Color(23, 23, 23));

        userInformationPanel = new JPanel();
        userInformationPanel.setBackground(new Color(23, 23, 23));
        userInformationPanel.setLayout(new BoxLayout(userInformationPanel, BoxLayout.LINE_AXIS));

        System.out.println("getting connected username...");
        connectedUser = serverSocketReader.nextLine();
        System.out.println("connected:" + connectedUser);
        serverSocketWriter.println("user Received");
        GUIFrame.addConnectedUserNameJCombobox(connectedUser);//setting connected user to show in JCombobox.

        connectedClientName = new JLabel(" " + connectedUser);
        connectedClientName.setForeground(Color.WHITE);

        JLabel connectedClientIcon = new JLabel(GUIFrame.setIconSize("Icons/User.PNG", 20));
        userInformationPanel.add(connectedClientIcon);
        userInformationPanel.add(connectedClientName);

        userInformationPanel.add(Box.createHorizontalStrut(5));
        state = new JLabel();
        state.setIcon(GUIFrame.setIconSize("Icons/green.PNG", 10));
        userInformationPanel.add(state);

        connectedClientPanel.add(userInformationPanel);
        GUIFrame.getEastPanel().addToNorth(connectedClientPanel);
        GUIFrame.reload();

    }

    private void createIO(Socket clientSocket) {
        try {
            serverSocketOutputStream = clientSocket.getOutputStream();
        } catch (IOException e) {
            System.err.println("error getting input stream");
        }
        try {
            serverSocketInputStream = clientSocket.getInputStream();
        } catch (IOException e) {
            System.err.println("error getting output stream");
        }
        serverSocketWriter = new PrintWriter(serverSocketOutputStream, true);
        serverSocketReader = new Scanner(serverSocketInputStream);
    }

    void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }
}
