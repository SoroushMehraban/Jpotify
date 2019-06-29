package com.Panels.WestPanelSections.WestPanelListeners;

import com.GUIFrame.GUIFrame;
import com.Panels.GeneralPanels.WestPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * this album title listener has this jobs to do:
 * when mouse clicked: it show homes which shows albums and playlist
 * when mouse entered: it become bolder and bigger
 * when mouse exited: it turn to previous form
 *
 * @author Morteza Damghani & Soroush Mehraban
 * @version 1.0
 */
public class HomePanelListener extends MouseAdapter {
    private JLabel icon;
    private JLabel label;
    private Font font;

    /**
     * Class Constructor which sets given parameters.
     *
     * @param icon icon of Home label
     * @param text a text label which it is " HOME"
     */
    public HomePanelListener(JLabel icon, JLabel text) {
        this.icon = icon;
        this.label = text;
        font = text.getFont();

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        GUIFrame.showHome();

    }


    @Override
    public void mouseEntered(MouseEvent e) {
        icon.setIcon(WestPanel.setIconSize("Icons/Home.png", 20));
        label.setFont(new Font("Serif", Font.BOLD, 16));

    }

    @Override
    public void mouseExited(MouseEvent e) {
        icon.setIcon(WestPanel.setIconSize("Icons/Home-no-select.png", 20));
        label.setFont(font);

    }
}
