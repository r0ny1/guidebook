package com.mirea.informatics;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mirea.informatics.ui.book.BookFragment;
import com.mirea.informatics.ui.lab.LabFragment;
import com.mirea.informatics.ui.theme.ThemeHelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.ActivityNavigator;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity {
    String current_email;
    String current_name;
    String current_group;

    AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        //AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                //R.id.navigation_home, R.id.navigation_book, R.id.navigation_apps, R.id.navigation_settings).build();
        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bar, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //Выход из учётки
    public void logout(View view) {
        SharedPreferences sharedPref = this.getSharedPreferences("com.mirea.informatics_preferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("auto_login",false);
        editor.clear().commit();
        editor.apply();
    }

    private void hideBottomNavigationView(BottomNavigationView view) {
        view.clearAnimation();
        view.animate().translationY(view.getHeight()).setDuration(300);
    }

    public void showBottomNavigationView(BottomNavigationView view) {
        view.clearAnimation();
        view.animate().translationY(0).setDuration(300);
    }

    @Override
    public void finish() {
        super.finish();
        ActivityNavigator.applyPopAnimationsToPendingTransition(this);
    }

/*    @Override
    public void onBackPressed(){
        if(LabFragment.myWebView.canGoBack()){
            LabFragment.myWebView.goBack();
        } else{
            super.onBackPressed();
        }

        if(BookFragment.myWebView.canGoBack()){
            BookFragment.myWebView.goBack();
        } else{
            super.onBackPressed();
        }
    }*/
}