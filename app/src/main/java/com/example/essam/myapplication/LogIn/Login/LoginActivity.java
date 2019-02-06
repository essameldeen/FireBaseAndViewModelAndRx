package com.example.essam.myapplication.LogIn.Login;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.essam.myapplication.LogIn.HomePage.HomeActivity;
import com.example.essam.myapplication.LogIn.Register.RegisterActivity;
import com.example.essam.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button login;
    private TextView register;
    private LogInViewModel logInViewModel;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onStart() {
        super.onStart();
        if(auth!=null){
         goToHomePage(auth.getCurrentUser().getUid());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth =FirebaseAuth.getInstance();
        logInViewModel = ViewModelProviders.of(this).get(LogInViewModel.class);
        initListerner();
        initView();
    }

    private void initView() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerActivity = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerActivity);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress();
                logInViewModel.logIn(getApplicationContext(), email.getText().toString(), password.getText().toString());
            }
        });


    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void initListerner() {
        logInViewModel.getLogin().observe(this, new Observer<loginModel>() {
            @Override
            public void onChanged(@Nullable loginModel loginModel) {
                hideProgress();
                if (loginModel != null) {
                     goToHomePage(loginModel.getId());

                } else {

                    Toast.makeText(LoginActivity.this, "Please Check Email or Password.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void goToHomePage(String id) {
        Intent home = new Intent(LoginActivity.this,HomeActivity.class);
        home.putExtra("userId",id);
        startActivity(home);
        finish();
    }


}
