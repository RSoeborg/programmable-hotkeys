public class PlatformNotSupportedException extends RuntimeException {
    public PlatformNotSupportedException(String message) {
        super(message);
    }
    public  PlatformNotSupportedException() {
        super("This platform is not supported");
    }
}
