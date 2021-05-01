package com.example.tugas7ver2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton btnAddList;
    LinearLayout emptyState;

    DataHelper myDB;
    ArrayList<String> list_id, list_nama, list_nim, list_kelas;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.recyclerView);
        emptyState = findViewById(R.id.ll_emptyState);

        btnAddList = findViewById(R.id.fab);
        btnAddList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, AddActivity.class);
                startActivityForResult(intent, 88);
            }
        });

        myDB = new DataHelper(ListActivity.this);
        list_id = new ArrayList<>();
        list_nama = new ArrayList<>();
        list_nim = new ArrayList<>();
        list_kelas = new ArrayList<>();

        storeDataInArray();

        adapter = new Adapter(ListActivity.this, this, list_id, list_nama, list_nim, list_kelas);
        adapter.notifyDataSetChanged();

        recyclerView.setAdapter(adapter);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(ListActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 88 || requestCode == 99){
            recreate();
            if (resultCode == 990) {
                Toast.makeText(this, "Data successfully updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Data successfully deleted", Toast.LENGTH_SHORT).show();
            }
        }
    }

void storeDataInArray() {
    Cursor cursor = myDB.getAllData();
    if (cursor.getCount() == 0) {
        emptyState.setVisibility(View.VISIBLE);
    } else {
        while (cursor.moveToNext()){
            list_id.add(cursor.getString(0));
            list_nama.add(cursor.getString(1));
            list_nim.add(cursor.getString(2));
            list_kelas.add(cursor.getString(3));
        }
        emptyState.setVisibility(View.GONE);
    }
    }
}