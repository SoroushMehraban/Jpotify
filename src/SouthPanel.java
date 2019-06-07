import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * South Panel of program
 * at center of panel:
 * -user can play,pause music or shuffle between musics.
 * -in musicPlayerPaneL: it shows current time of music and total of time and user can drag music wherever he/she wants.
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
public class SouthPanel extends JPanel {
    private JProgressBar soundBar;
    private JPanel westPart;
    private EastPartPanel eastPart;
    private JPanel centerPart;
    private ProgressPanel progressPanel;
    private PlayPanel playPanel;

    /**
     * Class Constructor
     * @throws IOException if reading PlayPanel icons failed.
     */
    public SouthPanel() throws IOException {
        //setting South Panel layout:
        this.setLayout(new BorderLayout());

        //setting background color of SouthPanel:
        this.setBackground(Color.getHSBColor(0, 0, 0.16f));

        //-----------------------------------------------------------------------------
        //center part where PlayPanel and MusicPlayBar located on:
        centerPart = new JPanel();
        centerPart.setLayout(new BoxLayout(centerPart, BoxLayout.PAGE_AXIS));
        centerPart.setBackground(Color.getHSBColor(0, 0, 0.16f));
        this.add(centerPart, BorderLayout.CENTER);

        centerPart.add(Box.createVerticalStrut(5));
        playPanel = new PlayPanel();//creating new PlayPanel
        centerPart.add(playPanel);//adding playPanel to center part of south panel

        progressPanel = new ProgressPanel();
        centerPart.add(progressPanel);//adding progressPanel to center part of south panel
        centerPart.add(Box.createVerticalStrut(5));
        //----------------------------------------------------------------------------

        westPart = new JPanel();
        //this.add(westPart,BorderLayout.WEST);

        eastPart = new EastPartPanel();
        this.add(eastPart,BorderLayout.EAST);
    }

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
    private class PlayPanel extends JPanel {
        private BufferedImage playImage;
        private BufferedImage pauseImage;
        private BufferedImage forwardImage;
        private BufferedImage backwardImage;
        private JLabel playLabel;
        private JLabel pauseLabel;
        private JLabel forwardLabel;
        private JLabel backwardLabel;

        /**
         * inner class constructor.
         * @throws IOException when failed to open icons.
         */
        public PlayPanel() throws IOException {
            //setting background color:
            this.setBackground(Color.getHSBColor(0, 0, 0.16f));
            //setting layout:
            this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
            //loading images:
            playImage = ImageIO.read(new File("Icons/Play-no-select.png"));
            pauseImage = ImageIO.read(new File("Icons/Pause-no-select.png"));
            forwardImage = ImageIO.read(new File("Icons/Forward-no-select.png"));
            backwardImage = ImageIO.read(new File("Icons/Backward-no-select.png"));
            //creating image labels:
            playLabel = new JLabel(new ImageIcon(playImage));
            createPlayLabelListener();//add Mouse listener to playLabel
            pauseLabel = new JLabel(new ImageIcon(pauseImage));
            createPauseLabelListener();
            forwardLabel = new JLabel(new ImageIcon(forwardImage));
            createForwardLabelListener();
            backwardLabel = new JLabel(new ImageIcon(backwardImage));
            createBackwardLabelListener();
            //adding labels to PlayPanel:
            this.add(backwardLabel);
            this.add(Box.createHorizontalStrut(10));
            this.add(playLabel);
            this.add(Box.createHorizontalStrut(10));
            this.add(forwardLabel);
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
            playPanel.removeAll();
            playPanel.add(backwardLabel);
            playPanel.add(Box.createHorizontalStrut(10));
            playPanel.add(inputLabel);
            playPanel.add(Box.createHorizontalStrut(10));
            playPanel.add(forwardLabel);
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
    }

