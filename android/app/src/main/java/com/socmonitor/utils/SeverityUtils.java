package com.socmonitor.utils;

import android.graphics.Color;

public class SeverityUtils {

    public static int getColor(String severity) {
        if (severity == null) return Color.parseColor("#1565C0");
        switch (severity.toUpperCase()) {
            case "CRITICAL": return Color.parseColor("#B71C1C");
            case "HIGH":     return Color.parseColor("#E65100");
            case "MEDIUM":   return Color.parseColor("#F57F17");
            case "LOW":      return Color.parseColor("#2E7D32");
            default:         return Color.parseColor("#1565C0");
        }
    }

    public static String getEmoji(String severity) {
        if (severity == null) return "[INFO]";
        switch (severity.toUpperCase()) {
            case "CRITICAL": return "[CRIT]";
            case "HIGH":     return "[HIGH]";
            case "MEDIUM":   return "[MED]";
            case "LOW":      return "[LOW]";
            default:         return "[INFO]";
        }
    }

    public static int getStatusColor(String status) {
        if (status == null) return Color.parseColor("#EF5350");
        switch (status.toUpperCase()) {
            case "ACKNOWLEDGED": return Color.parseColor("#FFB300");
            case "RESOLVED":     return Color.parseColor("#66BB6A");
            default:             return Color.parseColor("#EF5350");
        }
    }
}
