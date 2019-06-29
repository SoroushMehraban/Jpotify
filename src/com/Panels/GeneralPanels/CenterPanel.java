package com.Panels.GeneralPanels;

import com.GUIFrame.GUIFrame;
import com.Panels.CenterPanelSections.*;

import javax.swing.*;
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
    private SortPart sortPart;

    /**
     * Class constructor.
     * it set border layout as it's layout and has only two parts:
     * center part: where music panels is shown there.
     * north part: where search box and user box is located there.
     */
    public CenterPanel() {
        this.setLayout(new BorderLayout());//creating panel layout
        this.setBackground(new Color(23, 23, 23));//creating panel background

        sortPart = new SortPart();

        centerPart = new CenterPart(sortPart);//creating center part panel
        JScrollPane jScrollPane = new JScrollPane(centerPart);//creating JScrollPane to cover center part with scrollbar
        GUIFrame.customizeJScrollPane(jScrollPane);//customizing jScrollPane's colors
        sortPart.setSortLinker(centerPart);

        JPanel centerContainer = new JPanel();
        centerContainer.setOpaque(false);//to show center panel background behind it.
        centerContainer.setLayout(new BorderLayout());
        centerContainer.add(sortPart, BorderLayout.NORTH);
        centerContainer.add(Box.createHorizontalStrut(3));
        centerContainer.add(jScrollPane, BorderLayout.CENTER);

        this.add(centerContainer, BorderLayout.CENTER);//adding center part to center panel.

        try {
            northPart = new NorthPart(centerPart);//creating north part of panel
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading north part images", "An Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
        this.add(northPart, BorderLayout.NORTH);//adding north part of panel to center panel

    }

    public CenterPart getCenterPart() {
        return centerPart;
    }

    public NorthPart getNorthPart() {
        return northPart;
    }
}
