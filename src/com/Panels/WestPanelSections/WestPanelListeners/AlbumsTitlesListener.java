package com.Panels.WestPanelSections.WestPanelListeners;

import com.GUIFrame.GUIFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * this album title listener has this jobs to do:
 * when mouse clicked: it show songs with this album title in center panel.
 * when mouse entered: it become bolder and bigger
 * when mouse exited: it turn to previous form
 *
 * @author Morteza Damghani & Soroush Mehraban
 * @version 1.0
 */
public class AlbumsTitlesListener extends MouseAdapter {
    private JLabel title;
    private Font font;

    /**
     * Class constructor, it sets title and default font
     *
     * @param title title of album.
     */
    AlbumsTitlesListener(JLabel title) {
        this.title = title;
        font = title.getFont();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        GUIFrame.showAlbumSongs(title.getText());

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        title.setFont(new Font("Serif", Font.BOLD, 16));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        title.setFont(font);
    }
}