    /**
     * this class is about Music Player bar which located on south panel.
     * it has a ProgressBar which goes forward parallel with playing music.
     * and has a two label revealing current time and total time of music.
     *
     * @author Soroush Mehraban & Morteza Damghani
     * @version 1.0
     */
    private class ProgressPanel extends JPanel{
        private JProgressBar musicPlayerBar;
        private JLabel currentTimeLabel;
        private JLabel totalTimeLabel;

        /**
         * Class Constructor.
         */
        public ProgressPanel() {
            //setting layout to Box layout and Line axis:
            this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
            //setting background:
            this.setBackground(Color.getHSBColor(0, 0, 0.16f));
            //creating time labels:
            currentTimeLabel = new JLabel("2:15");
            currentTimeLabel.setForeground(Color.getHSBColor(0, 0, 0.7f));
            totalTimeLabel = new JLabel("4:07");
            totalTimeLabel.setForeground(Color.getHSBColor(0, 0, 0.7f));
            //customizing progress bar colors:
            UIManager.put("ProgressBar.background", Color.getHSBColor(0, 0.09f, 0.36f));
            UIManager.put("ProgressBar.foreground", Color.getHSBColor(0, 0, 0.7f));
            //creating music player bar:
            musicPlayerBar = new JProgressBar();
            musicPlayerBar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
            musicPlayerBar.setPreferredSize(new Dimension(430, 7));
            musicPlayerBar.setMaximumSize(musicPlayerBar.getPreferredSize());
            musicPlayerBar.setMinimumSize(musicPlayerBar.getPreferredSize());
            musicPlayerBar.setBackground(Color.getHSBColor(0, 0.09f, 0.36f));
            //adding components to pannel:
            this.add(currentTimeLabel);
            this.add(Box.createHorizontalStrut(10));
            this.add(musicPlayerBar);
            createMusicPlayerBarAction();
            this.add(Box.createHorizontalStrut(10));
            this.add(totalTimeLabel);
        }

        /**
         * This method demonstrate what happens if mouse pressed or dragged on music player bar.
         * when mouse pressed: it changes music to time where user clicks.
         * when mouse dragged: it drags progress bar with mouse and after that,plays music from that point.
         */
        private void createMusicPlayerBarAction(){
            musicPlayerBar.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    musicPlayerBar.setValue((int) (e.getX() / ((double) musicPlayerBar.getWidth()) * 100));
                }
            });
            musicPlayerBar.addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    musicPlayerBar.setValue((int) (e.getX() / ((double) musicPlayerBar.getWidth()) * 100));
                }
            });

        }
    }
    private class EastPartPanel extends JPanel{
        private JProgressBar soundBar;
        private JProgressBar sound;
        private BufferedImage soundImageOff;
        private BufferedImage soundImageMed;
        private BufferedImage soundImageHigh;
        private JLabel soundLabel;

        public EastPartPanel() throws IOException {
            //setting layout to Box layout and Line axis:
            this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
            //setting background:
            this.setBackground(Color.getHSBColor(0, 0, 0.16f));
            //loading icons:
            soundImageOff = ImageIO.read(new File("Icons/No-Sound-no-select.png"));
            soundImageMed = ImageIO.read(new File("Icons/Med-Sound-no-select.png"));
            soundImageHigh = ImageIO.read(new File("Icons/High-Sound-no-select.png"));
            //creating soundlabel:
            soundLabel = new JLabel(new ImageIcon(soundImageMed));
            //creating soundBar:
            soundBar = new JProgressBar();
            soundBar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
            soundBar.setPreferredSize(new Dimension(30, 7));
            soundBar.setMaximumSize(soundBar.getPreferredSize());
            soundBar.setMinimumSize(soundBar.getPreferredSize());
            soundBar.setValue(50);
            //adding components to panel:
            this.add(soundLabel);
            this.add(Box.createHorizontalStrut(5));
            this.add(soundBar);
            this.add(Box.createHorizontalStrut(20));
        }
    }
}