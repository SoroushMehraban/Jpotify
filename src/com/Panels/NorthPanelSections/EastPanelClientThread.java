package com.Panels.NorthPanelSections;

import javax.swing.*;
import java.util.ArrayList;

/**
 * This class handles the operations which are related to the client thread.
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
public class EastPanelClientThread extends Thread {
    private static ArrayList<UserThread> userThreads = new ArrayList<>();


    /**
     * this method send a request to given user to turn back all songs user has in shared songs.
     *
     * @param user user we want to get him/her his/her shared songs.
     */
    public void setShowSharedSongsReqest(String user) {
        for (UserThread userThread : userThreads)
            if (userThread.getConnectedUser().equals(user)) {
                userThread.setRequestMade(true);
                break;
            }
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
            UserThread userThread = new UserThread(hostNameField.getText());
            boolean canAdd = true;
            for (UserThread existingThread : userThreads) {
                if (existingThread.getIPv4().equals(userThread.getIPv4())) {
                    canAdd = false;
                    JOptionPane.showMessageDialog(null, "You are already connected!", "Warning!", JOptionPane.WARNING_MESSAGE);
                    break;
                }
            }
            if (canAdd) {
                userThreads.add(userThread);
                System.err.println();
                userThread.start();
            }
        }
    }

    public void setSongTitle(String songTitle) {
        for (UserThread userThread : userThreads)
            userThread.setSongTitle(songTitle);
    }

    public void setSongArtist(String songArtist) {
        for (UserThread userThread : userThreads)
            userThread.setSongArtist(songArtist);
    }
}
