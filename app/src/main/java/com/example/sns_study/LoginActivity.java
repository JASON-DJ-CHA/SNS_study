package com.example.sns_study;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    Button btnLogin,btnPwReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        btnLogin = findViewById(R.id.btnLogin);
        btnPwReset = findViewById(R.id.btnPwReset);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();

            }
        });

        btnPwReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                useIntent(Information_resetActivity.class);

            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }

    private void login(){
        String email = ((EditText)findViewById(R.id.etEmail)).getText().toString();
        String password = ((EditText)findViewById(R.id.etLoginPw)).getText().toString();

        if(email.length() >0 && password.length() > 0){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            informMsg("로그인 성공");

                            useIntent(MainActivity.class);
                        } else {
                            // If sign in fails, display a message to the user.
                            if(task.getException() != null){
                                //null값일때 표기가 된다? 실험해보자.
                                //
                                informMsg(task.getException().toString());
                            }
                        }
                    }
                });
        }else {
            informMsg("이메일 또는 비밀번호를 입력해주세요");
        }
    }
    private void informMsg(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void useIntent(Class ClassName){
        Intent intent = new Intent(this,ClassName);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
    }

}
