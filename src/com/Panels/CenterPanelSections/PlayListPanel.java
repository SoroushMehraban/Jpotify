package com.Panels.CenterPanelSections;

import com.GUIFrame.GUIFrame;
import com.Interfaces.ShowSongsLinker;
import com.Interfaces.SongPanelsLinker;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Album panel which contains list of music which is chosen by user.
 *
 * @author Soroush Mehraban
 * @version 1.0
 */
public class PlayListPanel extends MusicPanel {
    private ArrayList<SongPanel> playListSongs;
    private ShowSongsLinker showSongsLinker;
    private SongPanelsLinker songPanelsLinker;
    private String title;
    private String description;

    /**
     * Constructor which set information need to show in super class.
     *
     * @param image       image of panel to show at first in above.
     * @param title       title to show under the image.
     * @param description description to show under the title.
     */
    PlayListPanel(BufferedImage image, String title, String description, ShowSongsLinker showSongsLinker, SongPanelsLinker songPanelsLinker) {
        super(image, title, description);
        this.title = title;
        this.description = description;
        playListSongs = new ArrayList<>();
        this.showSongsLinker = showSongsLinker;
        this.songPanelsLinker = songPanelsLinker;
        createPlayListListener();
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    private void createPlayListListener() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                PlayListPanel source = (PlayListPanel) e.getSource();
                source.setBackground(new Color(23, 23, 23));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    showSongsLinker.showPlayListSongs(title);
                    PlayListPanel source = (PlayListPanel) e.getSource();
                    source.setBackground(new Color(23, 23, 23));
                } else if (SwingUtilities.isRightMouseButton(e)) {//if user press right click, he wants to remove playlist
                    if (!title.equals("Favorite Songs") && !title.equals("Shared Songs")) {//if chosen playlist created by user
                        int result = JOptionPane.showConfirmDialog(null, "Do you want to remove this playlist?",
                                "Removing playlist..", JOptionPane.OK_CANCEL_OPTION);
                        if (result == JOptionPane.OK_OPTION) {
                            songPanelsLinker.getPlayListPanels().remove(title);//removing this from our playlist panel.
                            GUIFrame.showHome();
                        }
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                PlayListPanel source = (PlayListPanel) e.getSource();
                source.setBackground(new Color(41, 41, 41));
            }
        });
    }

    void addSong(SongPanel songPanel) {
        playListSongs.add(songPanel);
    }

    void removeSong(String title) {
        List<SongPanel> tempSynchronized = Collections.synchronizedList(playListSongs);
        Iterator<SongPanel> it = tempSynchronized.iterator();
        while (it.hasNext()) {
            SongPanel songPanel = it.next();
            if (songPanel.getMp3Info().getTitle().equals(title))
                it.remove();
        }
        playListSongs = new ArrayList<>(tempSynchronized);
    }

    public ArrayList<SongPanel> getPlayListSongs() {
        return playListSongs;
    }

    /**
     * this method update playlist panel every time it called after adding new song(s)
     */
    void updateImage() {
        if (!title.equals("Favorite Songs") && !title.equals("Shared Songs")) {
            SongPanel lastSongAdded = playListSongs.get(0);//getting last song artwork added to playlist
            try {
                Image updatedImage = lastSongAdded.getMp3Info().getImage().getScaledInstance(getImageScale(), getImageScale(), Image.SCALE_SMOOTH);
                getImageLabel().setIcon(new ImageIcon(updatedImage));
            } catch (InvalidDataException | IOException | UnsupportedTagException e) {
                JOptionPane.showMessageDialog(null, "Error reading new playlist song artwork", "An Error Occurred", JOptionPane.ERROR_MESSAGE);
            }
        }

    }
}
