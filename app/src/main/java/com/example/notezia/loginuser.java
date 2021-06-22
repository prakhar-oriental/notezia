package com.example.notezia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginuser extends AppCompatActivity {
    ProgressBar loginProgressbar;
    FirebaseAuth loginfirebaseAuth;
    EditText loginemail, loginpassword;
    Button loginbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginuser);
        loginbutton = findViewById(R.id.loginbutton);
        loginfirebaseAuth = FirebaseAuth.getInstance();
        loginemail = findViewById(R.id.loginemail);
        loginpassword = findViewById(R.id.loginpassword);
        loginProgressbar = findViewById(R.id.loginprogressBar);
        loginProgressbar.setVisibility(View.INVISIBLE);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginProgressbar.setVisibility(View.VISIBLE);
                String email = loginemail.getText().toString().trim();
                String password = loginpassword.getText().toString().trim();
                if(email.isEmpty())
                {
                    Toast.makeText(loginuser.this, "Email is empty", Toast.LENGTH_SHORT).show();
                }else if(password.isEmpty())
                {
                    Toast.makeText(loginuser.this, "Password is empty", Toast.LENGTH_SHORT).show();
                }else
                {
                    loginfirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete( Task<AuthResult> task) {
                            if(task!=null)
                            {
                                loginProgressbar.setVisibility(View.INVISIBLE);
                                startActivity(new Intent(loginuser.this,homescreen.class));
                                finish();
                            }

                        }
                    });

                }
            }
        });
    }
}
