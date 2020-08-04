package com.example.trashwarrior;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Coupon extends AppCompatActivity {

    Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
    }

    public void dominos(View view)
    {
        Intent i= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.dominos.co.in/"));
        startActivity(i);
    }

    public void redeem(View view){

        Button ok;
        final TextView txtview;
        myDialog=new Dialog(Coupon.this);
        myDialog.setContentView(R.layout.redeem);
        ok=(Button)myDialog.findViewById(R.id.button2);
        txtview =(TextView) myDialog.findViewById(R.id.textView1);
        txtview.setText("You need to plant more trees to redeem the vouchers");

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }
}