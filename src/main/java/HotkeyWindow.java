import com.tulskiy.keymaster.common.Provider;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HotkeyWindow {
    private JFrame frame;
    private Provider provider;
    private HashMap<Integer, String> keyMap;

    HotkeyWindow(HotkeyLoader loader) {
        provider = Provider.getCurrentProvider(false);
        keyMap = new HashMap<>();

        frame = new JFrame("FooRendererTest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.getContentPane().setLayout(new FlowLayout());

        List<String> map = loader.getMap();
        for (int i = 0; i < map.size(); i++) {
            createMapEvent(i + 1, map.get(i), loader.getReplacementText(i));
        }

        /*
        Button newButton = new Button("new...");
        newButton.addActionListener(l -> {
            // Create dialog that asks for user input ...

            frame.pack();
        });

        frame.getContentPane().add(newButton);
        */

        int buttonRows = (int)(1+(map.size()-(map.size()%10.0))/10.0);
        int height = buttonRows * 28;
        int buttonWidth = 45;
        int buttonMargin = 5;

        frame.setPreferredSize(new Dimension(2*buttonMargin + (buttonMargin+buttonWidth)*9,  height));
        frame.pack();
        frame.setAlwaysOnTop(true);
        frame.setLocationRelativeTo(null);
    }

    public void show()  {
        Point location = MouseInfo.getPointerInfo().getLocation();
        int x = (int) location.getX();
        int y = (int) location.getY();
        frame.setLocation(x - frame.getWidth() / 2, y - frame.getHeight() / 2);
        frame.setVisible(true);

        provider.register(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), hotKey -> { close(); });

        Iterator it = keyMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            provider.register(KeyStroke.getKeyStroke((int)pair.getKey(), 0), hotKey -> {
                close();
                sendOutput(keyMap.get(hotKey.keyStroke.getKeyCode()));
            });
        }
    }

    private void createMapEvent(int buttonId, String map) { createMapEvent(buttonId, map, ""); }
    private void createMapEvent(int buttonId, String map, String replaceText) {
        String buttonText = !replaceText.equals("") ? replaceText : map;

        Button mapButton;
        if (buttonId <= 9) {
            mapButton = new Button(String.format("%s (%d)", buttonText, buttonId));
            keyMap.put(KeyStroke.getKeyStroke("" + buttonId).getKeyCode(), map);
        } else {
            mapButton = new Button(buttonText);
        }

        mapButton.setPreferredSize(new Dimension(45, 20));

        mapButton.addActionListener(l->{
            close();
            sendOutput(map);
        });


        frame.getContentPane().add(mapButton);
    }

    private void sendOutput(String toOutput){
        frame.setVisible(false);//reforce focus into last application.

        StringSelection stringSelection = new StringSelection(toOutput);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);

        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
        } catch (AWTException awtException) {
            System.out.println("awtException");
        }
    }

    private void close() {
        provider.reset();
        frame.setVisible(false);
    }
}
