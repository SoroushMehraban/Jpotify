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
        this.setBackground(new Color(23,23,23));

        searchBox = new SearchBox(10);
        searchBox.setForeground(new Color(168,168,168));
        this.add(searchBox,BorderLayout.WEST);
    }
}
