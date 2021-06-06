package com.riyadh.dailynotes10118421.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.fragment.app.Fragment;

import com.riyadh.dailynotes10118421.R;

/*Membuat project pada tanggal : 4 Juni 2021
Oleh
NIM   : 10118421
Nama  : Riyadh Rachman Firdaus
Kelas : IF10
Selesai pada tanggal :  6 Juni 2021*/

public class InfoFragment3 extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info3, container, false);


        ImageButton btn_info = (ImageButton) view.findViewById(R.id.btn_info);
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(getView());
            }
        });

        return view;
    }

    public void showDialog(View view) {
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("About DailyNotes App");
        builder.setMessage("Version 1.0.0 " +
                "developed by Riyadh Rachman Firdaus ");
        // add a button
        builder.setPositiveButton("OK", null);
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}