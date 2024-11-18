package org.isaac.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyLogger {
    private final String CLASS_NAME;

    public MyLogger(Class<?> Class) {
        this.CLASS_NAME = Class.getName();
    }

    private static String formatDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        return dateFormat.format(new Date());
    }

    public void log(String level, String message) {
        String timestamp = MyLogger.formatDate();
        System.out.println(String.format("%s [%s] [%s] %s", timestamp, level, CLASS_NAME, message));
    }

    public void info(String message) {
        this.log("INFO", message);
    }

    public void warn(String message) {
        this.log("WARN", message);
    }

    public void error(String message) {
        this.log("ERROR", message);
    }

    public void debug(String message) {
        this.log("DEBUG", message);
    }
}

