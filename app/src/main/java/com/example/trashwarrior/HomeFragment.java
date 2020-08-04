package com.example.trashwarrior;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    Dialog myDialog;
    FirebaseDatabase fbase=FirebaseDatabase.getInstance();
    DatabaseReference fref=fbase.getReference().child("points");
    TextView pts;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_home, container, false);

        ImageView map=(ImageView)view.findViewById(R.id.imageView);
        ImageView coupon=(ImageView)view.findViewById(R.id.imageView1);
        ImageView ngo=(ImageView)view.findViewById(R.id.imageView2);
        ImageView points=(ImageView)view.findViewById(R.id.imageView3);
        ImageView tree=(ImageView)view.findViewById(R.id.imageView4);
         pts=view.findViewById(R.id.textView25);
        fref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int pt=dataSnapshot.child("value").getValue(Integer.class);
                pts.setText(Integer.toString(pt));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),MapsActivity.class);
                startActivity(i);
            }
        });

        coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getContext(),Coupon.class);
                startActivity(i);
            }
        });
        ngo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.dailydump.org"));
                startActivity(i);
            }
        });
        points.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value= (int) (Integer.parseInt(pts.getText().toString())*1.02);
                fref.child("value").setValue(value).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){ Intent i= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com"));
                            startActivity(i);
                        }
                    }
                });

            }
        });
        tree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button donate,later;
                final TextView txtview;
                myDialog=new Dialog(getActivity());
                myDialog.setContentView(R.layout.tree);
                later =(Button) myDialog.findViewById(R.id.button);
                donate=(Button)myDialog.findViewById(R.id.button2);
                txtview =(TextView) myDialog.findViewById(R.id.textView1);
                txtview.setText("Trees provide so many benefits to our everyday lives. They filter clean air," +
                                " provide fresh drinking water, help curb climate change, and create homes " +
                        "for thousands of species of plants and animals." +
                                "  Planting a Billion Trees can help save the Earth from deforestation. " +
                        " It’s a big number, but we know we can do it with your help.\n" +
                                "\n");

                later.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                donate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i= new Intent(Intent.ACTION_VIEW, Uri.parse("https://sankalptaru.org"));
                        startActivity(i);
                    }
                });
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });
        return view;
    }

}
