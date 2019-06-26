package com.MP3;


import com.GUIFrame.GUIFrame;
import com.Interfaces.SongPanelsLinker;
import com.Panels.CenterPanelSections.AlbumPanel;
import com.Panels.CenterPanelSections.PlayListPanel;
import com.Panels.CenterPanelSections.SongPanel;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Scanner;

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
    public static void saveSongs() {
        SongPanelsLinker songPanelsLinker = GUIFrame.getSongPanelsLinker();
        try {
            String currentUser = GUIFrame.getUsername().replace(" ", "-");
            Formatter out = new Formatter("SavedDirectories/SavedSongsOf" + currentUser + ".txt");

            String albumName;
            String directory;
            for (SongPanel songPanel : songPanelsLinker.getAllSongPanels()) {
                albumName = songPanel.getMp3Info().getAlbum();
                directory = songPanel.getInputFileDirectory();
                out.format("Album^%s^%s%n", albumName, directory);
            }

            HashMap<String, PlayListPanel> playListPanels = songPanelsLinker.getPlayListPanels();
            String playlistName;
            String playlistDescription;
            for (PlayListPanel playListPanel : playListPanels.values()) {
                playlistName = playListPanel.getTitle();
                playlistDescription = playListPanel.getDescription();
                for (SongPanel songPanel : playListPanel.getPlayListSongs()) {
                    directory = songPanel.getInputFileDirectory();
                    out.format("Playlist^%s^%s^%s%n", playlistName, playlistDescription, directory);
                }
                if (playListPanel.getPlayListSongs().size() == 0)//if it's a empty playlist
                    out.format("Playlist^%s^%s%n", playlistName, playlistDescription);
            }
            out.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving songs", "An Error Occurred", JOptionPane.ERROR_MESSAGE);

        }
    }

    /**
     * this method is called at first running of program, after entering username, it searches for his/her directory files.
     * if exists, it will add  albums, then will add playlists.
     */
    public static void loadSongs() {
        String currentUser = GUIFrame.getUsername().replace(" ", "-");
        File file = new File("SavedDirectories/SavedSongsOf" + currentUser + ".txt");
        if (file.exists()) {
            try {
                FileReader fileReader = new FileReader(file.getAbsolutePath());
                Scanner scanner = new Scanner(fileReader);
                String line;
                ArrayList<MP3Info> currentMP3info;
                while (scanner.hasNext()) {
                    line = scanner.nextLine();
                    String[] parts = lineSeprator(line);
                    switch (parts[0]) {
                        case "Album":
                            currentMP3info = new ArrayList<>();
                            currentMP3info.add(new MP3Info(parts[2]));
                            GUIFrame.addAlbum(parts[1], currentMP3info);
                            break;
                        case "Playlist":
                            currentMP3info = new ArrayList<>();
                            if (parts.length == 4) {
                                currentMP3info.add(new MP3Info(parts[3]));
                                GUIFrame.addSongToPlayList(parts[1], parts[2], parts[3]);
                            } else {//if playlist is empty
                                GUIFrame.createPlayList(parts[1], parts[2]);
                            }
                            break;
                    }
                }
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Error loading songs", "An Error Occurred", JOptionPane.ERROR_MESSAGE);
            } catch (NoSuchFieldException | IOException e) {
                JOptionPane.showMessageDialog(null, "Error reading mp3 file", "An Error Occurred", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * This private static method, separate every line read by scanner to part of strings.
     *
     * @param line read line by scanner.
     * @return parts of line splitted by ^;
     */
    private static String[] lineSeprator(String line) {
        return line.split("[\\^]");
    }
}
