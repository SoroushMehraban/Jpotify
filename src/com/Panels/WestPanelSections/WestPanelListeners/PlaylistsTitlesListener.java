package com.Panels.WestPanelSections.WestPanelListeners;

import com.GUIFrame.GUIFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlaylistsTitlesListener extends MouseAdapter
{
    private JLabel label;
    private Font font;
    public PlaylistsTitlesListener(JLabel label)
    {
        this.label=label;
        font=label.getFont();
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        GUIFrame.showPlaylistSongs(label.getText());

    }


    @Override
    public void mouseEntered(MouseEvent e) {

        label.setFont(new Font("Serif", Font.BOLD, 16));

    }

    @Override
    public void mouseExited(MouseEvent e) {

        label.setFont(font);

    }


}
