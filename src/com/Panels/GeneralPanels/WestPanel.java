
package com.Panels.GeneralPanels;
import com.GUIFrame.GUIFrame;
import com.Panels.WestPanelSections.WestPanelListeners.*;

import java.io.*;
import javax.swing.*;
import java.awt.*;

public class WestPanel extends JPanel
{
    private static WestPanel westPanel;
    private static JPanel albumsPanel;
    private static JPanel homePanel;
    private static JPanel songsPanel;
    private static JPanel libraryPanel;
    private static JPanel playListsPanel;
    private static JPanel artworkPanel;
    private static JLabel artworkLabel;

    public WestPanel()
    {
        westPanel=this;
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


        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        this.setBackground(new Color(23,23,23));
        this.setPreferredSize(new Dimension(150,600));
        homePanel=new JPanel();
        homePanel.setBackground(new Color(23,23,23));
        homePanel.setLayout(new BoxLayout(homePanel,BoxLayout.LINE_AXIS));
        homeIcon=new JLabel();
        homeIcon.setIcon(setIconSize("Icons/Home-no-select.png"));
        homeLabel=new JLabel(" HOME");
        homeIcon.addMouseListener(new HomePanelListener(homeIcon,homeLabel));
        homeLabel.addMouseListener(new HomePanelListener(homeIcon,homeLabel));
        homeLabel.setForeground(Color.WHITE);
        homePanel.add(homeIcon);
        homePanel.add(homeLabel);
        this.add(Box.createVerticalStrut(20));
        this.add(homePanel);
        this.add(Box.createVerticalStrut(50));
        libraryPanel=new JPanel();
        libraryPanel.setBackground(new Color(23,23,23));
        libraryPanel.setLayout(new BoxLayout(libraryPanel,BoxLayout.LINE_AXIS));
        libraryLabel=new JLabel(" Library  ");
        libraryLabel.setForeground(Color.WHITE);
        libraryIcon=new JLabel();
        libraryIcon.setIcon(setIconSize("Icons/Library-no-select.PNG"));
        addToLibraryIcon=new JLabel();
        addToLibraryIcon.setIcon(setPlusIconSize("Icons/Plus-no-select.PNG"));
        libraryLabel.addMouseListener(new LibraryPanelListener(libraryIcon,libraryLabel,addToLibraryIcon));
        libraryIcon.addMouseListener(new LibraryPanelListener(libraryIcon,libraryLabel,addToLibraryIcon));
        addToLibraryIcon.addMouseListener(new LibraryPanelListener(libraryIcon,libraryLabel,addToLibraryIcon));
        libraryPanel.add(libraryIcon);
        libraryPanel.add(libraryLabel);
        libraryPanel.add(addToLibraryIcon);
        this.add(libraryPanel);
        this.add(Box.createVerticalStrut(30));
        songsPanel=new JPanel();
        songsPanel.setBackground(new Color(23,23,23));
        songsPanel.setLayout(new BoxLayout(songsPanel,BoxLayout.LINE_AXIS));
        songsIcon=new JLabel();
        songsIcon.setIcon(setIconSize("Icons/Song-no-selected.png"));
        songsPanel.add(songsIcon);
        songsLabel=new JLabel(" Songs");
        songsIcon.addMouseListener(new SongsPanelListener(songsIcon,songsLabel));
        songsLabel.addMouseListener(new SongsPanelListener(songsIcon,songsLabel));
        songsLabel.setForeground(Color.WHITE);
        songsPanel.add(songsLabel);
        this.add(songsPanel);
        this.add(Box.createVerticalStrut(30));
        albumsPanel=new JPanel();
        albumsPanel.setBackground(new Color(23,23,23));
        albumsPanel.setLayout(new BoxLayout(albumsPanel,BoxLayout.LINE_AXIS));
        albumsIcon=new JLabel();
        albumsIcon.setIcon(setIconSize("Icons/Album-no-selected.png"));
        albumsPanel.add(albumsIcon);
        albumsLabel=new JLabel(" Albums");
        AlbumsPanelListener AlbumsTempListener=new AlbumsPanelListener(albumsIcon,albumsLabel);
        albumsIcon.addMouseListener(AlbumsTempListener);
        albumsLabel.addMouseListener(AlbumsTempListener);
        albumsLabel.setForeground(Color.WHITE);
        albumsPanel.add(albumsLabel);
        this.add(albumsPanel);
        this.add(Box.createVerticalStrut(30));
        playListsPanel=new JPanel();
        playListsPanel.setBackground(new Color(23,23,23));
        playListsPanel.setLayout(new BoxLayout(playListsPanel,BoxLayout.LINE_AXIS));
        playListsIcon=new JLabel();
        playListsIcon.setIcon(setIconSize("Icons/p-no-selected.png"));
        playListsPanel.add(playListsIcon);
        playListsLabel=new JLabel(" Playlists  ");
        playListsLabel.setForeground(Color.WHITE);
        playListsPanel.add(playListsLabel);
        PlaylistsListener playlistsTempListener=new PlaylistsListener(playListsIcon,playListsLabel);
        playListsIcon.addMouseListener(playlistsTempListener);
        playListsLabel.addMouseListener(playlistsTempListener);
        addToPlaylistsIcon=new JLabel();
        addToPlaylistsIcon.setIcon(setPlusIconSize("Icons/Plus-no-select.PNG"));
        playListsPanel.add(addToPlaylistsIcon);
        addToPlaylistsIcon.addMouseListener(new PlaylistsPlusIconListener(addToPlaylistsIcon));
        this.add(playListsPanel);

    }

    public static ImageIcon setIconSize(String name) {
        ImageIcon output = new ImageIcon(name);
        Image newImage = output.getImage();
        Image newimg = newImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        output = new ImageIcon(newimg);
        return output;
    }
    public static ImageIcon setPlusIconSize(String name) {
        ImageIcon output = new ImageIcon(name);
        Image newImage = output.getImage();
        Image newimg = newImage.getScaledInstance(10, 10, Image.SCALE_SMOOTH);
        output = new ImageIcon(newimg);
        return output;
    }
    public static JPanel getAlbumsPanel()
    {
        return albumsPanel;
    }
    public static WestPanel getWestPanel()
    {
        return westPanel;
    }

    public static void setAlbumsPanel(JPanel changedAlbums)
    {
        albumsPanel=changedAlbums;
    }
    public static JPanel getPlayListsPanel()
    {
        return playListsPanel;
    }
    public static void setPlayListsPanel(JPanel changedPlaylists)
    {
        playListsPanel=changedPlaylists;
    }
    /*public JButton getLibraryButton()
    {
        return libraryButton;
    }
    public JButton getAddToLibraryButton()
    {
        return addToLibraryButton;
    }
    public JButton getSongsButton()
    {
        return songsButton;
    }
    public JButton getAlbumsButton()
    {
        return albumsButton;
    }
    public JButton getPlayListsButton()
    {
        return playListsButton;

    }
    public JButton getAddToPlaylistsButton()
    {
        return addToPlaylistsButton;

    }*/





    }







