package com.Panels.WestPanelSections.WestPanelListeners;

import com.GUIFrame.GUIFrame;
import com.Panels.GeneralPanels.WestPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * this listener is for Songs button in west panel and has this jobs:
 * when mouse Clicked: it shows all songs in center panel based on last playing by user.
 * when mouse Entered: it updates song icon and become bolder and bigger
 * when mouse Exited: it turns to previous form.
 *
 * @author Morteza Damghani & Soroush Mehraban
 * @version 1.0
 */
public class SongsPanelListener extends MouseAdapter {

    private JLabel icon;
    private JLabel textLabel;
    private Font font;

    /**
     * Class constructor, it sets given parameters and default fonts
     *
     * @param icon      icon of song panel
     * @param textLabel a text label which is : " Songs"
     */
    public SongsPanelListener(JLabel icon, JLabel textLabel) {
        this.icon = icon;
        this.textLabel = textLabel;
        font = textLabel.getFont();

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        GUIFrame.showAllSongs();

    }


    @Override
    public void mouseEntered(MouseEvent e) {
        icon.setIcon(WestPanel.setIconSize("Icons/Song-selected.png", 20));
        textLabel.setFont(new Font("Serif", Font.BOLD, 16));

    }

    @Override
    public void mouseExited(MouseEvent e) {
        icon.setIcon(WestPanel.setIconSize("Icons/Song-no-selected.png", 20));
        textLabel.setFont(font);

    }
}
