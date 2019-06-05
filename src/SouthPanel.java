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
    private JProgressBar musicPlayerBar;
    private JProgressBar soundBar;
    private JPanel westPart;
    private JPanel eastPart;
    private JPanel centerPart;
    private JPanel progressPanel;
    private JPanel playPanel;

    public SouthPanel() throws IOException {
        this.setLayout(new BorderLayout());
        //this.add(new Button("button"),BorderLayout.CENTER);
        this.setBackground(Color.getHSBColor(0,0,0.16f));

        centerPart = new JPanel();
        centerPart.setLayout(new BoxLayout(centerPart,BoxLayout.PAGE_AXIS));
        centerPart.setBackground(Color.getHSBColor(0,0,0.16f));
        this.add(centerPart,BorderLayout.CENTER);

        westPart = new JPanel();
        //westPart.add(new Button("Hi"));
        //this.add(westPart,BorderLayout.WEST);

        eastPart = new JPanel();
        eastPart.add(new Button("Hi2"));
        //this.add(eastPart,BorderLayout.EAST);

        playPanel = new JPanel();
        playPanel.setBackground(Color.getHSBColor(0,0,0.16f));
        BufferedImage myPicture = ImageIO.read(new File("Play.png"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        playPanel.add(picLabel);
        centerPart.add(playPanel);

        progressPanel = new JPanel();
        progressPanel.setLayout(new BoxLayout(progressPanel,BoxLayout.LINE_AXIS));
        progressPanel.setBackground(Color.getHSBColor(0,0,0.16f));
        centerPart.add(progressPanel);

        JLabel currentTimeLabel = new JLabel("2:15");
        currentTimeLabel.setForeground(Color.getHSBColor(0,0,0.7f));
        JLabel totalTimeLabel = new JLabel("4:07");
        totalTimeLabel.setForeground(Color.getHSBColor(0,0,0.7f));

        UIManager.put("ProgressBar.background", Color.getHSBColor(0,0.09f,0.36f));
        UIManager.put("ProgressBar.foreground", Color.getHSBColor(0,0,0.7f));

        musicPlayerBar = new JProgressBar();
        musicPlayerBar.setAlignmentX(Component.CENTER_ALIGNMENT);
        musicPlayerBar.setPreferredSize(new Dimension(430,7));
        musicPlayerBar.setMaximumSize(musicPlayerBar.getPreferredSize());
        musicPlayerBar.setMinimumSize(musicPlayerBar.getPreferredSize());
        musicPlayerBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                musicPlayerBar.setValue((int) (e.getX()/((double)musicPlayerBar.getWidth()) * 100));
            }
        });
        musicPlayerBar.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                musicPlayerBar.setValue((int) (e.getX()/((double)musicPlayerBar.getWidth()) * 100));
            }
        });

        progressPanel.add(currentTimeLabel);
        progressPanel.add(Box.createHorizontalStrut(10));
        progressPanel.add(musicPlayerBar);
        progressPanel.add(Box.createHorizontalStrut(10));
        progressPanel.add(totalTimeLabel);


        soundBar = new JProgressBar();
        soundBar.setSize(new Dimension(50,7));
        soundBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                soundBar.setValue(e.getX()*2);
            }
        });
        soundBar.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                soundBar.setValue(e.getX()*2);
            }
        });

        /*this.add(musicPlayerBar);
        this.add(soundBar);*/
    }

}