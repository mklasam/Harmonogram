package org.mycode.harmonogram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    private FrameLayout frameLayout;
    private FragmentAllTasks fragmentAllTasks;
    private Button goInto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fragmentAllTasks = new FragmentAllTasks();
        goInto = findViewById(R.id.zakladkaq);
        goInto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction().detach(fragmentAllTasks).attach(fragmentAllTasks);
                ft.replace(R.id.main_frame, fragmentAllTasks);
                ft.addToBackStack(null);
                ft.commit();
                goInto.setEnabled(false);
            }
        });
    }
}