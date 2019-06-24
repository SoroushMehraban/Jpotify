package com.Panels.GeneralPanels;

import com.GUIFrame.GUIFrame;
import com.Panels.CenterPanelSections.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.io.IOException;

/**
 * This class is about center panel of our Jpotify
 * at the north part of this panel we have:
 * -Search box: user can search musics here.
 * -login box: user can login here to make a connection with network.
 * at center part of this panel it shows us albums and playlist which user choose to see
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
public class CenterPanel extends JPanel {
    private NorthPart northPart;
    private CenterPart centerPart;
    /**
     * Class constructor.
     * @throws IOException if opening icon image failed.
     */
    public CenterPanel() throws IOException {
        this.setLayout(new BorderLayout());//creating panel layout
        this.setBackground(new Color(23,23,23));//creating panel background

        centerPart = new CenterPart();//creating center part panel
        JScrollPane jScrollPane = new JScrollPane(centerPart);//creating JScrollPane to cover center part with scrollbar
        GUIFrame.customizeJScrollPane(jScrollPane);//customizing jScrollPane's colors
        this.add(jScrollPane,BorderLayout.CENTER);//adding center part to center panel.

        northPart = new NorthPart(centerPart);//creating north part of panel
        this.add(northPart,BorderLayout.NORTH);//adding north part of panel to center panel

    }

    public CenterPart getCenterPart() {
        return centerPart;
    }
}
