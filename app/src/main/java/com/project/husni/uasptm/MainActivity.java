package com.project.husni.uasptm;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.project.husni.uasptm.R;

public class MainActivity extends AppCompatActivity {
    private Button bt_data;
    private Button bt_exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_data = (Button) findViewById(R.id.btn_data);
        bt_exit = (Button) findViewById(R.id.btn_exit);

        bt_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendata();
            }
        });

        bt_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });
    }
    public void opendata(){
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }
    private void exit(){
        AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        builder.setMessage("Apakah Kamu Benar-Benar ingin Keluar?")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }
}
