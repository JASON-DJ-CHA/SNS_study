package com.example.sns_study;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class MainActivity extends AppCompatActivity {
    Button btnLogout, btnMemberInit;
    String name;
    TextView UserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogout = findViewById(R.id.btnLogout);
        btnMemberInit = findViewById(R.id.btnMemberInit);
        UserName = findViewById(R.id.tvUserName);

        // 파이어베이스에서 유저 프로필 가져오기
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        for (UserInfo profile : user.getProviderData()) {
            name = profile.getDisplayName();
            if(name == null){
                    useIntent(MemberActivity.class);
                    // 유저 이름이 없을때 프로필 설정페이지로 이동
            }
        }

        // 유저네임 화면에 보여지기
        UserName.setText(name);

        //회원정보 수정 및 등록
        btnMemberInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                useIntent(MemberActivity.class);
            }
        });

        // 로그아웃 버튼

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