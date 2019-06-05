import javax.swing.*;
import java.awt.*;

/**
 * the window GUI where User can play a music.
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
public class GUIFrame {
    private JFrame jFrame;

    /**
     * Class Constructor
     */
    public GUIFrame() {
        JFrame frame = new JFrame(); //creating JFrame
        frame.setLayout(new BorderLayout()); //frame layout
        frame.setSize(940,512); //frame length : 940 * 512
        frame.setLocationRelativeTo(null); //setting frame at the center of screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closing the program when user close the window.
    }
}
