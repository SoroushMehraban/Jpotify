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
    private JPanel eastPart;
    private JPanel centerPart;
    private JPanel progressPanel;
    private JPanel playPanel;

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

        eastPart = new JPanel();
    }

    private class PlayPanel extends JPanel {
        private BufferedImage playImage;
        private BufferedImage pauseImage;
        private BufferedImage forwardImage;
        private BufferedImage backwardImage;
        private JLabel playLabel;
        private JLabel pauseLabel;
        private JLabel forwardLabel;
        private JLabel backwardLabel;

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
            this.add(Box.createHorizontalStrut(5));
            this.add(playLabel);
            this.add(Box.createHorizontalStrut(5));
            this.add(forwardLabel);
        }

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

        private void changePlayLabel(JLabel inputLabel) {
            playPanel.removeAll();
            playPanel.add(backwardLabel);
            playPanel.add(Box.createHorizontalStrut(5));
            playPanel.add(inputLabel);
            playPanel.add(Box.createHorizontalStrut(5));
            playPanel.add(forwardLabel);
            GUIFrame.reload();
        }

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
    private class ProgressPanel extends JPanel{
        private JProgressBar musicPlayerBar;
        private JLabel currentTimeLabel;
        private JLabel totalTimeLabel;

        public ProgressPanel() {
            this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
            this.setBackground(Color.getHSBColor(0, 0, 0.16f));

            currentTimeLabel = new JLabel("2:15");
            currentTimeLabel.setForeground(Color.getHSBColor(0, 0, 0.7f));
            totalTimeLabel = new JLabel("4:07");
            totalTimeLabel.setForeground(Color.getHSBColor(0, 0, 0.7f));

            UIManager.put("ProgressBar.background", Color.getHSBColor(0, 0.09f, 0.36f));
            UIManager.put("ProgressBar.foreground", Color.getHSBColor(0, 0, 0.7f));

            musicPlayerBar = new JProgressBar();
            musicPlayerBar.setAlignmentX(Component.CENTER_ALIGNMENT);
            musicPlayerBar.setPreferredSize(new Dimension(430, 7));
            musicPlayerBar.setMaximumSize(musicPlayerBar.getPreferredSize());
            musicPlayerBar.setMinimumSize(musicPlayerBar.getPreferredSize());

            this.add(currentTimeLabel);
            this.add(Box.createHorizontalStrut(10));
            this.add(musicPlayerBar);
            createMusicPlayerBarAction();
            this.add(Box.createHorizontalStrut(10));
            this.add(totalTimeLabel);
        }
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
}