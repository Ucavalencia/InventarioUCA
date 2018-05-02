package com.uca.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseLogin extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText emailInput;
    private EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_login);

        mAuth = FirebaseAuth.getInstance();
        emailInput = (EditText) findViewById(R.id.textUser);
        passwordInput = (EditText) findViewById(R.id.textPassword);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseUser mUser = mAuth.getCurrentUser();
        if (mUser != null)
        {
            Intent intent = new Intent(this, PaginaPrincipal.class);
            this.startActivity(intent);
            this.finishActivity(0);
        }
    }

    public void signIn(String email, String password)
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login correcto",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(FirebaseLogin.this, PaginaPrincipal.class);
                            FirebaseLogin.this.startActivity(intent);
                            FirebaseLogin.this.finishActivity(0);
                        } else {
                            Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos",
                                    Toast.LENGTH_LONG).show();
                            passwordInput.setText("");
                        }
                    }
                });
    }

    public void logIn(View view)
    {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        signIn(email, password);
    }
}
