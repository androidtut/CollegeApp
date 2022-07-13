package com.example.collegeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class NoteDetails extends AppCompatActivity {
  PDFView pdfView;
  String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        pdfView = findViewById(R.id.pdfView);



//        Intent intent = getIntent();
//        url = intent.getStringExtra("PdfUrl");

//        pdfView.fromAsset(url)
//                .password(null)
//                .pages(0, 2, 1, 3, 3, 3)
//                .load();

    }
}