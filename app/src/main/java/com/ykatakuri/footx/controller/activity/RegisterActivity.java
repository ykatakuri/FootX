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

public class RegisterActivity extends AppCompatActivity {

    private TextView mLoginTextView;
    private EditText mEmailEditText, mPasswordEditText, mConfirmPasswordEditText;
    private Button mRegisterButton;
    private ProgressDialog mProgressDialog;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mLoginTextView = findViewById(R.id.register_textview_login);
        mEmailEditText = findViewById(R.id.register_edittext_email);
        mPasswordEditText = findViewById(R.id.register_edittext_password1);
        mConfirmPasswordEditText = findViewById(R.id.register_edittext_password2);
        mRegisterButton = findViewById(R.id.register_button_register);
        mProgressDialog = new ProgressDialog(this);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        mLoginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performRegistration();
            }
        });
    }

    private void performRegistration() {
        String email = mEmailEditText.getText().toString();
        String password= mPasswordEditText.getText().toString();
        String confirmPassword = mConfirmPasswordEditText.getText().toString();

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailEditText.setError("Email incorrect");
            mEmailEditText.requestFocus();
        } else if(password.isEmpty() || password.length() < 8) {
            mPasswordEditText.setError("Mot de passe incorrect");
        } else if(!password.equals(confirmPassword)) {
            mPasswordEditText.setError("Les deux mots de passe sont différents");
        } else{
            mProgressDialog.setMessage("Inscription en cours...");
            mProgressDialog.setTitle("Inscription");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();

            mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        mProgressDialog.dismiss();
                        sendUserToLoginActivity();
                        Toast.makeText(RegisterActivity.this, "Inscription terminée!", Toast.LENGTH_SHORT).show();
                    }else{
                        mProgressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "Echec de l'inscription! Veuillez réessayer"+task.getException(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserToLoginActivity() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}