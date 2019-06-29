package com.Panels.NorthPanelSections;

import com.GUIFrame.GUIFrame;
import com.Panels.CenterPanelSections.SharedSongPanel;
import com.Panels.CenterPanelSections.SongPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * in our program user is server and client in same time, as a server for each client which entered our hostname,
 * we create a ServerThread to handle operations needed to handle with connected user, this class does that.
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
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
    private ArrayList<ConnectedUserPanel> connectedUserPanels;
    private boolean gettingSharedSongsList;
    private boolean gettingSharedSong;
    private boolean requestMade;
    private int requestDownloadIndex;
    private String connectedUser;

    /**
     * Class constructor
     * initializes some fields and accept socket request.
     */
    ServerThread() {
        connectedUserPanels = new ArrayList<>();
        requestDownloadIndex = -1;//default invalid index;
        try {
            ServerSocket mainSocket = new ServerSocket(2019);
            connectedSocket = mainSocket.accept();

        } catch (IOException ignored) { }
    }

    String getConnectedUser() {
        return connectedUser;
    }

    void setRequestDownloadIndex(int requestDownloadIndex) {
        this.requestDownloadIndex = requestDownloadIndex;
    }

    /**
     * when this method is called, we set a request to connected user to get shared songs of him/her
     * @param requestMade given request made.
     */
    void setRequestMade(boolean requestMade) {
        this.requestMade = requestMade;
    }

    @Override
    public void run() {
        try {
          //  System.out.println("I am Server!,someone connected.");
            createIO(connectedSocket);//creating IO streams.

          //  System.out.println("sending username..");
            serverSocketWriter.println(GUIFrame.getUsername());
          //  System.out.println("sent");

          //  System.out.println("trying to receive...");
            if (serverSocketReader.nextLine().equals("user Received")) {//if user connected to us received our user name.
             //   System.out.println("client received my messaage");

              //  System.out.println("getting connected username...");
                connectedUser = serverSocketReader.nextLine();//receiving username of user connected to us.
              //  System.out.println("connected:" + connectedUser);
                serverSocketWriter.println("user Received");//telling that user we received his user name.
                GUIFrame.addConnectedUserNameJCombobox(connectedUser);//setting connected user to show in JCombobox.

                while (true) {//doing updates in always running loop
                //    System.out.println("start of loop");
                    if (gettingSharedSongsList) {//if we suppose to get shared Songs.
                        createAndShowSharedSongs();
                    }
                    else if(gettingSharedSong){//if we suppose to download(not works)
                        downloadNewSharedSong();
                    }
                    else {
                        if (songTitle != null && songArtist != null) {//if we played a song in shared songs, we sending it
                            serverSocketWriter.println(songTitle);
                            serverSocketWriter.println(songArtist);
                            serverSocketWriter.flush();
                        } else {//if we didn't play anything yet, we send nothing played
                            serverSocketWriter.println("nothingPlayed");
                            serverSocketWriter.println("nothingPlayed");
                            serverSocketWriter.flush();
                        }
                        String firstInputSocket = serverSocketReader.nextLine();//expected to be song title
                        //System.out.println("First input received:" + firstInputSocket);

                        if (firstInputSocket.equals("get Shared Songs")) {//if user request us to send him our shared song list
                            for (SongPanel sharedSong : GUIFrame.getSharedSongs()) {//sending each song info in shared songs
                                //System.out.println("Sending a shared songs.......");
                                serverSocketWriter.println(sharedSong.getMp3Info().getTitle());
                                serverSocketWriter.println(sharedSong.getMp3Info().getArtist());
                                if (!serverSocketReader.nextLine().equals("song received"))//if user didn't get our song info
                                    JOptionPane.showMessageDialog(null, "Error sending shared songs", "An Error Occurred", JOptionPane.ERROR_MESSAGE);

                            }
                            serverSocketWriter.println("completed !");//telling user our list finished
                            //System.out.println("Done sending shared songs!");
                        } else {
                            String secondInputSocket = serverSocketReader.nextLine();//expected to be song artist
                           // System.out.println("First input received:" + secondInputSocket);

                            if (!firstInputSocket.equals("nothingPlayed") && !firstInputSocket.equals("song received")) {//if we received a song info
                                if (previousArtistReceived == null || (!previousTitleReceived.equals(firstInputSocket) && !previousArtistReceived.equals(secondInputSocket))) {
                                    previousArtistReceived = secondInputSocket;
                                    previousTitleReceived = firstInputSocket;

                                    ConnectedUserPanel connectedUserPanel = new ConnectedUserPanel(connectedUser, firstInputSocket, secondInputSocket);
                                    if (connectedUserPanels.size() > 0) {
                                        ConnectedUserPanel lastConnectedUserPanel = connectedUserPanels.get(connectedUserPanels.size() - 1);
                                        lastConnectedUserPanel.setStopped();//stop last one (changing playing label)
                                    }
                                    connectedUserPanels.add(connectedUserPanel);
                                    GUIFrame.getEastPanel().addToNorth(connectedUserPanel);
                                }
                            }
                            if (requestMade) {//if we made a request from one user to get shared songs (in JCombobox)
                               // System.out.println("a request made");
                                serverSocketWriter.println("get Shared Songs");//sending a request
                                gettingSharedSongsList = true;
                                requestMade = false;
                            }
                            if(requestDownloadIndex != -1){//if we clicked on user songs in shared songs list and request to download
                               // System.out.println("found a index to send mp3 data:"+requestDownloadIndex);
                                String sendingNote = "index "+requestDownloadIndex+" needed";
                               // System.out.println("Sending note: "+sendingNote);
                                serverSocketWriter.println(sendingNote);
                                serverSocketWriter.println(sendingNote);
                                while(!serverSocketReader.nextLine().equals("Index Received")){//wait until client receive index
                                }
                                requestDownloadIndex = -1;//changing to default for next time, until it is set by our user.
                                gettingSharedSong = true;//for next true while, trying to receive shared song.
                            }
                        }
                    }
                    GUIFrame.reload();//reloading lists
                    Thread.sleep(2000);//waiting 2 sec for next updates
                }

            }
        } catch (InterruptedException | NoSuchElementException e1) {//if user become offline
            for (ConnectedUserPanel connectedUserPanel : connectedUserPanels) {//setting all panel related to chosen socket off
                connectedUserPanel.setOffline();
            }
            GUIFrame.reload();
        } catch (NullPointerException ignored) {
        }
    }

    private void downloadNewSharedSong() {
        gettingSharedSong = false;//changing to default for next time, until it is  set by our user.
        try {

            FileOutputStream fos = new FileOutputStream("SharedSongs/test.mp3");
           //
            // System.out.println("Downloading...");
            int count;
            while ((count = serverSocketInputStream.read()) != -1) {
                fos.write(count);
               // System.out.println(count);
            }
            fos.close();
           // System.out.println("downloaded!");
            serverSocketWriter.println("downloaded");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error creating output file", "An Error Occurred", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
          //  System.out.println("error writing output file");
        }
    }

    /**
     * this method is called when we want to see connected user shared songs.
     */
    private void createAndShowSharedSongs() {
       // System.out.println("Trying to get shared songs...");
        ArrayList<SharedSongPanel> sharedSongPanels = new ArrayList<>();
        gettingSharedSongsList = false;
        String firstInput;
        String secondInput;
        BufferedImage defaultImage = null;
        try {
            defaultImage = ImageIO.read(new File("SharedSongs/defaultImage.png"));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading shared default image", "An Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
       // System.out.println("image loaded");
        while (true) {
            firstInput = serverSocketReader.nextLine();//expect to be song title
            if (firstInput.equals("completed !"))
                break;
         //   System.out.println(firstInput);
            secondInput = serverSocketReader.nextLine();//expect to be song artist
           // System.out.println(secondInput);
            serverSocketWriter.println("song received");
            SharedSongPanel newSharedSong = new SharedSongPanel(defaultImage, firstInput, secondInput, sharedSongPanels, connectedUser);
            sharedSongPanels.add(newSharedSong);
        }
        sharedSongPanels.remove(0);//it's invalid
        GUIFrame.showSharedSongs(sharedSongPanels);
    }

    /**
     * This method creates input output streams after connection made
     * @param clientSocket socket of our connection
     */
    private void createIO(Socket clientSocket) {
        try {
            serverSocketOutputStream = clientSocket.getOutputStream();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error getting input stream", "An Error Occurred", JOptionPane.ERROR_MESSAGE);
        } catch (NullPointerException ignored) {
        }
        try {
            serverSocketInputStream = clientSocket.getInputStream();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error getting output stream", "An Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
        serverSocketWriter = new PrintWriter(serverSocketOutputStream, true);
        serverSocketReader = new Scanner(serverSocketInputStream);
    }

    /**
     * when we played a new song in shared songs, this method called and set song title to show to our connected user
     * @param songTitle title of song to send
     */
    void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    /**
     * when we played a new song in shared songs, this method called and set song artist to show to our connected user
     * @param songArtist artist of song to send
     */
    void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }
}
