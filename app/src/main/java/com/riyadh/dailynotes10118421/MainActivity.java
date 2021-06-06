package com.riyadh.dailynotes10118421;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.riyadh.dailynotes10118421.activity.Create;
import com.riyadh.dailynotes10118421.activity.Detail;
import com.riyadh.dailynotes10118421.activity.InformationActivity;
import com.riyadh.dailynotes10118421.activity.ProfileActivity;
import com.riyadh.dailynotes10118421.activity.Update;
import com.riyadh.dailynotes10118421.database.Database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/*Membuat project pada tanggal : 4 Juni 2021
Oleh
NIM   : 10118421
Nama  : Riyadh Rachman Firdaus
Kelas : IF10
Selesai pada tanggal :  6 Juni 2021*/

public class MainActivity extends AppCompatActivity {
    // membuat notif saat ingin keluar aplikasi
    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

    private BottomNavigationView bottomNavigationView;

    String[] daftar, daftar2;
    ListView listView;
    Database database;
    protected Cursor cursor;
    public static MainActivity ma;

      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Create.class);
                startActivity(intent);
            }
        });
        ma = this;
        database = new Database(this);
        Refresh();

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.action_notes);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_notes :
                        return true;

                    case R.id.action_profile :
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0,0);
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

    public void Refresh() {
        SQLiteDatabase db = database.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM note", null);
        daftar = new String[cursor.getCount()];
        daftar2 = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            daftar[i] = cursor.getString(0).toString();
            daftar2[i] = cursor.getString(3).toString();
        }
        listView = (ListView) findViewById(R.id.listView);
        //listView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftar));
        MyAdapter adapter = new MyAdapter(this, daftar2, daftar);
        listView.setAdapter(adapter);
        listView.setSelected(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String selection = daftar[position];
                final CharSequence[] dialogItem = {"Lihat Notes", "Update Notes", "Hapus Notes"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                Intent i = new Intent(getApplicationContext(), Detail.class);
                                i.putExtra("judul", selection);
                                startActivity(i);
                                break;
                            case 1:
                                Intent in = new Intent(getApplicationContext(), Update.class);
                                in.putExtra("judul", selection);
                                startActivity(in);
                                break;
                            case 2:
                                SQLiteDatabase db = database.getWritableDatabase();
                                db.execSQL("delete from note where judul = '" + selection + "'");
                                Toast.makeText(MainActivity.this, "Notes berhasil dihapus!", Toast.LENGTH_SHORT).show();
                                Refresh();
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
       //((ArrayAdapter) listView.getAdapter()).notifyDataSetInvalidated();
        //((MyAdapter) listView.getAdapter()).notifyDataSetInvalidated();
    }


    public class MyAdapter extends ArrayAdapter<String> {

          Context context;
          String[] daftar, daftar2;


        public MyAdapter(Context context, String[] daftar, String[] daftar2) {
            super(context, R.layout.list_row, R.id.judul, daftar);
            this.context = context;
            this.daftar = daftar;
            this.daftar2 = daftar2;
        }

        @Override
        public int getCount() {
            return cursor.getCount();
        }


        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = getLayoutInflater().inflate(R.layout.list_row, parent, false);

            TextView dot = view.findViewById(R.id.dot);
            TextView timestamp = view.findViewById(R.id.timestamp);
            TextView judul = view.findViewById(R.id.judul);

            dot.setText(Html.fromHtml("&#8226;"));
            timestamp.setText(formatDate(daftar[position]));
            judul.setText(daftar2[position]);



            return view;
        }
    }

    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("d MMM yyyy");
            return fmtOut.format(date);
        } catch (ParseException e) {

        }

        return "";
    }

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
        }else { Toast.makeText(getBaseContext(), "Tekan Back Sekali lagi untuk Keluar", Toast.LENGTH_SHORT).show();
        }
        mBackPressed = System.currentTimeMillis();
    }

}