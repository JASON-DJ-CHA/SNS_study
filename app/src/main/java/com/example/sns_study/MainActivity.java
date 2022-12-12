package com.example.sns_study;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class MainActivity extends AppCompatActivity {
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // 회원가입이 안되어있으면 회원가입페이지로
        if(user== null){
            useIntent(sign_up.class);
        }else{
            // 유져 프로필 가져오는데 이름이 없으면 회원 정보 업데이트로
            for (UserInfo profile : user.getProviderData()) {
                String name = profile.getDisplayName();

                if(name != null){
                    if(name.length() == 0) {
                        useIntent(MemberActivity.class);
                    }
                }
            }
        }

        // 로그아웃 버튼
        btnLogout = findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                useIntent(sign_up.class);
            }
        });
        // 로그아웃 버튼 종료


    }

    private void useIntent(Class ClassName){
        Intent intent = new Intent(this,ClassName);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}