import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HotkeyLoader {
    private List<String> mapTo;
    private List<String> replaceTextMap;

    HotkeyLoader(String path) throws IOException {
        mapTo = new ArrayList<>();
        replaceTextMap = new ArrayList<>();
        loadMap(path);
    }

    public List<String> getMap() { return mapTo; }
    public boolean hasReplacementText(int index) { return !replaceTextMap.get(index).equals(""); }
    public String getReplacementText(int index) { return replaceTextMap.get(index); }

    private void loadMap(String path) throws IOException {
        File f = new File(path);
        if(f.exists() && !f.isDirectory()) {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));

            String line;
            while ((line = in.readLine()) != null) {
                String[] lineParts = line.split(" ");
                if (lineParts.length > 1) {
                    replaceTextMap.add(lineParts[lineParts.length - 1]);
                    mapTo.add(String.join(" ", lineParts).substring(0, line.length() - lineParts[lineParts.length - 1].length() - 1));
                } else {
                    replaceTextMap.add("");
                    mapTo.add(line);
                }
            }

            in.close();
        }
    }
}
