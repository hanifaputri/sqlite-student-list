package com.example.tugas7ver2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    private Context context;
    private Activity activity;
    private ArrayList list_id, list_nama, list_nim, list_kelas;
    int position;

    Adapter(Activity activity,
            Context context,
            ArrayList list_id,
            ArrayList list_nama,
            ArrayList list_nim,
            ArrayList list_kelas){
        this.activity = activity;
        this.context = context;
        this.list_id = list_id;
        this.list_nama = list_nama;
        this.list_nim = list_nim;
        this.list_kelas = list_kelas;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_id, tv_nama, tv_kelas, tv_nim;
        LinearLayout ll_list;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_nama = itemView.findViewById(R.id.tv_nama);
            tv_nim = itemView.findViewById(R.id.tv_nim);
            tv_kelas = itemView.findViewById(R.id.tv_kelas);
            ll_list = itemView.findViewById(R.id.ll_list);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        this.position = position;

        holder.tv_id.setText(String.valueOf(list_id.get(position)));
        holder.tv_nama.setText(String.valueOf(list_nama.get(position)));
        holder.tv_nim.setText(String.valueOf(list_nim.get(position)));
        holder.tv_kelas.setText(String.valueOf(list_kelas.get(position)));

        holder.ll_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(list_id.get(position)));
                intent.putExtra("nama", String.valueOf(list_nama.get(position)));
                intent.putExtra("nim", String.valueOf(list_nim.get(position)));
                intent.putExtra("kelas", String.valueOf(list_kelas.get(position)));
                activity.startActivityForResult(intent, 99);
             }
        });
    }

    @Override
    public int getItemCount() {
        return list_id.size();
    }
}
