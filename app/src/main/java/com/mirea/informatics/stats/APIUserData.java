package com.mirea.informatics.stats;

import android.content.SharedPreferences;

import com.mirea.informatics.MainActivity;

public class APIUserData {
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_USER = "name";
    private static final String KEY_GROUP = "group";
    private static Boolean REG_SUCCES = false;
    private static Boolean LOGIN_SUCCES = false;

    private SharedPreferences preferences;

    public APIUserData() {}

    public void setLoginState(Boolean state){
        LOGIN_SUCCES = state;
    }

    public boolean getLoginState(){
        return LOGIN_SUCCES;
    }

    public void setRegState(Boolean state){
        REG_SUCCES = state;
    }

    public boolean getRegState(){
        return REG_SUCCES;
    }

    public void setUserData(String email, String password, String name, String group) {
        MainActivity.preferences.edit().putString(KEY_EMAIL, email).commit();
        MainActivity.preferences.edit().putString(KEY_PASSWORD, password).commit();
        MainActivity.preferences.edit().putString(KEY_USER, name).commit();
        MainActivity.preferences.edit().putString(KEY_GROUP, group).commit();
    }

    public String[] returnUserData(){
        String[] user = new String[4];
        user[0] = MainActivity.preferences.getString(KEY_EMAIL, null);
        user[1] = MainActivity.preferences.getString(KEY_PASSWORD, null);
        user[2] = MainActivity.preferences.getString(KEY_USER, null);
        user[3] = MainActivity.preferences.getString(KEY_GROUP, null);
        return user;
    }

    public void eraseAll(){
        MainActivity.preferences.edit().clear().apply();
    }

    public void setEmail(String email) {
        MainActivity.preferences.edit().putString(KEY_EMAIL, email).commit();
    }

    public void setPassword(String password) {
        MainActivity.preferences.edit().putString(KEY_PASSWORD, password).commit();
    }

    public void setName(String name) {
        MainActivity.preferences.edit().putString(KEY_USER, name).commit();
    }

    public void setGroup(String group) {
        MainActivity.preferences.edit().putString(KEY_GROUP, group).commit();
    }

    public String getUser() {
        return MainActivity.preferences.getString(KEY_EMAIL, null);
    }

    public String getPassword() {
        return MainActivity.preferences.getString(KEY_PASSWORD, null);
    }

    public String getName() {
        return MainActivity.preferences.getString(KEY_USER, null);
    }

    public String getGroup() {
        return MainActivity.preferences.getString(KEY_GROUP, null);
    }
}
