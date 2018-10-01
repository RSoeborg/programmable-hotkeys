import com.tulskiy.keymaster.common.HotKey;
import com.tulskiy.keymaster.common.Provider;

import javax.swing.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HotkeyWindow {
    private JFrame frame;
    private Provider provider;
    private HashMap<Integer, String> keyMap;

    public void HotkeyWindow() {
        provider = Provider.getCurrentProvider(false);
        keyMap = new HashMap<>();

        frame = new JFrame("FooRendererTest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.pack();
        frame.setSize(250,65);
        frame.setAlwaysOnTop(true);
        frame.setLocationRelativeTo(null);

        //provider = Provider.getCurrentProvider(false);
        //keyMap = new HashMap<>();
    }

    public void mapKeyTo(String key, String value) {
        keyMap.put(KeyStroke.getKeyStroke(key).getKeyCode(), value);
        System.out.println(KeyStroke.getKeyStroke(key).getKeyCode());
    }

    public void show() {
        frame.setVisible(true);

        Iterator it = keyMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            provider.register(KeyStroke.getKeyStroke((int)pair.getKey(), 0), hotKey -> {
                System.out.println(keyMap.get(hotKey.keyStroke.getKeyCode()));


            });

            it.remove(); // avoids a ConcurrentModificationException
        }
    }

    public void isIdle() {}

    private void close() {
        provider.reset();
        frame.setVisible(false);
    }
}
