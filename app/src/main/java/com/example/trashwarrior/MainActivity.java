package com.example.trashwarrior;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseUser fUser;
    EditText email;
    EditText pass;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        fUser=mAuth.getCurrentUser();
        progressDialog=new ProgressDialog(this);
        if(fUser !=null){
              Intent i=new Intent(MainActivity.this,HomeActivity.class);
              startActivity(i);
        }

        Toast.makeText(this, "Welcome to become a Trash Warrior", Toast.LENGTH_SHORT).show();
        email = (EditText) findViewById(R.id.editText);
        pass = (EditText) findViewById(R.id.editText2);

        Button login = (Button) findViewById(R.id.button4);
        TextView signup = (TextView) findViewById(R.id.textView4);
        Button verify=findViewById(R.id.verify);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!email.toString().isEmpty() && !pass.toString().isEmpty()) {
                    if (pass.length() < 8) {
                        Toast.makeText(getApplicationContext(), "Password too short, enter minimum 8 characters!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.setMessage("Please Wait...");
                            progressDialog.show();
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(MainActivity.this,"Verification Sent!!",Toast.LENGTH_LONG).show();
                                mAuth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            fUser = mAuth.getCurrentUser();
//                                            fUser.sendEmailVerification();
                                        }
                                    }
                                });
                            }
                            else
                                Toast.makeText(MainActivity.this,"Error! Retry Later", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
//        Toast.makeText(MainActivity.this,fUser.getEmail().toString(),Toast.LENGTH_LONG).show();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mAuth.getCurrentUser()!=null) {
                    Intent i = new Intent(getApplicationContext(), signUP.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Verify First",Toast.LENGTH_LONG).show();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!email.toString().isEmpty() && !pass.toString().isEmpty()) {
                    if (pass.length() < 8) {
                        Toast.makeText(getApplicationContext(), "Password too short, enter minimum 8 characters!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    mAuth.signInWithEmailAndPassword(email.getText().toString(),pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(i);
                            }
                            else
                                Toast.makeText(MainActivity.this, "Enter Correct Username and Password", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

    }
}
