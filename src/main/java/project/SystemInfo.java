package project;

import java.util.prefs.Preferences;

public class SystemInfo {
    public static Preferences pref = Preferences.userNodeForPackage(project.SystemInfo.class);

    public static String javaVersion() {
        return System.getProperty("java.version");
    }

    public static String javafxVersion() {
        return System.getProperty("javafx.version");
    }

}