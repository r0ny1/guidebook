package com.mirea.informatics.ui.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mirea.informatics.R;

import java.util.Calendar;

public class HomeFragment extends Fragment {

    private TextView name;
    private TextView group;
    private TextView email;
    private TextView exam;
    private TextView week;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.bar, menu);
        return true;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = (TextView) getView().findViewById(R.id.studentName);
        email = (TextView) getView().findViewById(R.id.studentEmail);
        group = (TextView) getView().findViewById(R.id.studentGroup);
        exam = (TextView) getView().findViewById(R.id.text_exam);
        week = (TextView) getView().findViewById(R.id.text_week);
        set_exam();
        set_week();
        set_student_data();
    }

    public void set_student_data(){
        SharedPreferences sharedPref = getContext().getSharedPreferences("com.mirea.informatics_preferences",getContext().MODE_PRIVATE);
        name.setText(sharedPref.getString("name","Тебинов Никита Сергеевич"));
        email.setText(sharedPref.getString("email","nikita.tebinov@yandex.ru"));
        group.setText(sharedPref.getString("group","ИВБО-01-18"));
    }

    public void set_week(){
        Calendar calender = Calendar.getInstance();
        int current_week = calender.get(Calendar.WEEK_OF_YEAR) - 6;
        String week_data = getText(R.string.Week1)+" "+current_week;
        week.setText(week_data);
    }

    public void set_exam(){
        Calendar calender = Calendar.getInstance();
        int days_to_exam = 16+7 - calender.get(Calendar.WEEK_OF_YEAR);
        String exam_data =  getText(R.string.Exam)+" "+days_to_exam;
        exam.setText(exam_data);
    }
}
