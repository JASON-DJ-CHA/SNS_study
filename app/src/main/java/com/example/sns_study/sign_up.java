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

public class sign_up extends AppCompatActivity {
    private FirebaseAuth mAuth;

    Button btnSign, btnMoveLoginPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        btnSign = findViewById(R.id.btnLogin);
        btnMoveLoginPage = findViewById(R.id.btnMoveLoginPage);

        btnMoveLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(sign_up.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(sign_up.this, "요청보내기", Toast.LENGTH_SHORT).show();
                createUser();
            }
        });

    }


    // 없으면 파이어베이스 실행이 안됨.
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);

    }
    //뒤로가지 않고 앱이 종료되는 코드

    private void createUser() {
        // layout에서 변수들 가져오기
        String email= ((EditText)findViewById(R.id.txtLoginEmail)).getText().toString();
        String password =((EditText)findViewById(R.id.etLoginPw)).getText().toString();
        String passwordCheck = ((EditText)findViewById(R.id.etpassword2)).getText().toString();

        if(email.length() >0 && password.length() > 0 && passwordCheck.length()>0){
            if(password.equals(passwordCheck)){
                //password 일치 시 성공 if 문
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
//                                Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    informMsg("회원가입에 성공하였습니다.");
                                } else {
                                    // If sign in fails, display a message to the user.
//                                Log.w(TAG, "createUserWithEmail:failure", task.getException())
//                                informMsg("회원가입에 실패하였습니다.");
                                    if(task.getException() != null){
                                        //null값일때 표기가 된다? 실험해보자.
                                        // ==성공
                                        informMsg(task.getException().toString());
                                    }
                                }
                            }


                        });
            }else {
                // 비밀번호가 일치 하지 않았을때
                informMsg("비밀번호가 일치하지 않습니다.");
            }
        }else {
            informMsg("이메일 또는 비밀번호를 입력해주세요");
        }
    }
    private void informMsg(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
