import com.tulskiy.keymaster.common.*;
import javax.swing.*;
import java.io.IOException;

public class Program {
    public static void main(String[] args) throws IOException {
        Provider.logger.setUseParentHandlers(false);
        HotkeyLoader loader = new HotkeyLoader("keymap.txt");
        HotkeyWindow hotkeyWindow = new HotkeyWindow(loader);
        Provider provider = Provider.getCurrentProvider(false);

        provider.register(KeyStroke.getKeyStroke("shift A"), hotKey -> {
            hotkeyWindow.show();
        });
        System.out.println("[PHK]: Running");
    }
}
