package com.socmonitor.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME    = "SOCSession";
    private static final String KEY_LOGGED   = "isLoggedIn";
    private static final String KEY_EMAIL    = "userEmail";
    private static final String KEY_ROLE     = "userRole";

    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        prefs  = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void createLoginSession(String email, String role) {
        editor.putBoolean(KEY_LOGGED, true);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_ROLE, role);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_LOGGED, false);
    }

    public String getUserEmail() {
        return prefs.getString(KEY_EMAIL, "");
    }

    public String getUserRole() {
        return prefs.getString(KEY_ROLE, "Analyst");
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}
