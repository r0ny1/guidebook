package com.mirea.informatics.ui.apps;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import com.mirea.informatics.R;

public class AppsFragment extends Fragment {

    CardView solver_btn;
    CardView tests_btn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_apps, container, false);
        return root;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        solver_btn = view.findViewById(R.id.solver_btn);
        solver_btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_navigation_apps_to_solverFragment);
            }
        });
        tests_btn = view.findViewById(R.id.tests_btn);
        tests_btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_navigation_apps_to_testsFragment);
            }
        });
    }
}
