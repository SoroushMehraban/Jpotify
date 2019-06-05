import javax.swing.*;
import java.awt.*;

/**
 * the window GUI where User can play a music.
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
public class GUIFrame extends JFrame {
    private static GUIFrame guiFrame;
    /**
     * Class Constructor
     */
    private GUIFrame() {
        this.setLayout(new BorderLayout()); //frame layout
        this.setSize(940,512); //frame length : 940 * 512
        this.setLocationRelativeTo(null); //setting frame at the center of screen
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closing the program when user close the window.
    }
    public static GUIFrame getInstance(){
        if(guiFrame == null)
            guiFrame = new GUIFrame();
        return guiFrame;
    }

    public static void reload(){
        guiFrame.repaint();
        guiFrame.revalidate();
    }
}
