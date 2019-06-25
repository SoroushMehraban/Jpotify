package com.MP3;


import com.GUIFrame.GUIFrame;
import com.Panels.CenterPanelSections.AlbumPanel;
import com.Panels.CenterPanelSections.PlayListPanel;
import com.Panels.CenterPanelSections.SongPanel;

import javax.swing.*;
import java.io.IOException;
import java.util.Formatter;
import java.util.HashMap;

/**
 * This class has two static method to save and load songs which user added to library.
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
public class AppStorage {
    /**
     * This method save current songs existing in library when user close jpotify.
     * for doing so, it creates a txt file to SavedDirectories folder(folder should exist)
     * then at first it saves all album directories
     * at the end it saves all songs existing in playlists.
     */
    public static void saveSongs(){
        try {
           String currentUser = GUIFrame.getUsername().replace(" ","-");
           Formatter out = new Formatter("SavedDirectories/SavedSongsOf"+currentUser+".txt");

            HashMap<String, AlbumPanel> albumPanels = GUIFrame.getAlbumPanels();
            String albumName;
            String directory;
            for(AlbumPanel albumPanel : albumPanels.values())
                for (SongPanel songPanel : albumPanel.getSongPanels()) {
                    albumName = songPanel.getMp3Info().getAlbum();
                    directory = songPanel.getInputFileDirectory();
                    out.format("Album-%s:%s%n",albumName,directory);
                }

            HashMap<String,PlayListPanel> playListPanels = GUIFrame.getPlayListPanels();
            String playlistName;
            for(PlayListPanel playListPanel: playListPanels.values()) {
                playlistName = playListPanel.getTitle();
                for (SongPanel songPanel : playListPanel.getPlayListSongs()) {
                    directory = songPanel.getInputFileDirectory();
                    out.format("Playlist-%s:%s%n",playlistName,directory);
                }
            }
            out.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving songs","An Error Occurred",JOptionPane.ERROR_MESSAGE);

        }
    }
}
