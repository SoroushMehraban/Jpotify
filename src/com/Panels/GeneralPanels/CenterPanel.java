package com.Panels.GeneralPanels;

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
        this.setBackground(Color.getHSBColor(0, 0, 0.09f));//creating panel background

        northPart = new NorthPart();//creating north part of panel
        this.add(northPart,BorderLayout.NORTH);//adding north part of panel to center panel

        centerPart = new CenterPart();//creating center part panel
        JScrollPane jScrollPane = new JScrollPane(centerPart);//creating JScrollPane to cover center part with scrollbar
        customizeJScrollPane(jScrollPane);//customizing jScrollPane's colors
        this.add(jScrollPane,BorderLayout.CENTER);//adding center part to center panel.
    }

    /**
     * this method customize JScrollPane's color to fit in center part theme.
     * @param jScrollPane our jScrollPane to to be customized.
     */
    public static void customizeJScrollPane(JScrollPane jScrollPane){
        jScrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors(){
                this.thumbColor = Color.getHSBColor(0,0,0.33f);
                this.trackColor = Color.getHSBColor(0,0,0.09f);
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return new BasicArrowButton(orientation,
                        Color.getHSBColor(0,0,0.09f),
                        Color.getHSBColor(0,0,0.09f),
                        Color.getHSBColor(0,0,0.33f),
                        Color.getHSBColor(0,0,0.09f));
            }

            protected JButton createDecreaseButton(int orientation)  {
                return new BasicArrowButton(orientation,
                        Color.getHSBColor(0,0,0.09f),
                        Color.getHSBColor(0,0,0.09f),
                        Color.getHSBColor(0,0,0.33f),
                        Color.getHSBColor(0,0,0.09f));
            }
        });
    }

}