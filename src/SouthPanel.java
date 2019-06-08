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
    private WestPartPanel westPart;
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

        westPart = new WestPartPanel();
        this.add(westPart,BorderLayout.WEST);

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

    /**
     * This class is about east part of south panel
     * it has a Sound Bar where user can change volume of music
     *
     * @author Soroush Mehraban & Morteza Damghani
     * @version 1.0
     */
    private class EastPartPanel extends JPanel{
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

    /**
     * This inner class is about west part panel of SouthPanel for Jpotify.
     * it shows us 2 things: Song Name and Artist Name.
     *
     * @author Soroush Mehraban & Morteza Damghani
     * @version 1.0
     */
    private class WestPartPanel extends JPanel{
        private JLabel songName;
        private JLabel artistName;
        private BufferedImage plusIcon;
        private JLabel plusLabel;

        /**
         * Class Constructor.
         * @throws IOException if opening plus icon failed.
         */
        public WestPartPanel() throws IOException {
            //setting layout to Box layout and Line axis:
            this.setLayout(new GridBagLayout());
            GridBagConstraints constraints = new GridBagConstraints();
            //setting background:
            this.setBackground(Color.getHSBColor(0, 0, 0.16f));
            //creating song name label:
            songName = new JLabel("Song Name");
            songName.setForeground(Color.getHSBColor(0, 0, 0.7f));
            //creating artist name label:
            artistName = new JLabel("Artist Name");
            artistName.setForeground(Color.getHSBColor(0, 0.09f, 0.36f));
            //creating plus label with icon:
            plusIcon = ImageIO.read(new File("Icons/Plus-no-select.png"));
            plusLabel = new JLabel(new ImageIcon(plusIcon));
            createplusLabelAction();
            //putting components in panel:
            constraints.fill = GridBagConstraints.BOTH;
            constraints.insets = new Insets(5,5,0,0);
            constraints.gridx = 0;
            constraints.gridy = 0;
            this.add(songName, constraints);
            constraints.gridx = 1;
            constraints.gridy = 0;
            this.add(plusLabel,constraints);
            constraints.insets = new Insets(0,5,0,0);
            constraints.gridx = 0;
            constraints.gridy = 1;
            this.add(artistName,constraints);
        }
        /**
         * This method demonstrate what happens if mouse pressed,entered or exited from plus Icon beside Song name.
         * when mouse pressed: ...
         * when mouse entered: it made that icon look brighter.
         * when mouse exited: it changes to previous form.
         */
        private void createplusLabelAction(){
            plusLabel.addMouseListener(new MouseAdapter(
            ) {
                /*@Override
                public void mousePressed(MouseEvent e) {
                    changePlayLabel(pauseLabel);
                }*/

                @Override
                public void mouseEntered(MouseEvent e) {
                    try {
                        plusIcon = ImageIO.read(new File("Icons/Plus.png"));
                        plusLabel.setIcon(new ImageIcon(plusIcon));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    try {
                        plusIcon = ImageIO.read(new File("Icons/Plus-no-select.png"));
                        plusLabel.setIcon(new ImageIcon(plusIcon));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                }
            });
        }
    }
}