package com.Panels.WestPanelSections.WestPanelListeners;

import com.GUIFrame.GUIFrame;
import com.Panels.GeneralPanels.WestPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AlbumsTitlesListener extends MouseAdapter
{
    private JLabel label;
    private Font font;
    public AlbumsTitlesListener(JLabel label)
    {
     this.label=label;
     font=label.getFont();
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        GUIFrame.showAlbumSongs(label.getText());

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
