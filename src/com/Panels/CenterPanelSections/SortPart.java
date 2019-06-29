package com.Panels.CenterPanelSections;


import com.Interfaces.SortLinker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * SortPart is a part of center panel.
 * When user opens a playlist, if that playlist consist 2 or more songs, sort box of this part appears
 * in the others parts, removeSortBox method called and no sorting box will show at top of center part.
 *
 * @author Soroush Mehraban & Morteza damghani
 * @version 1.0
 */
public class SortPart extends JPanel {
    private JComboBox<String> sortBox;
    private SortLinker sortLinker;

    /**
     * Class constructor, setting layout and background and creating a sort box.
     */
    public SortPart() {
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(23, 23, 23));//setting panel background

        sortBox = new JComboBox<>();//creating a JComboBox for user.
        sortBox.addItem("Artist");
        sortBox.addItem("Album");
        sortBox.addItem("Song Title");
        createSortBoxListener();
        sortBox.setPreferredSize(new Dimension(100, 25));//setting PreferredSize to show.
    }

    public void setSortLinker(SortLinker sortLinker) {
        this.sortLinker = sortLinker;
    }

    /**
     * when this method is called, it shows sort box at left of this panel.
     */
    void showSortBox() {
        if (this.getComponentCount() == 0)
            this.add(sortBox, BorderLayout.WEST);
    }

    /**
     * when this method is called, it removes sort box and nothing will show.
     */
    void removeSortBox() {
        this.removeAll();
    }

    /**
     * this method created a listener for sort box and indicate what happens if user chose an item
     */
    private void createSortBoxListener() {
        sortBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox source = (JComboBox) e.getSource();
                String selectedItem = (String) source.getSelectedItem();
                if (selectedItem != null) {

                    if (selectedItem.equals("Artist"))
                        sortByArtist(sortLinker.getCurrentPlaylistPanel());
                    if (selectedItem.equals("Album"))
                        sortByAlbum(sortLinker.getCurrentPlaylistPanel());
                    if (selectedItem.equals("Song Title"))
                        sortByeSongName(sortLinker.getCurrentPlaylistPanel());

                    sortLinker.showPlayListSongs(sortLinker.getCurrentPlaylistPanel().getTitle());//updating page
                }
            }
        });
    }

    /**
     * this method sorts all of songs in playlist based on artist names.
     */
    private void sortByArtist(PlayListPanel currentPlaylistPanel) {
        currentPlaylistPanel.getPlayListSongs().sort(new Comparator<>() {
            @Override
            public int compare(SongPanel o1, SongPanel o2) {
                String o1Artist = o1.getMp3Info().getArtist();
                String o2Artist = o2.getMp3Info().getArtist();
                return o1Artist.compareToIgnoreCase(o2Artist);
            }
        });
    }

    /**
     * this method sorts all of songs in playlist based on album names.
     */
    private void sortByAlbum(PlayListPanel currentPlaylistPanel) {
        currentPlaylistPanel.getPlayListSongs().sort(new Comparator<>() {
            @Override
            public int compare(SongPanel o1, SongPanel o2) {
                String o1Album = o1.getMp3Info().getAlbum();
                String o2Album = o2.getMp3Info().getAlbum();
                return o1Album.compareToIgnoreCase(o2Album);
            }
        });
    }

    /**
     * this method sorts all of songs in playlist based on song names.
     */
    private void sortByeSongName(PlayListPanel currentPlaylistPanel) {
        currentPlaylistPanel.getPlayListSongs().sort(new Comparator<>() {
            @Override
            public int compare(SongPanel o1, SongPanel o2) {
                String o1SongName = o1.getMp3Info().getTitle();
                String o2SongName = o2.getMp3Info().getTitle();
                return o1SongName.compareToIgnoreCase(o2SongName);
            }
        });
    }
}
