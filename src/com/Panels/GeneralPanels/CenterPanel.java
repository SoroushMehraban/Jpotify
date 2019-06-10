package com.Panels.GeneralPanels;

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

    /**
     * Class constructor.
     * @throws IOException if opening icon image failed.
     */
    public CenterPanel() throws IOException {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.getHSBColor(0, 0, 0.09f));

        northPart = new NorthPart();
        this.add(northPart,BorderLayout.NORTH);
    }
}
