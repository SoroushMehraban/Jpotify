import com.GUIFrame.GUIFrame;
import java.io.IOException;

/**
 * Main class, where program executes.
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
public class Main {
    public static void main(String[] args){
        try {
            GUIFrame frame = GUIFrame.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
