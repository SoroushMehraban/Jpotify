package com.Panels.CenterPanelSections;

import com.Interfaces.SearchLinker;
import com.jtattoo.plaf.BaseComboBoxUI;
import com.jtattoo.plaf.hifi.HiFiComboBoxUI;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.BasicComboBoxUI;
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
     * @param searchLinker a linker helps to searchBox search in centerPart.
     * @throws IOException if opening search box icon failed.
     */
    public NorthPart(SearchLinker searchLinker) throws IOException {
        //setting north part layout:
        this.setLayout(new BorderLayout());
        //setting background color:
        this.setBackground(new Color(23,23,23));

        searchBox = new SearchBox(10,searchLinker);//creating searchbox
        searchBox.setForeground(new Color(168,168,168));//setting search box text color
        this.add(searchBox,BorderLayout.WEST);//adding search box in west of our panel.

        JComboBox usersBox=new JComboBox();//creating a JComboBox for user.
        usersBox.setUI(new BaseComboBoxUI());
        usersBox.setPreferredSize(new Dimension(100,25));//setting PreferredSize to show.
        JLabel temp=new JLabel("USER");
        //usersBox.addItem(temp);

        this.add(usersBox,BorderLayout.EAST);
    }
}
