package com.Panels.SouthPanelSections;

import com.GUIFrame.GUIFrame;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class is about east part of south panel
 * it has a Sound Bar where user can change volume of music
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
public class EastPartPanel extends JPanel {
    private JProgressBar soundBar;
    private JProgressBar sound;
    private BufferedImage soundImageOff;
    private BufferedImage soundImageLow;
    private BufferedImage soundImageMed;
    private BufferedImage soundImageHigh;
    private JLabel soundLabel;

    /**
     * class constuctor,
     * it creates a sound bar which is a JProgressBar and creates a volume image label beside of it
     *
     * @throws IOException if fails opening image icons.
     */
    public EastPartPanel() throws IOException {
        //setting layout to Box layout and Line axis:
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        //setting background:
        this.setBackground(Color.getHSBColor(0, 0, 0.16f));
        //loading icons:
        soundImageOff = ImageIO.read(new File("Icons/No-Sound-no-select.png"));
        soundImageLow = ImageIO.read(new File("Icons/Low-Sound-no-select.png"));
        soundImageMed = ImageIO.read(new File("Icons/Med-Sound-no-select.png"));
        soundImageHigh = ImageIO.read(new File("Icons/High-Sound-no-select.png"));
        //creating soundlabel:
        soundLabel = new JLabel(new ImageIcon(soundImageMed));
        createSoundLabelAction();
        //creating soundBar:
        soundBar = new JProgressBar();
        soundBar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        soundBar.setPreferredSize(new Dimension(40, 7));
        soundBar.setMaximumSize(soundBar.getPreferredSize());
        soundBar.setMinimumSize(soundBar.getPreferredSize());
        createSoundBarAction();
        soundBar.setValue(50);
        //adding components to panel:
        this.add(soundLabel);
        this.add(Box.createHorizontalStrut(5));
        this.add(soundBar);
        this.add(Box.createHorizontalStrut(20));
    }

    /**
     * This method demonstrate what happens if mouse pressed or dragged on sound bar.
     * when mouse pressed: it changes volume to time where user clicks.
     * when mouse dragged: it drags sound bar with mouse and after that,changes volume to that point.
     */
    private void createSoundBarAction(){
        soundBar.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                soundBar.setValue((int) (e.getX() / ((double) soundBar.getWidth()) * 100));
                setSoundIcon();
            }
        });
        soundBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                soundBar.setValue((int) (e.getX() / ((double) soundBar.getWidth()) * 100));
                setSoundIcon();
            }
        });
    }

    /**
     * this method changes image icon beside sound bar parallel with changing it.
     */
    private void setSoundIcon(){
        if(soundBar.getValue() >= 75)
            soundLabel.setIcon(new ImageIcon(soundImageHigh));
        else if(soundBar.getValue() >=25)
            soundLabel.setIcon(new ImageIcon(soundImageMed));
        else if(soundBar.getValue() > 0)
            soundLabel.setIcon(new ImageIcon(soundImageLow));
        else
            soundLabel.setIcon(new ImageIcon(soundImageOff));
        GUIFrame.reload();
    }
    /**
     * This method demonstrate what happens if mouse pressed,entered or exited from sound Icon beside sound bar.
     * when mouse pressed: if volume is >0 it cuts it off but when is =0 it changes to 50%.
     * when mouse entered: it made that bottom look brighter.
     * when mouse exited: it changes to previous form.
     */
    private void createSoundLabelAction(){
        soundLabel.addMouseListener(new MouseAdapter(
        ) {
                /*@Override
                public void mousePressed(MouseEvent e) {
                    changePlayLabel(pauseLabel);
                }*/

            @Override
            public void mouseEntered(MouseEvent e) {
                try {
                    if(soundBar.getValue() >= 75)
                        soundImageHigh = ImageIO.read(new File("Icons/High-Sound.png"));
                    else if(soundBar.getValue() >= 25)
                        soundImageMed = ImageIO.read(new File("Icons/Med-Sound.png"));
                    else if(soundBar.getValue() > 0)
                        soundImageLow = ImageIO.read(new File("Icons/Low-Sound.png"));
                    else
                        soundImageOff = ImageIO.read(new File("Icons/No-Sound.png"));
                    setSoundIcon();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                try {
                    if(soundBar.getValue() >= 75)
                        soundImageHigh = ImageIO.read(new File("Icons/High-Sound-no-select.png"));
                    else if(soundBar.getValue() >= 25)
                        soundImageMed = ImageIO.read(new File("Icons/Med-Sound-no-select.png"));
                    else if(soundBar.getValue() > 0)
                        soundImageLow = ImageIO.read(new File("Icons/Low-Sound-no-select.png"));
                    else
                        soundImageOff = ImageIO.read(new File("Icons/No-Sound-no-select.png"));
                    setSoundIcon();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                super.mouseExited(e);
            }
        });
    }
}
