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

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Port;

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
    private int lastSoundBarValue;

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
        this.setBackground(new Color(41, 41, 41));
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
        lastSoundBarValue = 50;
        changeVolumeSystem(50);
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
    private void createSoundBarAction() {
        soundBar.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                soundBar.setValue((int) (e.getX() / ((double) soundBar.getWidth()) * 100));
                changeVolumeSystem((float) (e.getX() / ((double) soundBar.getWidth())));
                lastSoundBarValue = soundBar.getValue();
                setSoundIcon();
            }
        });
        soundBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                changeVolumeSystem((float) (e.getX() / ((double) soundBar.getWidth())));
                soundBar.setValue((int) (e.getX() / ((double) soundBar.getWidth()) * 100));
                lastSoundBarValue = soundBar.getValue();
                setSoundIcon();
            }
        });
    }

    /**
     * this method changes image icon beside sound bar parallel with changing it.
     */
    private void setSoundIcon() {
        if (soundBar.getValue() >= 75)
            soundLabel.setIcon(new ImageIcon(soundImageHigh));
        else if (soundBar.getValue() >= 25)
            soundLabel.setIcon(new ImageIcon(soundImageMed));
        else if (soundBar.getValue() > 0)
            soundLabel.setIcon(new ImageIcon(soundImageLow));
        else
            soundLabel.setIcon(new ImageIcon(soundImageOff));
        GUIFrame.reload();
    }

    /**
     * This method demonstrate what happens if mouse pressed,entered or exited from sound Icon beside sound bar.
     * when mouse pressed: if volume is >0 it cuts it off but when is =0 it changes to previous volume or 0.5 if it's recent value is 0.
     * when mouse entered: it made that bottom look brighter.
     * when mouse exited: it changes to previous form.
     */
    private void createSoundLabelAction() {
        soundLabel.addMouseListener(new MouseAdapter(
        ) {
            @Override
            public void mousePressed(MouseEvent e) {
                if (soundBar.getValue() > 0) {
                    changeVolumeSystem(0);
                    soundBar.setValue(0);
                    soundLabel.setIcon(new ImageIcon(soundImageOff));
                } else {
                    if (lastSoundBarValue > 0)
                        soundBar.setValue(lastSoundBarValue);
                    else
                        soundBar.setValue(50);
                    changeVolumeSystem((float) (soundBar.getValue() / 100.0));
                    setSoundIcon();
                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                try {
                    if (soundBar.getValue() >= 75)
                        soundImageHigh = ImageIO.read(new File("Icons/High-Sound.png"));
                    else if (soundBar.getValue() >= 25)
                        soundImageMed = ImageIO.read(new File("Icons/Med-Sound.png"));
                    else if (soundBar.getValue() > 0)
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
                    if (soundBar.getValue() >= 75)
                        soundImageHigh = ImageIO.read(new File("Icons/High-Sound-no-select.png"));
                    else if (soundBar.getValue() >= 25)
                        soundImageMed = ImageIO.read(new File("Icons/Med-Sound-no-select.png"));
                    else if (soundBar.getValue() > 0)
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

    /**
     * Changing volume to where user desired.
     * this method doesn't change volume which shows at right corner of task bar of windows and just change music volume.
     *
     * @param volume desired value of volume
     */
    private void changeVolumeSystem(float volume) {
        Info source = Port.Info.SPEAKER;//getting speaker's source

        if (AudioSystem.isLineSupported(source))//if AudioSystem support it and we can change it
        {
            try {
                Port outline = (Port) AudioSystem.getLine(source);
                outline.open();
                FloatControl volumeControl = (FloatControl) outline.getControl(FloatControl.Type.VOLUME);
                if (volume > 0.02f)
                    volumeControl.setValue(volume);
                else
                    volumeControl.setValue(0);
            } catch (LineUnavailableException ex) {
                JOptionPane.showMessageDialog(null, "source not supported");
            }
        }
    }
}
