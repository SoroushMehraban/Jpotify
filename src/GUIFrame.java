import javax.swing.*;
import java.awt.*;

/**
 * the window GUI where User can play a music.
 * when user run this program, its only create one instance of this class, so it's a singleton class.
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

    /**
     * getting instance of class.
     * @return a unique GUIFrame object.
     */
    public static GUIFrame getInstance(){
        if(guiFrame == null)
            guiFrame = new GUIFrame();
        return guiFrame;
    }

    /**
     * reloading GUIFrame when components changes.
     */
    public static void reload(){
        guiFrame.repaint();
        guiFrame.revalidate();
    }
}
