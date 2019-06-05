import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Main class, where program executes.
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {
        GUIFrame frame = GUIFrame.getInstance();
        SouthPanel southPanel = null;
        try {
            southPanel = new SouthPanel();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //southPanel.setBounds(200,200,500,200);
        frame.add(southPanel,BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
