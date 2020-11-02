package com.example.fit4all;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private LoginButton loginButton;
    private CallbackManager callbackManager;

    private FirebaseAuth firebaseAut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
