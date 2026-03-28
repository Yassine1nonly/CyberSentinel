package com.socmonitor.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF = "SOCSession";
    private static final String KEY_LOGGED = "loggedIn";
    private static final String KEY_EMAIL  = "email";
    private static final String KEY_ROLE   = "role";

    private final SharedPreferences prefs;

    public SessionManager(Context ctx) {
        prefs = ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE);
    }

    public void login(String email, String role) {
        prefs.edit().putBoolean(KEY_LOGGED, true)
                .putString(KEY_EMAIL, email)
                .putString(KEY_ROLE, role).apply();
    }

    public boolean isLoggedIn() { return prefs.getBoolean(KEY_LOGGED, false); }
    public String getEmail()    { return prefs.getString(KEY_EMAIL, ""); }
    public String getRole()     { return prefs.getString(KEY_ROLE, "Analyst"); }

    public void logout() { prefs.edit().clear().apply(); }
}
