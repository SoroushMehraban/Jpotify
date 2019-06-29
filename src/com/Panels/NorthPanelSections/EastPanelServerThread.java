package com.Panels.NorthPanelSections;

import java.util.ArrayList;


/**
 * This class handles the operations which are related to the server thread.
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
public class EastPanelServerThread extends Thread {
    private ArrayList<ServerThread> serverThreads;
    private int requestDownloadIndex;

    public EastPanelServerThread() {
        serverThreads = new ArrayList<>();
    }

    /**
     * this method send a request to given user to turn back all songs user has in shared songs.
     *
     * @param user user we want to get him/her his/her shared songs.
     */
    public void setShowSharedSongsRequest(String user) {
        for (ServerThread serverThread : serverThreads)
            if (serverThread.getConnectedUser().equals(user)) {
                serverThread.setRequestMade(true);
                break;
            }
    }

    /**
     * This method set a request download index to user we want to download from
     *
     * @param requestDownloadIndex index of song in shared song we want to download
     * @param connectedUser        user we want to get Him song
     */
    public void setRequestDownloadIndex(int requestDownloadIndex, String connectedUser) {
        for (ServerThread serverThread : serverThreads)
            if (serverThread.getConnectedUser().equals(connectedUser)) {
                serverThread.setRequestDownloadIndex(requestDownloadIndex);
                break;
            }
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Trying to connect..");
            ServerThread serverThread = new ServerThread();
            System.out.println("Connected!");
            serverThread.start();
            serverThreads.add(serverThread);
        }
    }

    public void setSongTitle(String songTitle) {
        for (ServerThread serverThread : serverThreads)
            serverThread.setSongTitle(songTitle);
    }

    public void setSongArtist(String songArtist) {
        for (ServerThread serverThread : serverThreads)
            serverThread.setSongArtist(songArtist);
    }
}
