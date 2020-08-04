package com.example.trashwarrior;



import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }
    private FirebaseAuth mAuth;
    FirebaseUser fUser;
    Dialog myDialog;
    private static final int PICK_IMAGE=1;
    private static final int RESULT_OK=-1;
    Uri imageUri;
    ImageView imageView;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_profile, container, false);

        mAuth = FirebaseAuth.getInstance();
        fUser=mAuth.getCurrentUser();

         imageView=(ImageView)view.findViewById(R.id.imageView3);

        final TextView tv1=(TextView) view.findViewById(R.id.textView6);
        final TextView tv2=(TextView) view.findViewById(R.id.textView8);
        final TextView tv3=(TextView) view.findViewById(R.id.textView9);
        final TextView tv4=(TextView) view.findViewById(R.id.textView10);
        final TextView tv5=(TextView) view.findViewById(R.id.textView11);
        final TextView tv6=(TextView) view.findViewById(R.id.textView12);

        TextView t1=(TextView) view.findViewById(R.id.textView13);
        TextView t2=(TextView) view.findViewById(R.id.textView14);
        TextView t3=(TextView) view.findViewById(R.id.textView15);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("USERS").child(mAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name=dataSnapshot.child("uname").getValue().toString();
                String add=dataSnapshot.child("address").getValue().toString();
                String dob=dataSnapshot.child("dob").getValue().toString();
                String email=dataSnapshot.child("email").getValue().toString();
                String mob=dataSnapshot.child("mobno").getValue().toString();
                String gender=dataSnapshot.child("gender").getValue().toString();
                tv1.setText(name);
                tv2.setText(mob);
                tv3.setText(dob);
                tv4.setText(email);
                tv5.setText(add);
                tv6.setText(gender);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery=new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery,"Select Picture"),PICK_IMAGE);
            }
        });



        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txtclose;
                myDialog=new Dialog(getActivity());
                myDialog.setContentView(R.layout.trash_rate);
                txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
                txtclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });

        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            TextView txtclose;
            myDialog=new Dialog(getActivity());
            myDialog.setContentView(R.layout.refer_friend);
            txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
            txtclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.dismiss();
                }
            });
            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.show();
            }
        });

        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button signout,cancel;
                myDialog=new Dialog(getActivity());
                myDialog.setContentView(R.layout.confirm_signout);
                cancel =(Button) myDialog.findViewById(R.id.button);
                signout=(Button)myDialog.findViewById(R.id.button2);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                signout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAuth.signOut();
                        Toast.makeText(getContext(),"Signed Out",Toast.LENGTH_LONG).show();
                        Intent i= new Intent(getContext(),MainActivity.class);
                        startActivity(i);
                    }
                });
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_IMAGE && resultCode== RESULT_OK){
            imageUri=data.getData();
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),imageUri);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
