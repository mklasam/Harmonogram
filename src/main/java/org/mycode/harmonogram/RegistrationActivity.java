package org.mycode.harmonogram;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class RegistrationActivity extends AppCompatActivity {
    private EditText mUsername;
    private EditText mPass;
    private EditText mName;
    private EditText mEmail;
    private Button btnRegister;
    private TextView mSingInHere;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        AndroidNetworking.initialize(getApplicationContext());
        mDialog = new ProgressDialog(this);
        registerDetails();
    }

    private void registerDetails() {
        mUsername = findViewById(R.id.username_reg);
        mPass = findViewById(R.id.password_reg);
        mName = findViewById(R.id.name_reg);
        mEmail = findViewById(R.id.email_reg);
        btnRegister = findViewById(R.id.btn_reg);
        mSingInHere = findViewById(R.id.signin_here);
        mPass.setTransformationMethod(new PasswordTransformationMethod());
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUsername.getText().toString().trim();
                String pass = mPass.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String name = mName.getText().toString().trim();
                if(TextUtils.isEmpty(username)) {
                    mUsername.setError("Wpisz nazwę użytkownika");
                    return;
                }
                if(TextUtils.isEmpty(pass)) {
                    mUsername.setError("Wpisz hasło!");
                    return;
                }
                if(TextUtils.isEmpty(email)) {
                    mEmail.setError("Wpisz email!");
                    return;
                }
                if(TextUtils.isEmpty(name)) {
                    mUsername.setError("Wpisz imię!");
                    return;
                }
                mDialog.setMessage("Logowanie...");
                mDialog.show();
                AndroidNetworking.post("http://10.0.2.2:5000/register")
                        .addHeaders("Content-Type", "application/json")
                        .addBodyParameter("username", username)
                        .addBodyParameter("password", pass)
                        .addBodyParameter("email", email)
                        .addBodyParameter("name", name)
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                    }
                                }, 50000L);
                                mDialog.dismiss();
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                Toast.makeText(getApplicationContext(), "Zostałeś zarejestrowany!", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onError(ANError error) {
                                Toast.makeText(getApplicationContext(), "Wystąpił błąd podczas rejestracji!", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

        mSingInHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}