package shamboard;

public class SystemVars {
    private static final SystemVars INSTANCE = new SystemVars();

    public static SystemVars getInstance() {
        return INSTANCE;
    }

    public static boolean fade = false;
}
