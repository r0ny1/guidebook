package com.mirea.informatics.stats;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;


public class Userdata extends Fragment {
    String email;
    String password;
    String status;

    Userdata(){

    }

    public void setLogin(String Email, String Password){
        this.email = Email;
        this.password = Password;
    }

    public void sendLogin(String email, String password){
        //TODO Отправка данных логина
    }

    public void sendReg(String email, String password, String group){
        //TODO Отправка данных регистрации
    }

    private void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}
