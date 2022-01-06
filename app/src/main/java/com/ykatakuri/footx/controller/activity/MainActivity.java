package com.ykatakuri.footx.controller.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ykatakuri.footx.R;

public class MainActivity extends AppCompatActivity {

    private TextView mRegisterTextView;
    private EditText mEmailEditText, mPasswordEditText;
    private Button mLoginButton;

    private ProgressDialog mProgressDialog;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mRegisterTextView = findViewById(R.id.main_textview_register);
        mEmailEditText = findViewById(R.id.main_edittext_email);
        mPasswordEditText = findViewById(R.id.main_edittext_password);
        mLoginButton = findViewById(R.id.main_button_login);
        mProgressDialog = new ProgressDialog(this);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        mRegisterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });
    }

    private void performLogin() {
        String email = mEmailEditText.getText().toString();
        String password= mPasswordEditText.getText().toString();

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailEditText.setError("Email incorrect");
            mEmailEditText.requestFocus();
        } else if(password.isEmpty() || password.length() < 8) {
            mPasswordEditText.setError("Mot de passe incorrect");
        } else {
            mProgressDialog.setMessage("Connexion en cours...");
            mProgressDialog.setTitle("Connexion");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();

            mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        mProgressDialog.dismiss();
                        sendUserToHomeActivity();
                        Toast.makeText(MainActivity.this, "Connexion réussie!", Toast.LENGTH_SHORT).show();
                    } else {
                        mProgressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Connexion échouée! Veuillez réessayer" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    private void sendUserToHomeActivity() {
        Intent intent = new Intent(MainActivity.this, FootActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}