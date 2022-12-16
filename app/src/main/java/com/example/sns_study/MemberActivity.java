package com.example.sns_study;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MemberActivity extends AppCompatActivity {
    private  static final String TAG ="MemberInitActivity";
    Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_init);


        btnConfirm = findViewById(R.id.btnConfirm);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserProfileUpdate();

            }
        });
    }



    private void UserProfileUpdate(){
        String name = ((EditText)findViewById(R.id.etName)).getText().toString();
        String PhoneNumber = ((EditText)findViewById(R.id.etPhoneNumber)).getText().toString();
        String Birthday = ((EditText)findViewById(R.id.etBirthday)).getText().toString();
        String Address = ((EditText)findViewById(R.id.etAddress)).getText().toString();



        if(name.length() >0 && PhoneNumber.length() > 7 && Birthday.length() >5 && Address.length() > 6){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            MemberInfo memberInfo = new MemberInfo(name,PhoneNumber,Birthday,Address);


            if(user != null){
                db.collection("users").document(user.getUid()).set(memberInfo)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                informMsg("회원 정보 입력 성공");
                                useIntent(MainActivity.class);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                informMsg("회원 정보 입력 실패");

                                Log.w(TAG, "Error writing document", e);
                                // 만약 실패오류가 발생했을시 firebase색인에서 규칙을 바꿔줄것
                            }
                        });
            }

//==============보류==========>
//            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                    .setDisplayName(name)
//                    .build();
//            //이름을 firebase에 업데이트가 가능해진다.
//
//
//            if(user != null){
//                user.updateProfile(profileUpdates)
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()) {
//                                    informMsg("업데이트 성공");
//                                    finish();
//                                }
//                            }
//                        });
//            }

        }else {
            informMsg("회원정보를 입력해주세요");

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
