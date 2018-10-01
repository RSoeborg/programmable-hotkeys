import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinUser.GUITHREADINFO;

import java.awt.*;

/*
    [DllImport("user32.dll")]
    static extern int GetWindowText(int hWnd, StringBuilder text, int count);

    [DllImport("user32.dll", SetLastError = true)]
    static extern uint GetWindowThreadProcessId(int hWnd, out uint lpdwProcessId);

    [DllImport("user32.dll", EntryPoint = "GetGUIThreadInfo")]
    public static extern bool GetGUIThreadInfo(uint tId, out GUITHREADINFO threadInfo);

    [DllImport("user32.dll")]
    public static extern IntPtr GetForegroundWindow();

    [DllImport("user32.dll")]
    public static extern bool ClientToScreen(IntPtr hWnd, out Point position);
*/

public class WindowsInterop {
    public WindowsInterop() {
        if (!Platform.isWindows()) { throw new PlatformNotSupportedException(); }
    }

    public Point getCaretPosition() {
        GUITHREADINFO guithreadinfo = new GUITHREADINFO();
        User32.INSTANCE.GetGUIThreadInfo(0, guithreadinfo);

        WinUser.POINT p = new WinUser.POINT(guithreadinfo.rcCaret.left, guithreadinfo.rcCaret.top);
        //User32Ext.INSTANCE.ClientToScreen( guithreadinfo.hwndCaret, p);

        return new Point(p.x, p.y);
    }

    public Point getCursorPosition() {
        WinUser.POINT p = new WinUser.POINT(0, 0);
        User32Ext.INSTANCE.GetCursorPos(p);
        return new Point(p.x, p.y);
    }

}
