package com.Panels.CenterPanelSections;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * north part of center panel. it has this features:
 *-Search box: user can search musics here.
 *-login box: user can login here to make a connection with network.
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
public class NorthPart extends JPanel {
    private JTextField searchBox;

    /**
     * class constructor
     * @throws IOException if opening search box icon failed.
     */
    public NorthPart() throws IOException {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.getHSBColor(0, 0, 0.09f));

        searchBox = new SearchBox(10);
        searchBox.setForeground(Color.getHSBColor(0,0,0.66f));
        this.add(searchBox,BorderLayout.WEST);
    }
}
