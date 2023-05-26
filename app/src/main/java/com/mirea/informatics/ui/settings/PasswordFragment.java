package com.mirea.informatics.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.PreferenceFragmentCompat;

import com.mirea.informatics.R;

import java.util.regex.Pattern;

public class PasswordFragment extends Fragment {
    int PASSWORD_SUCCESS = 1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_password, container, false);
        Button pass_btn = (Button) root.findViewById(R.id.password_btn);
        pass_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword(v);
            }
        });

        return root;
    }

    public void changePassword(View view){
        if (!validate()) {
        onPasswordFailed();
        return;
    }

    final ProgressBar loadingProgressBar = getActivity().findViewById(R.id.loading);
        loadingProgressBar.setVisibility(view.VISIBLE);

        new android.os.Handler().postDelayed(
                new Runnable() {
        public void run() {
            loadingProgressBar.setVisibility(View.GONE);
            onPasswordSuccess();
        }
    }, 3000);
}

    public void onPasswordSuccess() {
        //Отправка и проверка данных на сервере
        //
        //
        if (PASSWORD_SUCCESS == 1) {
            Toast.makeText(getContext(), getText(R.string.password_success), Toast.LENGTH_LONG).show();
            NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_passwordFragment_to_navigation_settings);
        } else {onPasswordFailed();}
    }

    public void onPasswordFailed() {
        Toast.makeText(getContext(), getText(R.string.password_failed), Toast.LENGTH_LONG).show();
    }

    public boolean validate() {
        boolean valid = true;

        EditText oldpass_text = getActivity().findViewById(R.id.input_oldpassword);
        EditText newpass_text = getActivity().findViewById(R.id.input_password);
        EditText newrepass_text = getActivity().findViewById(R.id.input_repassword);

        String oldpass = oldpass_text.getText().toString();
        String newpass = newpass_text.getText().toString();
        String newrepass = newrepass_text.getText().toString();


        if (oldpass.isEmpty() || oldpass.length() < 4 || oldpass.length() > 16) {
            oldpass_text.setError(getText(R.string.enter_valid_password));
            valid = false;
        } else {
            oldpass_text.setError(null);
        }

        if (newpass.equals(oldpass)) {
            newpass_text.setError(getText(R.string.oldpass_newpass));
            valid = false;
        } else if (newpass.isEmpty() || newpass.length() < 4 || newpass.length() > 16) {
                newpass_text.setError(getText(R.string.enter_valid_password));
                newpass_text.setText(null);
                valid = false;
            } else {
                newpass_text.setError(null);
            }

        if (newrepass.isEmpty() || newrepass.length() < 4 || newrepass.length() > 16 || !newrepass.equals(newpass)) {
            newrepass_text.setError(getText(R.string.enter_valid_password));
            valid = false;
        } else {
            newrepass_text.setError(null);
        }
        return valid;
    }
}
