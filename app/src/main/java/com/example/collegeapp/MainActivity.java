package com.example.collegeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.collegeapp.databinding.ActivityMainBinding;
import com.example.collegeapp.ui.fragment_about;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ActionBarDrawerToggle toggle;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference,head,subhead;
    private String clgnames,clgdesc;
    private ArrayList<String>departmentlist;
    private ArrayAdapter adapter;


//    fetch data from database
    private void showdata(){
        head = databaseReference.child("clgdata").child("name");
        subhead = databaseReference.child("clgdata").child("desc");
        head.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clgnames = snapshot.getValue(String.class);
                binding.clgname.setText(clgnames);
//                Toast.makeText(MainActivity.this, clgnames, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        subhead.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clgdesc = snapshot.getValue(String.class);
                binding.subclgdesc.setText(clgdesc);
//                Toast.makeText(MainActivity.this, clgdesc, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

//    department clg
    private void showDepartment(){
      String [] city = {"computer science","civil engineering","Information technology","computer science","civil engineering","Information technology"};

      adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,city);
      binding.department.setAdapter(adapter);
    }

    private void VideoLecture(String url){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        setSupportActionBar(binding.toolbar);

        binding.toolbar.setTitle("College App");

//        show database data from textview
         showdata();

        showDepartment();

        toggle = new ActionBarDrawerToggle(this,binding.drawer,binding.toolbar,R.string.open,R.string.close);
        binding.drawer.addDrawerListener(toggle);
        toggle.syncState();


//       slider image
        List<SlideModel> imagelist = new ArrayList<>();
        imagelist.add(new SlideModel(R.drawable.ctevt,null));
        imagelist.add(new SlideModel(R.drawable.ctevt1,null));
        imagelist.add(new SlideModel(R.drawable.ctevt2,null));
        binding.imageSlider.setImageList(imagelist, ScaleTypes.FIT);


//        listview item click
       binding.department.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Toast.makeText(MainActivity.this, "item click"+i, Toast.LENGTH_SHORT).show();
           }
       });

//       onclick in drawer items
        binding.navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                binding.drawer.closeDrawer(GravityCompat.START);
                Fragment fragment = new fragment_about();
                switch (item.getItemId()){
                    case R.id.home:
                        Toast.makeText(MainActivity.this, "home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.about:
//                        Toast.makeText(MainActivity.this, "about", Toast.LENGTH_SHORT).show();
                        fragment = new fragment_about();
                        loadFragment(fragment);
                        break;
                    case R.id.book:
                        startActivity(new Intent(MainActivity.this,ebook.class));
                        break;
                    case R.id.website:
                        VideoLecture("https://coderkharal.blogspot.com/");
                        Toast.makeText(MainActivity.this, "website", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.video:
                        VideoLecture("https://www.youtube.com/channel/UCF5oO43y67XYOfJRiPif1rw/videos");
                        Toast.makeText(MainActivity.this, "video lecture", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.share:
                        shareitem();
                        Toast.makeText(MainActivity.this, "share app to social media", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.exit:
                        finish();
                        Toast.makeText(MainActivity.this, "exit", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,fragment).commit();
        binding.drawer.closeDrawer(GravityCompat.START);
        fragmentTransaction.addToBackStack(null);
    }


    @Override
    public void onBackPressed() {
        if(binding.drawer.isDrawerOpen(GravityCompat.START)){
            binding.drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    //    share to social media
    private void shareitem(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,clgdesc);
        startActivity(intent);
        Toast.makeText(this, clgdesc, Toast.LENGTH_SHORT).show();
    }


//   insert data from database
//    private void insertClgdata(){
//        DatabaseReference head,subhead;
//        databaseReference = databaseReference.child("clgdata");
//        head = databaseReference.child("name");
//        head.setValue("Hello world");
//        subhead = databaseReference.child("desc");
//        subhead.setValue("Hello world");
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}