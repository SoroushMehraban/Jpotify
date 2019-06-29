package com.Panels.SouthPanelSections;

import com.MP3.MP3Info;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This inner class is about west part panel of com.Panels.GeneralPanels.SouthPanel for Jpotify.
 * it shows us 2 things: Song Name and Artist Name.
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
public class WestPartPanel extends JPanel {
    private JLabel songName;
    private JLabel artistName;
    private String artistFullName;
    private String songFullName;
    private boolean isArtistEntered;
    private boolean isSongEntered;

    /**
     * Class Constructor.
     *
     * @throws IOException if opening plus icon failed.
     */
    public WestPartPanel() throws IOException {
        //setting layout to Box layout and Line axis:
        this.setLayout(new GridBagLayout());
        this.setPreferredSize(new Dimension(100, 50));
        GridBagConstraints constraints = new GridBagConstraints();
        //setting background:
        this.setBackground(new Color(41, 41, 41));
        //creating song name label:
        songFullName = "Song Name";
        songName = new JLabel(songFullName);
        songName.setForeground(new Color(179, 179, 179));
        songName.setMaximumSize(new Dimension(72, 16));
        songName.setMinimumSize(new Dimension(72, 16));
        songName.setPreferredSize(new Dimension(72, 16));
        createSongNameAction();
        //creating artist name label:
        artistFullName = "Artist Name";
        artistName = new JLabel(artistFullName);
        artistName.setForeground(new Color(92, 84, 84));
        artistName.setMaximumSize(new Dimension(72, 16));
        artistName.setMinimumSize(new Dimension(72, 16));
        artistName.setPreferredSize(new Dimension(72, 16));
        createArtistNameAction();
        //putting components in panel:
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 5, 0, 0);
        constraints.gridx = 0;
        constraints.gridy = 0;
        this.add(songName, constraints);
        constraints.insets = new Insets(0, 5, 0, 0);
        constraints.gridx = 0;
        constraints.gridy = 1;
        this.add(artistName, constraints);
    }

    /**
     * This method demonstrate what happens if mouse pressed or exited from Artist name at left down corner.
     * when mouse entered: if it's length is more than 15 character, it moves in a animated way.
     * when mouse exited: it changes to previous form.
     */
    private void createArtistNameAction() {
        artistName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isArtistEntered = true;//this variable indicated when should thread in below terminate.
                if (artistFullName.length() > 12) {//if it's size is more than 12 character and doesn't fit
                    Thread artistNameThread = new Thread(new Runnable() {//creating thread to move the text
                        @Override
                        public void run() {
                            while (isArtistEntered)//while mouse is Entered
                                for (int i = 0; i < artistFullName.length() - 9 && isArtistEntered; i++) {
                                    artistName.setText(artistFullName.substring(i, i + 9) + "  ");
                                    try {
                                        Thread.sleep(500);
                                    } catch (InterruptedException e1) {
                                        JOptionPane.showMessageDialog(null, "Artist name thread Interrupted");
                                    }
                                }
                        }
                    });
                    artistNameThread.start();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                artistName.setText(createShortenedText(artistFullName));//changing text to previous form
                isArtistEntered = false;//closing thread if it's running
            }
        });
    }

    /**
     * This method demonstrate what happens if mouse pressed or exited from Song name at left down corner.
     * when mouse entered: if it's length is more than 15 character, it moves in a animated way.
     * when mouse exited: it changes to previous form.
     */
    private void createSongNameAction() {
        songName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isSongEntered = true;//this variable indicated when should thread in below terminate.
                if (songFullName.length() > 12) {//if it's size is more than 12 character and doesn't fit
                    Thread songNameThread = new Thread(new Runnable() {//creating thread to move the text
                        @Override
                        public void run() {
                            while (isSongEntered)//while mouse is Entered
                                for (int i = 0; i < songFullName.length() - 9 && isSongEntered; i++) {
                                    songName.setText(songFullName.substring(i, i + 9));
                                    try {
                                        Thread.sleep(500);
                                    } catch (InterruptedException e1) {
                                        JOptionPane.showMessageDialog(null, "Song name thread Interrupted");
                                    }
                                }
                        }
                    });
                    songNameThread.start();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                songName.setText(createShortenedText(songFullName));//changing text to previous form
                isSongEntered = false;//closing thread if it's running
            }
        });
    }

    public void updateNames(String directory) {
        try {
            MP3Info mp3Info = new MP3Info(directory);
            artistFullName = mp3Info.getArtist();
            songFullName = mp3Info.getTitle();
            String artist = createShortenedText(artistFullName);
            String songTitle = createShortenedText(songFullName);
            artistName.setText(artist);
            songName.setText(songTitle);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading mp3 file");
        } catch (NoSuchFieldException e) {
            JOptionPane.showMessageDialog(null, "There isn't any mp3 file in directory to get info");
        }
    }

    private String createShortenedText(String text) {
        if (text.length() <= 12)
            return text;
        else
            return text.substring(0, 9) + "...";
    }
}