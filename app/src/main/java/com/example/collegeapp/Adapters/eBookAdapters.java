package com.example.collegeapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeapp.MainActivity;
import com.example.collegeapp.Models.eBookModels;
import com.example.collegeapp.NoteDetails;
import com.example.collegeapp.R;

import java.util.ArrayList;

public class eBookAdapters extends RecyclerView.Adapter<eBookAdapters.EVieholder>{
    ArrayList<eBookModels>list;
    Context context;

    public eBookAdapters(ArrayList<eBookModels> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public EVieholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_ebook,parent,false);
        return new EVieholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EVieholder holder, int position) {
       final eBookModels models = list.get(position);
       holder.title.setText(models.getCoursetitle());

//       holder.download.setOnClickListener(v->{
//           Intent intent = new Intent(Intent.ACTION_VIEW);
//           intent.setData(Uri.parse(models.getPdfUrl()));
//           context.startActivity(intent);
//       });

       holder.title.setOnClickListener(v->{
           context.startActivity(new Intent(context.getApplicationContext(), MainActivity.class));
       });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class EVieholder extends RecyclerView.ViewHolder{
    TextView title,pdfurl;
    ImageView download;
        public EVieholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.coursetitle);
            download = itemView.findViewById(R.id.download);
        }
    }
}
