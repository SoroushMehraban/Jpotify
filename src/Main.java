import com.GUIFrame.GUIFrame;

import javax.swing.*;

/**
 * Main class, where program executes.
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
            GUIFrame.getInstance();
        } catch (Exception ignored) {
            //ignoring...nothing is gonna happen just default theme is going to load.
        }
    }
}
