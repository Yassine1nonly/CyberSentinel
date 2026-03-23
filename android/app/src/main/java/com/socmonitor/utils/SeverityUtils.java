package com.socmonitor.utils;

import android.content.Context;
import androidx.core.content.ContextCompat;
import com.socmonitor.R;

public class SeverityUtils {

    public static int getColor(Context ctx, String severity) {
        if (severity == null) return ContextCompat.getColor(ctx, R.color.severity_info);
        switch (severity.toUpperCase()) {
            case "CRITICAL": return ContextCompat.getColor(ctx, R.color.severity_critical);
            case "HIGH":     return ContextCompat.getColor(ctx, R.color.severity_high);
            case "MEDIUM":   return ContextCompat.getColor(ctx, R.color.severity_medium);
            case "LOW":      return ContextCompat.getColor(ctx, R.color.severity_low);
            default:         return ContextCompat.getColor(ctx, R.color.severity_info);
        }
    }

    public static String getEmoji(String severity) {
        if (severity == null) return "ℹ️";
        switch (severity.toUpperCase()) {
            case "CRITICAL": return "🔴";
            case "HIGH":     return "🟠";
            case "MEDIUM":   return "🟡";
            case "LOW":      return "🟢";
            default:         return "🔵";
        }
    }
}
