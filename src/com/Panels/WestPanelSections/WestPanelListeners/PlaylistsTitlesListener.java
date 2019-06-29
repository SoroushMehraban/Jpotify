package com.Panels.WestPanelSections.WestPanelListeners;

import com.GUIFrame.GUIFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * this listener is for every playlist title shows as a list under playlist button:
 * when mouse Clicked: it opens chosen playlist in center panel
 * when mouse Entered: it becomes bolder
 * when mouse Exited: it turns to previous form
 */
public class PlaylistsTitlesListener extends MouseAdapter {
    private JLabel titleTextLabel;
    private Font font;

    /**
     * class constructor, it sets title of playlist as a label and sets default font.
     *
     * @param titleTextLabel title of playlist
     */
    PlaylistsTitlesListener(JLabel titleTextLabel) {
        this.titleTextLabel = titleTextLabel;
        font = titleTextLabel.getFont();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        GUIFrame.showPlaylistSongs(titleTextLabel.getText());
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        titleTextLabel.setFont(new Font("Serif", Font.BOLD, 16));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        titleTextLabel.setFont(font);
    }
}
