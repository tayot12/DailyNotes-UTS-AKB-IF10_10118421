package com.riyadh.dailynotes10118421.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.riyadh.dailynotes10118421.MainActivity;
import com.riyadh.dailynotes10118421.R;
import com.riyadh.dailynotes10118421.database.Database;

/*Membuat project pada tanggal : 4 Juni 2021
Oleh
NIM   : 10118421
Nama  : Riyadh Rachman Firdaus
Kelas : IF10
Selesai pada tanggal :  6 Juni 2021*/

public class Detail extends AppCompatActivity {

    protected Cursor cursor;
    Database database;
    Button btn_kembali;
    TextView judul, kategori, isi, timestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        database = new Database(this);
        timestamp = findViewById(R.id.timestamp);
        judul = findViewById(R.id.judul);
        kategori = findViewById(R.id.kategori);
        isi = findViewById(R.id.isi);
        btn_kembali = findViewById(R.id.btn_kembali);
        btn_kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Detail.this, MainActivity.class));
                finish();
            }
        });


        SQLiteDatabase db = database.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM note WHERE judul = '" +
                getIntent().getStringExtra("judul")+"'", null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0){
            cursor.moveToPosition(0);
            judul.setText(cursor.getString(0).toString());
            kategori.setText(cursor.getString(1).toString());
            isi.setText(cursor.getString(2).toString());
            timestamp.setText(cursor.getString(3).toString());
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}