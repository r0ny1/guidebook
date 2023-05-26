package com.mirea.informatics.ui.startup;

import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.PreferenceManager;

import com.mirea.informatics.R;
import com.mirea.informatics.ui.theme.ThemeHelper;

public class StartupFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_startup, container, false);
        return root;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPref = getContext().getSharedPreferences("com.mirea.informatics_preferences",Context.MODE_PRIVATE);
                boolean autolog = sharedPref.getBoolean("auto_login",false);
                if (autolog == true) {
                    NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_startupFragment_to_navigation_home);
                } else{
                    NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_startupFragment_to_loginFragment);
                }
            }
        }, 2500);

    }
}
