package com.example.tugas7ver2;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    DataHelper myDb;
    EditText nama, nim, kelas, id;
    Button btnAddData, btnViewAll, btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        myDb = new DataHelper(this);

        nama = (EditText) findViewById(R.id.et_nama);
        nim = (EditText) findViewById(R.id.et_nim);
        kelas = (EditText) findViewById(R.id.et_kelas);

        btnAddData = (Button) findViewById(R.id.btn_add);
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });
    }

    public void addData() {
        if (isEmpty(nama) || isEmpty(kelas) || isEmpty(nim)) {
            Toast.makeText(AddActivity.this, "Please fill the form", Toast.LENGTH_SHORT).show();
        } else {
            boolean isInserted = myDb.insertData(
                nama.getText().toString(),
                nim.getText().toString(),
                kelas.getText().toString()
            );

            if (isInserted == true) {
                nama.setText("");
                nim.setText("");
                kelas.setText("");

                nama.clearFocus();
                nim.clearFocus();
                kelas.clearFocus();
                Toast.makeText(AddActivity.this, "Data successfully added", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AddActivity.this, "Data already exist", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }
}