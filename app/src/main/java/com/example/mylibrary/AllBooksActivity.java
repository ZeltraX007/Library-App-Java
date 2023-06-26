package com.example.mylibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AllBooksActivity extends AppCompatActivity {

    private BooksRecViewAdapter adapter;
    private RecyclerView booksRecView;

    private FloatingActionButton fab_main, fabAddBook;
    private Animation fab_open, fab_close, fab_clock, fab_anticlock;
    TextView txtAddBook;
    Boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books);

//        overridePendingTransition(R.anim.slide_id,R.anim.slide_out);

        adapter = new BooksRecViewAdapter(this,"allBooks");
        booksRecView = findViewById(R.id.booksRecView);

        booksRecView.setAdapter(adapter);
        booksRecView.setLayoutManager(new LinearLayoutManager(this));

//        ArrayList<Book> books = new ArrayList<>();

        adapter.setBooks(Utils.getInstance(this).getAllBooks());

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
                Intent intent = new Intent(AllBooksActivity.this,AddNewBookActivity.class);
                startActivity(intent);
            }
        });
    }

//    @Override
//    public void finish() {
//        super.finish();
//        overridePendingTransition(R.anim.slide_out,R.anim.slide_id);
//    }
}