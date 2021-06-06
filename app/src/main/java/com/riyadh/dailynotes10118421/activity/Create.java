package com.riyadh.dailynotes10118421.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.riyadh.dailynotes10118421.MainActivity;
import com.riyadh.dailynotes10118421.R;
import com.riyadh.dailynotes10118421.database.Database;

import static android.text.TextUtils.isEmpty;

/*Membuat project pada tanggal : 4 Juni 2021
Oleh
NIM   : 10118421
Nama  : Riyadh Rachman Firdaus
Kelas : IF10
Selesai pada tanggal :  6 Juni 2021*/

public class Create extends AppCompatActivity {
    protected Cursor cursor;
    Database database;
    Button btn_simpan;
    EditText judul, kategori, isi;
    TextView timestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        database = new Database(this);
        judul = findViewById(R.id.judul);
        kategori = findViewById(R.id.kategori);
        isi = findViewById(R.id.isi);
        timestamp = findViewById(R.id.timestamp);

        btn_simpan = findViewById(R.id.btn_simpan);

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //validasi data kosong
                if (isEmpty(judul.getText().toString())) {
                    Toast.makeText(Create.this, "Judul notes harus di isi!", Toast.LENGTH_SHORT).show();
                } else {
                    SQLiteDatabase db = database.getWritableDatabase();
                    db.execSQL("insert into note(judul, kategori, isi) values('" +
                            judul.getText().toString() + "','" +
                            kategori.getText().toString() + "','" +
                            isi.getText().toString() + "')");
                    Toast.makeText(Create.this, "Data Tersimpan", Toast.LENGTH_SHORT).show();
                    MainActivity.ma.Refresh();
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}