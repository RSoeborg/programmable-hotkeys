import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.win32.W32APIOptions;

/* Small extension of User32 */
public interface User32Ext extends WinUser {
    /** The instance. */
    User32Ext INSTANCE = Native.loadLibrary("user32", User32Ext.class, W32APIOptions.DEFAULT_OPTIONS);

    boolean ClientToScreen(
            HWND hWnd,
            POINT lpPoint
    );

    boolean GetCursorPos(
        POINT lpPoint
    );
}
