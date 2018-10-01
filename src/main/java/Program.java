import com.sun.jna.Platform;
import com.tulskiy.keymaster.common.*;
import javax.swing.*;
import java.awt.*;

public class Program {
    public static void main(String[] args) {
        if (!Platform.isWindows()) {
            System.out.println("This program only supports windows!");
            return;
        }

        HotkeyWindow hotkeyWindow = new HotkeyWindow();
        hotkeyWindow.mapKeyTo("A", "b");

        Provider provider = Provider.getCurrentProvider(false);
        provider.register(KeyStroke.getKeyStroke("F3"), hotKey -> {
            hotkeyWindow.show();
        });

        try {
            while (true) {/*event loop*/
                Thread.sleep(500);
                hotkeyWindow.isIdle();
            }
        } catch (InterruptedException ie) {

        }
    }
}
