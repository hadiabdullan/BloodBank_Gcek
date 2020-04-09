package com.hadi123.www.bloodbankgcek;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    AutoCompleteTextView inputSearch;
    RecyclerView recyclerView;
    FirebaseRecyclerOptions<Users> options;
    FirebaseRecyclerAdapter<Users,MyViewHolder> adapter;
    DatabaseReference DataRef;
    FirebaseAuth auth;
    FloatingActionButton addinguser;
    ImageView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        DataRef= FirebaseDatabase.getInstance().getReference().child("users");
        inputSearch=findViewById(R.id.inputsearch);
        recyclerView=findViewById(R.id.recyleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        auth=FirebaseAuth.getInstance();

        addinguser=findViewById(R.id.add_fab);
        addinguser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(AdminActivity.this,AdminRegisterActivity.class);
                startActivity(i);
            }
        });





        String[] blood = {"A+","A-","B+","B-","O+","O-","AB+","AB-"};
        final ArrayAdapter<String> use=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,blood);
        LoadData("");

       inputSearch.setThreshold(1);
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                inputSearch.setAdapter(use);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString()!= null){
                    LoadData(s.toString());
                }
                else{
                    LoadData("");


                }
            }
        });

    }
   //
    private void LoadData(String data) {


        Query query= DataRef.orderByChild("Bloodgroup").startAt(data).endAt(data + "\uf8ff");

        options=new FirebaseRecyclerOptions.Builder<Users>().setQuery(query,Users.class).build();
        adapter= new FirebaseRecyclerAdapter<Users, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, final int position, @NonNull Users model) {
                holder.nametext_single.setText(model.getName());
                holder.bloodtext_sinle.setText(model.getBloodgroup());
                holder.mobilenumber_single.setText(model.getMobile_number());
                holder.donation_single.setText(model.getDonation());

                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(AdminActivity.this,MainNewActivity.class);
                        i.putExtra("UserKey",getRef(position).getKey());
                        startActivity(i);
                    }
                });
            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view,parent,false);
                return new MyViewHolder(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);


        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(AdminActivity.this)
                        .setTitle("Warning")
                        .setMessage("Are you sure that you want to logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent=new Intent(AdminActivity.this,LoginActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", null)
                        .create()
                        .show();

            }
        });


    }
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 1000);
    }



}
