package com.mirea.informatics.ui.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mirea.informatics.R;

import java.util.regex.Pattern;
import com.mirea.informatics.stats.Userdata;
public class RegisterFragment extends Fragment {
    private boolean REG_SUCCESS = true;

    Userdata Userdata;

    private EditText mail, password, password2, name, group;
    private String name_str, group_str, password_str, password2_str, mail_str;
    private Button reg_btn;
    private ProgressBar loadingProgressBar;
    private TextView login_activity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_register, container, false);
        return root;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mail = view.findViewById(R.id.input_email);
        password = view.findViewById(R.id.reg_password);
        password2 = view.findViewById(R.id.reg_repassword);
        name = view.findViewById((R.id.input_name));
        group = view.findViewById((R.id.input_group));
        loadingProgressBar = view.findViewById(R.id.loading);
        reg_btn = view.findViewById(R.id.reg_btn);
        reg_btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                register(v);
            }
        });

        login_activity = view.findViewById(R.id.link_login);
        login_activity.setOnClickListener(new TextView.OnClickListener(){
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_registerFragment_to_loginFragment);
            }
        });
    }


    /*@Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }*/

    public void register(View view) {
        name_str = name.getText().toString();
        group_str = group.getText().toString();
        mail_str = mail.getText().toString();
        password_str = password.getText().toString();
        password2_str = password2.getText().toString();
        name.setEnabled(false);
        group.setEnabled(false);
        mail.setEnabled(false);
        password.setEnabled(false);
        password2.setEnabled(false);
        reg_btn.setEnabled(false);

        if (!validate()) {
            onRegFailed();
            return;
        }
        loadingProgressBar.setVisibility(View.VISIBLE);
        // TODO: Процесс регистарции после проверки ввода данных

        sendRegData();

        loadingProgressBar.setVisibility(View.GONE);
        if (REG_SUCCESS) { onRegSuccess(); } else onRegFailed();
    }

    private void sendRegData(){
        //Userdata.sendReg(mail_str, password_str, group_str);
        //Userdata.getStatus();
        // TODO: Отправка данных для регистрации на сервер
    }

    private void onRegSuccess() {
        Toast.makeText(getContext(), getText(R.string.signup_success), Toast.LENGTH_LONG).show();
    }

    private void onRegFailed() {
        Toast.makeText(getContext(), getText(R.string.signup_failed), Toast.LENGTH_LONG).show();
        reg_btn.setEnabled(true);
        mail.setEnabled(true);
        name.setEnabled(true);
        group.setEnabled(true);
        password.setEnabled(true);
        password2.setEnabled(true);
        reg_btn.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;

        if (name_str.isEmpty() || name_str.length() < 3||
                Pattern.compile("(\\w{1,}\\ \\w{1,}\\ \\w{1,})").matcher(name_str).matches() == false) {
            name.setError(getText(R.string.hint_name));
            valid = false;
        } else {
            name.setError(null);
        }

        if (mail_str.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(mail_str).matches()) {
            mail.setError(getText(R.string.enter_valid_email));
            valid = false;
        } else {
            mail.setError(null);
        }

        if (group_str.isEmpty() || !Pattern.compile("((\\w{4}\\-[0-9]{2}\\-[0-9]{2}))").matcher(group_str).matches()) {
            group.setError(getText(R.string.enter_valid_group));
            valid = false;
        } else {
            group.setError(null);
        }

        if (password_str.isEmpty() || password_str.length() < 4 || password_str.length() > 16) {
            password.setError(getText(R.string.enter_valid_password));
            valid = false;
        } else {
            password.setError(null);
        }

        if (password2_str.isEmpty() || password2_str.length() < 4 || password2_str.length() > 16) {
            password2.setError(getText(R.string.enter_valid_password));
            valid = false;
        } else {
            password2.setError(null);
        }

        if (!(password_str.equals(password2_str))){
            password.setError(getText(R.string.pass1_pass2));
            password2.setError(getText(R.string.pass1_pass2));
            valid = false;
        }

        System.out.println(password_str);
        System.out.println(password2_str);
        return valid;
    }
}
