package com.hadi123.www.bloodbankgcek;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference reff;
    FirebaseAuth auth;
    FirebaseUser user;
    TextView name,email,dob,mob,blood,ht,wt,don,add,dept,yr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=findViewById(R.id.username1);
        email=findViewById(R.id.emailuser);
        dob=findViewById(R.id.dob);
        mob=findViewById(R.id.mobilenumber);
        blood=findViewById(R.id.bloodgroup);
        ht=findViewById(R.id.height);
        wt=findViewById(R.id.weight);
        don=findViewById(R.id.donation);
        add=findViewById(R.id.address);
        dept=findViewById(R.id.dept);
        yr=findViewById(R.id.year);
        database = FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        reff=FirebaseDatabase.getInstance().getReference().getRoot();
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name1 =dataSnapshot.child("users").child(auth.getCurrentUser().getUid()).child("name").getValue().toString();
                name.setText(name1);
                String mail =dataSnapshot.child("users").child(auth.getCurrentUser().getUid()).child("email").getValue().toString();
                email.setText(mail);
                String dobe =dataSnapshot.child("users").child(auth.getCurrentUser().getUid()).child("DateofBirth").getValue().toString();
                dob.setText(dobe);
                String mobile =dataSnapshot.child("users").child(auth.getCurrentUser().getUid()).child("mobile number").getValue().toString();
                mob.setText(mobile);
                String bloodg =dataSnapshot.child("users").child(auth.getCurrentUser().getUid()).child("Bloodgroup").getValue().toString();
                blood.setText(bloodg);
                String htg =dataSnapshot.child("users").child(auth.getCurrentUser().getUid()).child("height").getValue().toString();
                ht.setText(htg);
                String wtg =dataSnapshot.child("users").child(auth.getCurrentUser().getUid()).child("weight").getValue().toString();
                wt.setText(wtg);
                String dont =dataSnapshot.child("users").child(auth.getCurrentUser().getUid()).child("Donation").getValue().toString();
                don.setText(dont);
                String addd =dataSnapshot.child("users").child(auth.getCurrentUser().getUid()).child("Address").getValue().toString();
                add.setText(addd);
                String yj =dataSnapshot.child("users").child(auth.getCurrentUser().getUid()).child("yearOfJoin").getValue().toString();
                yr.setText(yj);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
