package com.riyadh.dailynotes10118421.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Update extends AppCompatActivity {
    protected Cursor cursor;
    Database database;
    Button btn_simpan;
    EditText judul, kategori, isi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        database = new Database(this);
        judul = findViewById(R.id.judul);
        kategori = findViewById(R.id.kategori);
        isi = findViewById(R.id.isi);

        SQLiteDatabase db = database.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM note WHERE judul = '" +
                getIntent().getStringExtra("judul") + "'", null);
        cursor.moveToFirst();

        if(cursor.getCount() >0){
            cursor.moveToPosition(0);
            judul.setText(cursor.getString(0).toString());
            kategori.setText(cursor.getString(1).toString());
            isi.setText(cursor.getString(2).toString());
        }

        btn_simpan = findViewById(R.id.btn_simpan);
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                //validasi data kosong
                if (isEmpty(judul.getText().toString())) {
                    Toast.makeText(Update.this, "Judul Catatan Harus Diisi!", Toast.LENGTH_SHORT).show();
                } else {
                    SQLiteDatabase db = database.getWritableDatabase();
                    db.execSQL("update note set judul='" +
                            judul.getText().toString() + "', kategori= '" +
                            kategori.getText().toString() + "', isi= '" +
                            isi.getText().toString() +"' where judul = '" +
                            getIntent().getStringExtra("judul")+"'");
                    Toast.makeText(Update.this, "Data Berhasil di Update" , Toast.LENGTH_SHORT).show();
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