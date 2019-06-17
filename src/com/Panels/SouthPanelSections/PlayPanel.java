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
 * this class is about bottoms above Music Player Bar.
 * the bottoms are :
 * -play bottom: when user clicks, it plays music.
 * -pause bottom: when user clicks, it pauses the msuic.
 * -forward bottom: changes music to next music.
 * -backward bottom: changes music to previous music.
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
public class PlayPanel extends JPanel {
    private BufferedImage playImage;
    private BufferedImage pauseImage;
    private BufferedImage forwardImage;
    private BufferedImage backwardImage;
    private BufferedImage repeatImage;
    private BufferedImage shuffleImage;
    private JLabel playLabel;
    private JLabel pauseLabel;
    private JLabel forwardLabel;
    private JLabel backwardLabel;
    private JLabel repeatLabel;
    private JLabel shuffleLabel;
    private boolean isShuffle;
    private boolean isRepeat;

    /**
     * inner class constructor.
     * @throws IOException when failed to open icons.
     */
    public PlayPanel() throws IOException {
        isShuffle = false;
        isRepeat = false;
        //setting background color:
        this.setBackground(new Color(41,41,41));
        //setting layout:
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        //loading images:
        playImage = ImageIO.read(new File("Icons/Play-no-select.png"));
        pauseImage = ImageIO.read(new File("Icons/Pause-no-select.png"));
        forwardImage = ImageIO.read(new File("Icons/Forward-no-select.png"));
        backwardImage = ImageIO.read(new File("Icons/Backward-no-select.png"));
        repeatImage = ImageIO.read(new File("Icons/Repeat-no-select.png"));
        shuffleImage = ImageIO.read(new File("Icons/Shuffle-no-select.png"));
        //creating image labels:
        playLabel = new JLabel(new ImageIcon(playImage));
        createPlayLabelListener();//add Mouse listener to playLabel
        pauseLabel = new JLabel(new ImageIcon(pauseImage));
        createPauseLabelListener();
        forwardLabel = new JLabel(new ImageIcon(forwardImage));
        createForwardLabelListener();
        backwardLabel = new JLabel(new ImageIcon(backwardImage));
        createBackwardLabelListener();
        repeatLabel = new JLabel(new ImageIcon(repeatImage));
        createRepeatLabelListener();
        shuffleLabel = new JLabel(new ImageIcon(shuffleImage));
        createShuffleLabelListener();
        //adding labels to PlayPanel:
        this.add(shuffleLabel);
        this.add(Box.createHorizontalStrut(10));
        this.add(backwardLabel);
        this.add(Box.createHorizontalStrut(10));
        this.add(playLabel);
        this.add(Box.createHorizontalStrut(10));
        this.add(forwardLabel);
        this.add(Box.createHorizontalStrut(10));
        this.add(repeatLabel);
    }

