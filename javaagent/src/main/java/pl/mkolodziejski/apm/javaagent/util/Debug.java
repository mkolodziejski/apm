package pl.mkolodziejski.apm.javaagent.util;

public class Debug {

    private static boolean isDebugMode = true;

    public static boolean isDebugMode() {
        return isDebugMode;
    }

    public static void setIsDebugMode(boolean isDebugMode) {
        Debug.isDebugMode = isDebugMode;
    }

    public static void print(String msg) {
        if(isDebugMode()) {
            System.out.println("[javaagent] " + msg);
        }
    }

    public static void dumpStacktrace(Exception e) {
        e.printStackTrace();
    }

}
