package com.hell.hell;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Profile extends AppCompatActivity {
TextView Name,Email,Mob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    Name=(TextView)findViewById(R.id.Pname);
        Email=(TextView)findViewById(R.id.Pemail);
       // Mob=(TextView)findViewById(R.id.Mob);
Name.setText(SharedPrefManager.getInstance(this).getName());
        Email.setText(SharedPrefManager.getInstance(this).getEmail());
     //   Mob.setText(Math.toIntExact(SharedPrefManager.getInstance(this).getMob()));
    }

    }
