package com.riyadh.dailynotes10118421.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.riyadh.dailynotes10118421.MainActivity;
import com.riyadh.dailynotes10118421.R;

/*Membuat project pada tanggal : 4 Juni 2021
Oleh
NIM   : 10118421
Nama  : Riyadh Rachman Firdaus
Kelas : IF10
Selesai pada tanggal :  6 Juni 2021*/

public class ProfileActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button btn_ig = (Button) findViewById(R.id.btn_ig) ;
        btn_ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.instagram.com/ibukotaarabb/");
                Intent ig = new Intent(Intent.ACTION_VIEW, uri);
                ig.setPackage("com.instagram.android");
                try {
                    startActivity(ig);
                }catch (ActivityNotFoundException e){
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.instagram.com/ibukotaarabb/")));
                }
            }
        });


        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.action_profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_notes :
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.action_profile :
                        return true;

                    case R.id.action_info :
                        startActivity(new Intent(getApplicationContext(), InformationActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });
    }
}