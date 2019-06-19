package com.Panels.CenterPanelSections;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
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
    private BufferedImage leftArrowNoSelectedImage;
    private BufferedImage leftArrowImage;
    private JLabel leftArrowLabel;

    /**
     * class constructor
     * @throws IOException if opening search box icon failed.
     */
    public NorthPart() throws IOException {
        //setting north part layout:
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        //setting background color:
        this.setBackground(new Color(23,23,23));

        //loading images:
        leftArrowNoSelectedImage = ImageIO.read(new File("Icons/LeftArrow-no-select.png"));
        leftArrowImage = ImageIO.read(new File("Icons/LeftArrow.png"));
        //creating label to be controlled by user:
        leftArrowLabel = new JLabel(new ImageIcon(leftArrowNoSelectedImage));

        this.add(leftArrowLabel);//adding label to left corner
        this.add(Box.createHorizontalStrut(10));//creating some spaces after label

        searchBox = new SearchBox(10);//creating searchbox
        searchBox.setForeground(new Color(168,168,168));//setting search box text color
        this.add(searchBox);//adding search box beside left arrow label
    }

    public BufferedImage getLeftArrowNoSelectedImage() {
        return leftArrowNoSelectedImage;
    }

    public BufferedImage getLeftArrowImage() {
        return leftArrowImage;
    }
    public void setLeftArrowListener(MouseListener mouseListener){
        leftArrowLabel.addMouseListener(mouseListener);
    }
    public void setLeftArrowIcon(BufferedImage image){
        leftArrowLabel.setIcon(new ImageIcon(image));
    }
}
