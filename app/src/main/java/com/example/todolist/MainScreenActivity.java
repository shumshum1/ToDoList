package com.example.todolist;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_screen);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // קבלת ה-FragmentContainerView מה-XML
        View fragmentContainer = findViewById(R.id.fragmentContainerView);
        View fragmentContainer1 = findViewById(R.id.fragmentContainerView2);

        // קביעת מאזין ללחיצה
        fragmentContainer.setOnClickListener(v -> {
            // קבלת גובה המסך
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int screenHeight = displayMetrics.heightPixels;

            // שינוי גובה ה-FragmentContainerView ל-2/3 מגובה המסך
            ViewGroup.LayoutParams params = fragmentContainer.getLayoutParams();
            params.height = (int) (screenHeight * 2 / 3.0);
            fragmentContainer.setLayoutParams(params);
        });
        fragmentContainer1.setOnClickListener(v -> {
            // קבלת גובה המסך
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int screenHeight = displayMetrics.heightPixels;

            // שינוי גובה ה-FragmentContainerView ל-2/3 מגובה המסך
            ViewGroup.LayoutParams params = fragmentContainer1.getLayoutParams();
            params.height = (int) (screenHeight * 2 / 3.0);
            fragmentContainer.setLayoutParams(params);
        });
    }
}
