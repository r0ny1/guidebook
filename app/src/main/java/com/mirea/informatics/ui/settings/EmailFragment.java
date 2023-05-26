package com.mirea.informatics.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.PreferenceFragmentCompat;

import com.mirea.informatics.R;

public class EmailFragment extends Fragment {

    private EditText oldemail_text;
    private EditText newemail_text;
    private EditText password_text;

    boolean EMAIL_SUCCESS = false;
    String[] email_data = {"",""};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_email, container, false);
        Button pass_btn = (Button) root.findViewById(R.id.mail_btn);
        pass_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeEmail(v);
            }
        });
        return root;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        password_text = (EditText) getView().findViewById(R.id.input_email);
        oldemail_text = (EditText) getView().findViewById(R.id.input_oldmail);
        newemail_text = (EditText) getView().findViewById(R.id.input_newmail);
    }

    public void changeEmail(View view){

        email_data[0] = oldemail_text.getText().toString();
        email_data[1] = newemail_text.getText().toString();

        if (!validate()) {
            onEmailFailed();
            return;
        }
        final ConstraintLayout hiddenLayout = getActivity().findViewById(R.id.verification_container);
        hiddenLayout.setVisibility(view.VISIBLE);

        sendEmailData();

        if (EMAIL_SUCCESS) { onEmailSuccess();} else onEmailFailed();
    }

    public void sendEmailData(){

    }

    public void onEmailSuccess() {
            Toast.makeText(getContext(), getText(R.string.email_success), Toast.LENGTH_LONG).show();
            NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_emailFragment_to_navigation_settings);
    }

    public void onEmailFailed() {
        Toast.makeText(getContext(), getText(R.string.password_failed), Toast.LENGTH_LONG).show();
    }

    public boolean validate() {
        boolean valid = true;

        SharedPreferences sharedPref = getContext().getSharedPreferences("com.mirea.informatics_preferences", Context.MODE_PRIVATE);

        if (!password_text.equals(sharedPref.getString("password","not set"))) {
            password_text.setError(getText(R.string.enter_valid_password));
        }

        if (email_data[0].isEmpty() || !email_data[0].equals(sharedPref.getString("email", "not set")) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email_data[0]).matches()) {
            oldemail_text.setError(getText(R.string.enter_valid_email));
            valid = false;
        } else {
            oldemail_text.setError(null);
        }

        if (email_data[1].isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email_data[1]).matches()) {
            newemail_text.setError(getText(R.string.enter_valid_email));
            valid = false;
        } else {
            newemail_text.setError(null);
        }

        if (email_data[1].equals(email_data[0])) {
            newemail_text.setError(getText(R.string.oldemail_newemail));
            valid = false;
        }

        return valid;
    }
}

