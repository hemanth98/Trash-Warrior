package com.example.trashwarrior;

import android.widget.EditText;

import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

public class UserInfo implements Serializable {

    public String getUname() {
        return Uname;
    }

    public void setUname(String uname) {
        Uname = uname;
    }

    public String getMobno() {
        return Mobno;
    }

    public void setMobno(String mobno) {
        Mobno = mobno;
    }

    public String getDob() {
        return Dob;
    }

    public void setDob(String dob) {
        Dob = dob;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    String Uname="";
    String Mobno="";
    String Dob="";
    String Email="";
    String Address="";
    String Gender="";

    public UserInfo(EditText name, EditText mobno, EditText dob, EditText email, EditText address, EditText gender)
    {

    }
    public UserInfo(String name,String mobno,String dob,String email,String address,String gender)
    {
        Uname=name;
        Mobno=mobno;
        Dob=dob;
        Email=email;
        Address=address;
        Gender=gender;
    }

}

