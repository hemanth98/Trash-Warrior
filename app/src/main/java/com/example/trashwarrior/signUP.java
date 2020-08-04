package com.example.trashwarrior;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signUP extends AppCompatActivity {

    private Button btnSignUp;
    private FirebaseAuth auth;
    EditText name,mobno,dob,email,address,gender;
    FirebaseUser fUser;
    DatabaseReference databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name=findViewById(R.id.editText3);
        mobno=findViewById(R.id.editText4);
        dob=findViewById(R.id.editText5);
        email=findViewById(R.id.editText7);
        address=findViewById(R.id.editText6);
        gender=findViewById(R.id.editText8);
        btnSignUp=findViewById(R.id.button2);

        auth = FirebaseAuth.getInstance();
        fUser=auth.getCurrentUser();
        databaseUser= FirebaseDatabase.getInstance().getReference().child("USERS");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                FirebaseUser currentUser= auth.getCurrentUser();
                String uid=currentUser.getUid();
                UserInfo userInfo=new UserInfo(name.getText().toString(),mobno.getText().toString(),dob.getText().toString(),email.getText().toString(),address.getText().toString(),gender.getText().toString());
                databaseUser.child(uid).setValue(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(signUP.this,"User Details Stored Successfully",Toast.LENGTH_LONG).show();
                            Intent i=new Intent(getApplicationContext(),HomeActivity.class);
                            startActivity(i);
                        }
                        else
                        {
                            Toast.makeText(signUP.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }

                });
            }
        });
    }
}
