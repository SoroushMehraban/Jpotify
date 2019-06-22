
package com.Panels.GeneralPanels;
import com.Panels.WestPanelSections.WestPanelListeners.LibraryPanelListener;
import com.Panels.WestPanelSections.WestPanelListeners.SongsPanelListener;

import java.io.*;
import javax.swing.*;
import java.awt.*;

public class WestPanel extends JPanel
{


    public WestPanel()
    {
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
        JPanel homePanel=new JPanel();
        homePanel.setBackground(new Color(23,23,23));
        homePanel.setLayout(new BoxLayout(homePanel,BoxLayout.LINE_AXIS));
        homeIcon=new JLabel();
        ImageIcon homeImage=new ImageIcon("Icons/Home-no-select.png");
        homeIcon.setIcon(homeImage);
        homeLabel=new JLabel(" HOME");
        homeLabel.setForeground(Color.WHITE);
        homePanel.add(homeIcon);
        homePanel.add(homeLabel);
        this.add(Box.createVerticalStrut(20));
        this.add(homePanel);
        this.add(Box.createVerticalStrut(20));
        this.add(Box.createVerticalStrut(20));
        JPanel libraryPanel=new JPanel();
        libraryPanel.setBackground(new Color(23,23,23));
        libraryPanel.setLayout(new BoxLayout(libraryPanel,BoxLayout.LINE_AXIS));
        libraryLabel=new JLabel(" Library  ");
        libraryLabel.setForeground(Color.WHITE);
        libraryIcon=new JLabel();
        ImageIcon libraryImage=new ImageIcon("Icons/Library-no-select.PNG");
        libraryIcon.setIcon(libraryImage);
        addToLibraryIcon=new JLabel();
        ImageIcon addToLibraryImage=new ImageIcon("Icons/Plus-no-select.PNG");
        addToLibraryIcon.setIcon(addToLibraryImage);
        libraryLabel.addMouseListener(new LibraryPanelListener(libraryIcon,libraryLabel,addToLibraryIcon));
        libraryIcon.addMouseListener(new LibraryPanelListener(libraryIcon,libraryLabel,addToLibraryIcon));
        addToLibraryIcon.addMouseListener(new LibraryPanelListener(libraryIcon,libraryLabel,addToLibraryIcon));
        libraryPanel.add(libraryIcon);
        libraryPanel.add(libraryLabel);
        libraryPanel.add(addToLibraryIcon);
        this.add(libraryPanel);
        this.add(Box.createVerticalStrut(20));
        JPanel songsPanel=new JPanel();
        songsPanel.setBackground(new Color(23,23,23));
        songsPanel.setLayout(new BoxLayout(songsPanel,BoxLayout.LINE_AXIS));
        songsIcon=new JLabel();
        ImageIcon songsImage=new ImageIcon("Icons/Song-no-selected.png");
        songsIcon.setIcon(songsImage);
        songsPanel.add(songsIcon);
        songsLabel=new JLabel(" Songs");
        songsIcon.addMouseListener(new SongsPanelListener(songsIcon,songsLabel));
        songsLabel.addMouseListener(new SongsPanelListener(songsIcon,songsLabel));
        songsLabel.setForeground(Color.WHITE);
        songsPanel.add(songsLabel);
        this.add(songsPanel);
        this.add(Box.createVerticalStrut(20));
        JPanel albumsPanel=new JPanel();
        albumsPanel.setBackground(new Color(23,23,23));
        albumsPanel.setLayout(new BoxLayout(albumsPanel,BoxLayout.LINE_AXIS));
        albumsIcon=new JLabel();
        ImageIcon albumsImage=new ImageIcon("Icons/Album-no-selected.png");
        albumsIcon.setIcon(albumsImage);
        albumsPanel.add(albumsIcon);
        albumsLabel=new JLabel(" Albums");
        albumsLabel.setForeground(Color.WHITE);
        albumsPanel.add(albumsLabel);
        this.add(albumsPanel);
        this.add(Box.createVerticalStrut(20));
        JPanel playListsPanel=new JPanel();
        playListsPanel.setBackground(new Color(23,23,23));
        playListsPanel.setLayout(new BoxLayout(playListsPanel,BoxLayout.LINE_AXIS));
        playListsIcon=new JLabel();
        ImageIcon playListsImage=new ImageIcon("Icons/p-no-selected.png");
        playListsIcon.setIcon(playListsImage);
        playListsPanel.add(playListsIcon);
        playListsLabel=new JLabel(" Playlists  ");
        playListsLabel.setForeground(Color.WHITE);
        playListsPanel.add(playListsLabel);
        addToPlaylistsIcon=new JLabel();
        addToPlaylistsIcon.setIcon(addToLibraryImage);
        playListsPanel.add(addToPlaylistsIcon);
        this.add(playListsPanel);








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







