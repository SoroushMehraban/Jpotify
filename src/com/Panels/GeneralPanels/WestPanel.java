
package com.Panels.GeneralPanels;

import com.GUIFrame.GUIFrame;
import com.Panels.WestPanelSections.WestPanelListeners.*;

import javax.swing.*;
import java.awt.*;

/**
 * This Class is about west panel of our program,It has features:
 * HOME: it shows list of albums and playlist in center panel.
 * Library: it opens a file chooser to select desired mp3 files.
 * Songs: it shows all songs based on last playing.
 * Albums: it opens list of albums and user can open them.
 * Playlist: it opens list of playlist and user can open them.
 */
public class WestPanel extends JPanel {

    private static WestPanel westPanel;
    private static JPanel albumsPanel;
    private static JPanel homePanel;
    private static JPanel songsPanel;
    private static JPanel libraryPanel;
    private static JPanel playListsPanel;

    public WestPanel() {
        westPanel = this;
        JLabel homeLabel;
        JLabel homeIcon;
        JLabel libraryLabel;
        JLabel libraryIcon;
        JLabel addToLibraryIcon;
        JLabel songsLabel;
        JLabel songsIcon;
        JLabel albumsLabel;
        JLabel albumsIcon;
        JLabel playListsLabel;
        JLabel playListsIcon;
        JLabel addToPlaylistsIcon;

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setBackground(new Color(23, 23, 23));
        //designing home panel:
        homePanel = new JPanel();
        homePanel.setBackground(new Color(23, 23, 23));
        homePanel.setLayout(new BoxLayout(homePanel, BoxLayout.LINE_AXIS));
        homeIcon = new JLabel();
        homeIcon.setIcon(setIconSize("Icons/Home-no-select.png", 20));
        homeLabel = new JLabel(" HOME");
        homeIcon.addMouseListener(new HomePanelListener(homeIcon, homeLabel));
        homeLabel.addMouseListener(new HomePanelListener(homeIcon, homeLabel));
        homeLabel.setForeground(Color.WHITE);
        homePanel.add(homeIcon);
        homePanel.add(homeLabel);
        this.add(Box.createVerticalStrut(20));
        this.add(homePanel);
        this.add(Box.createVerticalStrut(50));
        //designing library panel:
        libraryPanel = new JPanel();
        libraryPanel.setBackground(new Color(23, 23, 23));
        libraryPanel.setLayout(new BoxLayout(libraryPanel, BoxLayout.LINE_AXIS));
        libraryLabel = new JLabel(" Library  ");
        libraryLabel.setForeground(Color.WHITE);
        libraryIcon = new JLabel();
        libraryIcon.setIcon(setIconSize("Icons/Library-no-select.PNG", 20));
        addToLibraryIcon = new JLabel();
        addToLibraryIcon.setIcon(setIconSize("Icons/Plus-no-select.PNG", 10));
        libraryLabel.addMouseListener(new LibraryPanelListener(libraryIcon, libraryLabel, addToLibraryIcon));
        libraryIcon.addMouseListener(new LibraryPanelListener(libraryIcon, libraryLabel, addToLibraryIcon));
        addToLibraryIcon.addMouseListener(new LibraryPanelListener(libraryIcon, libraryLabel, addToLibraryIcon));
        libraryPanel.add(libraryIcon);
        libraryPanel.add(libraryLabel);
        libraryPanel.add(addToLibraryIcon);
        this.add(libraryPanel);
        this.add(Box.createVerticalStrut(30));
        //designing song panel:
        songsPanel = new JPanel();
        songsPanel.setBackground(new Color(23, 23, 23));
        songsPanel.setLayout(new BoxLayout(songsPanel, BoxLayout.LINE_AXIS));
        songsIcon = new JLabel();
        songsIcon.setIcon(setIconSize("Icons/Song-no-selected.png", 20));
        songsPanel.add(songsIcon);
        songsLabel = new JLabel(" Songs");
        songsIcon.addMouseListener(new SongsPanelListener(songsIcon, songsLabel));
        songsLabel.addMouseListener(new SongsPanelListener(songsIcon, songsLabel));
        songsLabel.setForeground(Color.WHITE);
        songsPanel.add(songsLabel);
        this.add(songsPanel);
        this.add(Box.createVerticalStrut(30));
        //designing albums panel:
        albumsPanel = new JPanel();
        albumsPanel.setBackground(new Color(23, 23, 23));
        albumsPanel.setLayout(new BoxLayout(albumsPanel, BoxLayout.LINE_AXIS));
        albumsIcon = new JLabel();
        albumsIcon.setIcon(setIconSize("Icons/Album-no-selected.png", 20));
        albumsPanel.add(albumsIcon);
        albumsLabel = new JLabel(" Albums");
        AlbumsPanelListener AlbumsTempListener = new AlbumsPanelListener(albumsIcon, albumsLabel);
        albumsIcon.addMouseListener(AlbumsTempListener);
        albumsLabel.addMouseListener(AlbumsTempListener);
        albumsLabel.setForeground(Color.WHITE);
        albumsPanel.add(albumsLabel);
        this.add(albumsPanel);
        this.add(Box.createVerticalStrut(30));
        //designing playlist panel:
        playListsPanel = new JPanel();
        playListsPanel.setBackground(new Color(23, 23, 23));
        playListsPanel.setLayout(new BoxLayout(playListsPanel, BoxLayout.LINE_AXIS));
        playListsIcon = new JLabel();
        playListsIcon.setIcon(setIconSize("Icons/p-no-selected.png", 20));
        playListsPanel.add(playListsIcon);
        playListsLabel = new JLabel(" Playlists  ");
        playListsLabel.setForeground(Color.WHITE);
        playListsPanel.add(playListsLabel);
        PlaylistsListener playlistsTempListener = new PlaylistsListener(playListsIcon, playListsLabel);
        playListsIcon.addMouseListener(playlistsTempListener);
        playListsLabel.addMouseListener(playlistsTempListener);
        addToPlaylistsIcon = new JLabel();
        addToPlaylistsIcon.setIcon(setIconSize("Icons/Plus-no-select.PNG", 10));
        playListsPanel.add(addToPlaylistsIcon);
        addToPlaylistsIcon.addMouseListener(new PlaylistsPlusIconListener(addToPlaylistsIcon));
        this.add(playListsPanel);
    }

    /**
     * This method changes size of icons.
     *
     * @param directory directory of image to change
     * @param scale     scale we want to resize.
     * @return resized image icon.
     */
    public static ImageIcon setIconSize(String directory, int scale) {
        ImageIcon output = GUIFrame.setIconSize(directory, scale);
        return output;
    }


    public static JPanel getAlbumsPanel() {
        return albumsPanel;
    }

    public static void setAlbumsPanel(JPanel changedAlbums) {
        albumsPanel = changedAlbums;
    }

    public static JPanel getPlayListsPanel() {
        return playListsPanel;
    }

    public static void setPlayListsPanel(JPanel changedPlaylists) {
        playListsPanel = changedPlaylists;
    }


}







