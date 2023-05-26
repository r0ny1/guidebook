package com.mirea.informatics.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mirea.informatics.MainActivity;
import com.mirea.informatics.R;
import com.mirea.informatics.stats.Userdata;

public class LoginFragment extends Fragment {

    Userdata Userdata;

    private boolean LOGIN_SUCCESS = true;
    private boolean valid;
    private TextView register_activity;
    private EditText mail, password;
    private Button login_btn;
    private SharedPreferences SharedPref;
    private SharedPreferences.Editor SharedPrefEditor;
    private String mail_str, password_str;
    private ProgressBar loadingProgressBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_login, container, false);
        return root;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mail = view.findViewById(R.id.mail);
        password = view.findViewById(R.id.password);
        SharedPref = this.getActivity().getSharedPreferences("com.mirea.informatics_preferences",Context.MODE_PRIVATE);
        loadingProgressBar = view.findViewById(R.id.loading);
        login_btn = view.findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                login(v);
            }
        });

        register_activity = view.findViewById(R.id.link_register);
        register_activity.setOnClickListener(new TextView.OnClickListener(){
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });
    }


    //Начало логина
    public void login(View view) {
        login_btn.setEnabled(false);
        mail.setEnabled(false);
        password.setEnabled(false);
        mail_str = mail.getText().toString();
        password_str = password.getText().toString();

        if (!validate()) {
            onLoginFailed();
            return;
        }

        loadingProgressBar.setVisibility(view.VISIBLE);
        // TODO: Процесс логина после проверки ввода данных
        sendLoginData();

        loadingProgressBar.setVisibility(View.GONE);
        if (LOGIN_SUCCESS) { onLoginSuccess(); } else onLoginFailed();
    }

    public void sendLoginData(){
        
    }

    //Логика при успешнов входе
    public void onLoginSuccess() {
        //Userdata.setLogin(password_str,mail_str);
        SharedPrefEditor = SharedPref.edit();
        SharedPrefEditor.putString("password",password_str);
        SharedPrefEditor.putString("name","Долидзе Илона Ильинична");
        SharedPrefEditor.putString("group","ИВБО-04-19");
        SharedPrefEditor.putString("email",mail_str);
        SharedPrefEditor.apply();
        NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_loginFragment_to_navigation_home);
    }

    //Вывод сообщения об ошибке ввода
    public void onLoginFailed() {
        Toast.makeText(getContext(),getText(R.string.login_failed) , Toast.LENGTH_LONG).show();
        login_btn.setEnabled(true);
        mail.setEnabled(true);
        password.setEnabled(true);
    }

    //Проверка на тупость ввода данных
    public boolean validate() {
        valid = true;

        if (mail_str.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(mail_str).matches()) {
            mail.setError(getText(R.string.enter_valid_email));
            valid = false;
        } else {
            mail.setError(null);
        }

        if (password_str.isEmpty() || password.length() < 4 || password.length() > 16) {
            password.setError(getText(R.string.enter_valid_password));
            valid = false;
        } else {
            password.setError(null);
        }
        return valid;
    }
}
