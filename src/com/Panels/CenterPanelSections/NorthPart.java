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
        //setting north part layout:
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        //setting background color:
        this.setBackground(new Color(23,23,23));
        searchBox = new SearchBox(10);//creating searchbox
        searchBox.setForeground(new Color(168,168,168));//setting search box text color
        this.add(searchBox);//adding search box beside left arrow label
    }
}
