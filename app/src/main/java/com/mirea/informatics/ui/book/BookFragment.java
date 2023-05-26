package com.mirea.informatics.ui.book;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewFragment;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.mirea.informatics.R;
import com.mirea.informatics.ui.book.BookFragment;

public class BookFragment extends Fragment {
    public static WebView myWebView;

    //private boolean WAS_OPENED = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() { if(BookFragment.myWebView.canGoBack()){ BookFragment.myWebView.goBack(); }; }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_book, container, false);
        return root;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myWebView = (WebView) getActivity().findViewById(R.id.bookreader);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.loadUrl("file:///android_asset/loading.html");

        Toast.makeText(getContext(), "Проверка текущей версии", Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), "Скачивание недостающих данных", Toast.LENGTH_LONG).show();
        Toast.makeText(getContext(), "Скачивание успешно", Toast.LENGTH_SHORT).show();
    }
}