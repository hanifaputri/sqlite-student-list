package com.example.tugas7ver2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
    DataHelper myDB;
    EditText et_nama, et_nim, et_kelas;
    Button btn_update, btn_delete;
    String id, nama, nim, kelas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        myDB = new DataHelper(UpdateActivity.this);

        et_nama = findViewById(R.id.et_ed_nama);
        et_nim = findViewById(R.id.et_ed_nim);
        et_kelas = findViewById(R.id.et_ed_kelas);
        btn_update = findViewById(R.id.btn_ed_update);
        btn_delete = findViewById(R.id.btn_ed_delete);

        getIntentData();

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });
    }

    void getIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("nama") && getIntent().hasExtra("nim") && getIntent().hasExtra("kelas")) {
            id = getIntent().getStringExtra("id");
            nama = getIntent().getStringExtra("nama");
            nim = getIntent().getStringExtra("nim");
            kelas = getIntent().getStringExtra("kelas");

            et_nama.setText(nama);
            et_nim.setText(nim);
            et_kelas.setText(kelas);
        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateData() {
        if (et_nama.getText().toString().equals(nama) && et_nim.getText().toString().equals(nim) && et_kelas.getText().toString().equals(kelas)){
            Toast.makeText(UpdateActivity.this, "You haven't made any changes", Toast.LENGTH_SHORT).show();
        } else if (!et_nim.getText().toString().equals(nim) && myDB.checkDataExist(et_nim.getText().toString().trim())){
            Toast.makeText(UpdateActivity.this, "Data already exists", Toast.LENGTH_SHORT).show();
        } else {
            boolean isUpdate = myDB.updateData(
                id,
                et_nama.getText().toString().trim(),
                et_nim.getText().toString().trim(),
                et_kelas.getText().toString().trim()
            );

            if (isUpdate == true) {
                setResult(990);
                finish();
            } else {
                Toast.makeText(UpdateActivity.this, "Data failed to add", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void deleteData() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Data?");
        builder.setMessage("Are you sure you want to delete " + nama + " data? The process can't be undone");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Integer deletedRows = myDB.deleteData(id);
                if (deletedRows > 0) {
                    setResult(991);
                    finish();
                } else {
                    Toast.makeText(UpdateActivity.this, "Delete failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}