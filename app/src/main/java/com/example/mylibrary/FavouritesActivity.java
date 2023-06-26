package com.example.mylibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FavouritesActivity extends AppCompatActivity {

    private FloatingActionButton fab_main, fabAddBook;
    private Animation fab_open, fab_close, fab_clock, fab_anticlock;
    TextView txtAddBook;
    Boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        RecyclerView recyclerView = findViewById(R.id.favRecyclerView);
        BooksRecViewAdapter adapter = new BooksRecViewAdapter(this,"favourite");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setBooks(Utils.getInstance(this).getFavouriteBooks());

        fab_main = findViewById(R.id.fab);
        fabAddBook = findViewById(R.id.fabAddBook);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_clock);
        fab_anticlock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_anticlock);
        txtAddBook = (TextView) findViewById(R.id.txtAdd);
        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isOpen) {

                    txtAddBook.setVisibility(View.INVISIBLE);
                    fabAddBook.startAnimation(fab_close);
                    fab_main.startAnimation(fab_anticlock);
                    fabAddBook.setClickable(false);
                    isOpen = false;
                } else {
                    txtAddBook.setVisibility(View.VISIBLE);
                    fabAddBook.startAnimation(fab_open);
                    fab_main.startAnimation(fab_clock);
                    fabAddBook.setClickable(true);
                    isOpen = true;
                }

            }
        });

        fabAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FavouritesActivity.this,AddNewBookActivity.class);
                startActivity(intent);
            }
        });
    }

}