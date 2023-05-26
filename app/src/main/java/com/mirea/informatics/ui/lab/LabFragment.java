package com.mirea.informatics.ui.lab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mirea.informatics.R;

public class LabFragment extends Fragment {
    public static WebView myWebView;

    //private boolean WAS_OPENED = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() { if(LabFragment.myWebView.canGoBack()){ LabFragment.myWebView.goBack(); }; }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_lab, container, false);
        return root;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myWebView = (WebView) getActivity().findViewById(R.id.labreader);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.loadUrl("file:///android_asset/loading_lab.html");

        Toast.makeText(getContext(), "Проверка текущей версии", Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), "Скачивание недостающих данных", Toast.LENGTH_LONG).show();
        Toast.makeText(getContext(), "Скачивание успешно", Toast.LENGTH_SHORT).show();
    }
}