import com.GUIFrame.GUIFrame;
import com.MP3.MP3Info;

import java.io.IOException;

/**
 * Main class, where program executes.
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) throws IOException {
        /*try {
            GUIFrame frame = GUIFrame.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        MP3Info mp3Info = new MP3Info("Soroush Tabarsi - Grey (Ft Seventh Soul) [128].mp3");
    }
}
