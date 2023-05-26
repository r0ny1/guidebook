package com.mirea.informatics.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.mirea.informatics.R;
import com.mirea.informatics.ui.theme.ThemeHelper;

import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        logout_btn();
        email_btn();
        password_btn();
        email();
        theme_setup();
    }

    public void theme_setup(){
        ListPreference themePreference = findPreference("themePref");
        if (themePreference != null) {
            themePreference.setOnPreferenceChangeListener(
                    new Preference.OnPreferenceChangeListener() {
                        @Override
                        public boolean onPreferenceChange(Preference preference, Object newValue) {
                            String themeOption = (String) newValue;
                            ThemeHelper.applyTheme(themeOption);
                            return true;
                        }
                    });
        }
    }

    public void logout_btn(){
        Preference preference = findPreference("logout_btn");
        preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                logout();
                return true;
            }
        });
    }

    public void logout(){
        SharedPreferences sharedPref = getContext().getSharedPreferences("com.mirea.informatics_preferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("auto_login",false);
        editor.clear().commit();
        editor.apply();
        NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_navigation_settings_to_loginFragment);
    }

    public void email_btn(){
        Preference preference = findPreference("email");
        preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_navigation_settings_to_emailFragment);
                //email();
                return true;
            }
        });
    }

    public void password_btn(){
        Preference preference = findPreference("password_btn");
        preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_navigation_settings_to_passwordFragment);
                return true;
            }
        });
    }

    public void email(){
        SharedPreferences sharedPref = getContext().getSharedPreferences("com.mirea.informatics_preferences",Context.MODE_PRIVATE);
        Preference preference1 = (Preference) findPreference("email");
        String email = sharedPref.getString("email","ivbo0118@gmail.com");
        preference1.setSummary(email);
    }
}