    /**
     * This method demonstrate what happens if mouse pressed,entered or exited from play bottom.
     * when mouse pressed: it changes the bottom to pause bottom and plays the music.
     * when mouse entered: it made that bottom look brighter.
     * when mouse exited: it changes to previous form.
     */
    private void createPlayLabelListener() {
        playLabel.addMouseListener(new MouseAdapter(
        ) {
            @Override
            public void mousePressed(MouseEvent e) {
                changePlayLabel(pauseLabel);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                try {
                    playImage = ImageIO.read(new File("Icons/Play.png"));
                    playLabel.setIcon(new ImageIcon(playImage));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                try {
                    playImage = ImageIO.read(new File("Icons/Play-no-select.png"));
                    playLabel.setIcon(new ImageIcon(playImage));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                super.mouseExited(e);
            }
        });
    }

    /**
     * this method changes the play bottom to pause bottom or vice versa.
     * @param inputLabel bottom label to substitute.
     */
    private void changePlayLabel(JLabel inputLabel) {
        this.removeAll();
        this.add(shuffleLabel);
        this.add(Box.createHorizontalStrut(10));
        this.add(backwardLabel);
        this.add(Box.createHorizontalStrut(10));
        this.add(inputLabel);
        this.add(Box.createHorizontalStrut(10));
        this.add(forwardLabel);
        this.add(Box.createHorizontalStrut(10));
        this.add(repeatLabel);
        GUIFrame.reload();
    }
    /**
     * This method demonstrate what happens if mouse pressed,entered or exited from pause bottom.
     * when mouse pressed: it changes the bottom to play bottom and pauses the music.
     * when mouse entered: it made that bottom look brighter.
     * when mouse exited: it changes to previous form.
     */
    private void createPauseLabelListener() {
        pauseLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                changePlayLabel(playLabel);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                try {
                    pauseImage = ImageIO.read(new File("Icons/Pause.png"));
                    pauseLabel.setIcon(new ImageIcon(pauseImage));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                try {
                    pauseImage = ImageIO.read(new File("Icons/Pause-no-select.png"));
                    pauseLabel.setIcon(new ImageIcon(pauseImage));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                super.mouseExited(e);
            }
        });
    }
    /**
     * This method demonstrate what happens if mouse pressed,entered or exited from forward bottom.
     * when mouse pressed: it goes to next music.
     * when mouse entered: it made that bottom look brighter.
     * when mouse exited: it changes to previous form.
     */
    private void createForwardLabelListener() {
        forwardLabel.addMouseListener(new MouseAdapter(
        ) {
                /*@Override
                public void mousePressed(MouseEvent e) {
                    changePlayLabel(pauseLabel);
                }*/

            @Override
            public void mouseEntered(MouseEvent e) {
                try {
                    forwardImage = ImageIO.read(new File("Icons/Forward.png"));
                    forwardLabel.setIcon(new ImageIcon(forwardImage));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                try {
                    forwardImage = ImageIO.read(new File("Icons/Forward-no-select.png"));
                    forwardLabel.setIcon(new ImageIcon(forwardImage));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                super.mouseExited(e);
            }
        });
    }
    /**
     * This method demonstrate what happens if mouse pressed,entered or exited from backward bottom.
     * when mouse pressed: it goes to previous music.
     * when mouse entered: it made that bottom look brighter.
     * when mouse exited: it changes to previous form.
     */
    private void createBackwardLabelListener() {
        backwardLabel.addMouseListener(new MouseAdapter(
        ) {
                /*@Override
                public void mousePressed(MouseEvent e) {
                    changePlayLabel(pauseLabel);
                }*/

            @Override
            public void mouseEntered(MouseEvent e) {
                try {
                    backwardImage = ImageIO.read(new File("Icons/Backward.png"));
                    backwardLabel.setIcon(new ImageIcon(backwardImage));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                try {
                    backwardImage = ImageIO.read(new File("Icons/Backward-no-select.png"));
                    backwardLabel.setIcon(new ImageIcon(backwardImage));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                super.mouseExited(e);
            }
        });
    }
    /**
     * This method demonstrate what happens if mouse pressed,entered or exited from play bottom.
     * when mouse pressed: it changes the bottom to pause bottom and plays the music.
     * when mouse entered: it made that bottom look brighter.
     * when mouse exited: it changes to previous form.
     */
    private void createShuffleLabelListener() {
        shuffleLabel.addMouseListener(new MouseAdapter(
        ) {
            @Override
            public void mousePressed(MouseEvent e) {
                isShuffle = !isShuffle;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                try {
                    if(!isShuffle) {
                        shuffleImage = ImageIO.read(new File("Icons/Shuffle.png"));
                        shuffleLabel.setIcon(new ImageIcon(shuffleImage));
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                try {
                    if(!isShuffle)
                        shuffleImage = ImageIO.read(new File("Icons/Shuffle-no-select.png"));
                    else
                        shuffleImage = ImageIO.read(new File("Icons/Shuffle.png"));
                    shuffleLabel.setIcon(new ImageIcon(shuffleImage));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                super.mouseExited(e);
            }
        });
    }
    /**
     * This method demonstrate what happens if mouse pressed,entered or exited from repeat bottom.
     * when mouse pressed: it repeat playing music after finished.
     * when mouse entered: it made that bottom look brighter or vice versa if pressed before.
     * when mouse exited: it changes to previous form.
     */
    private void createRepeatLabelListener() {
        repeatLabel.addMouseListener(new MouseAdapter(
        ) {
            @Override
            public void mousePressed(MouseEvent e) {
                isRepeat = !isRepeat;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                try {
                    if(!isRepeat) {
                        repeatImage = ImageIO.read(new File("Icons/Repeat.png"));
                        repeatLabel.setIcon(new ImageIcon(repeatImage));
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                try {
                    if(!isRepeat)
                        repeatImage = ImageIO.read(new File("Icons/Repeat-no-select.png"));
                    else
                        repeatImage = ImageIO.read(new File("Icons/Repeat.png"));
                    repeatLabel.setIcon(new ImageIcon(repeatImage));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                super.mouseExited(e);
            }
        });
    }
}
