package org.mycode.harmonogram;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    private EditText mUsername;
    private EditText mPass;
    private Button btnLogin;
    private TextView mSignupHere;
    private ProgressDialog mDialog;
    private ScheduleListFragment scheduleListFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidNetworking.initialize(getApplicationContext());
        mDialog = new ProgressDialog(this);
        loginDetails();
    }

    private void loginDetails() {
        mUsername = findViewById(R.id.username_login);
        mPass = findViewById(R.id.password_login);
        btnLogin = findViewById(R.id.btn_login);
        mSignupHere = findViewById(R.id.signup_reg);
        mPass.setTransformationMethod(new PasswordTransformationMethod());
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUsername.getText().toString().trim();
                String pass = mPass.getText().toString().trim();

                if(TextUtils.isEmpty(username)) {
                    mUsername.setError("Wpisz nazwę użytkownika!");
                    return;
                }
                if(TextUtils.isEmpty(pass)) {
                    mUsername.setError("Wpisz hasło!");
                    return;
                }
                mDialog.setMessage("Logowanie...");
                mDialog.show();
                AndroidNetworking.post("http://10.0.2.2:5000/login")
                        .addHeaders("Content-Type", "application/json")
                        .addBodyParameter("username", username)
                        .addBodyParameter("password", pass)
                        .build()
                        .getAsJSONArray(new JSONArrayRequestListener() {
                            @Override
                            public void onResponse(JSONArray response) {
                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                    }
                                }, 50000L);
                                mDialog.dismiss();
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                Toast.makeText(getApplicationContext(), "Jesteś zalogowany!", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onError(ANError error) {
                                mDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Błąd logowania!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        mSignupHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
            }
        });
    }
}