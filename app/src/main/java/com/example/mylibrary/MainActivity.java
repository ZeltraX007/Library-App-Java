package com.example.mylibrary;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private Button btnAllBooks,btnCurrent, btnRead, btnWish, btnFav, btnAbout;
    private FloatingActionButton fab_main, fabAddBook;
    private Animation fab_open, fab_close, fab_clock, fab_anticlock;
    private ConstraintLayout outside;
    TextView txtAddBook;
    Boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        btnAllBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AllBooksActivity.class);
                startActivity(intent);
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AlreadyReadBookActivity.class);
                startActivity(intent);
            }
        });

        btnCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CurrentlyReadingActivity.class);
                startActivity(intent);
            }
        });

        btnWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WantToReadActivity.class);
                startActivity(intent);
            }
        });

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FavouritesActivity.class);
                startActivity(intent);
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(getString(R.string.app_name));
                builder.setMessage("Designed and Developed by Aritra");
                builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.setPositiveButton("Visit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO: show the website
                        Intent intent = new Intent(MainActivity.this, WebActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setCancelable(true);
                builder.create().show();
            }
        });



        Utils.getInstance(this);

//        fabAdd = findViewById(R.id.fabAdd);
//        fabAddBook = findViewById(R.id.fabAddBook);
//        txtAddBook = findViewById(R.id.txtAddBook);
//        isAllFabVisible = false;
//        fabAddBook.setVisibility(View.GONE);
//        txtAddBook.setVisibility(View.GONE);
//        fabAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(!isAllFabVisible){
//                    showFABMenu();
//                }else{
//                    closeFABMenu();
//                    fabAddBook.setVisibility(View.GONE);
//                    txtAddBook.setVisibility(View.GONE);
//                }
//            }
//        });


//        fabAddBook.setVisibility(View.GONE);
//        txtAddBook.setVisibility(View.GONE);
//
//        isAllFabsVisible = false;
//        fabAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(!isAllFabsVisible){
//                    fabAddBook.setVisibility(View.VISIBLE);
//                    txtAddBook.setVisibility(View.VISIBLE);
//                } else {
//                    fabAddBook.setVisibility(View.GONE);
//                    txtAddBook.setVisibility(View.GONE);
//                }
//                isAllFabsVisible = !isAllFabsVisible;
//            }
//        });

        fab_main = findViewById(R.id.fab);
        fabAddBook = findViewById(R.id.fabAddBook);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_clock);
        fab_anticlock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_anticlock);
        txtAddBook = (TextView) findViewById(R.id.txtAdd);
        outside = findViewById(R.id.outside);
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
                Intent intent = new Intent(MainActivity.this,AddNewBookActivity.class);
                startActivity(intent);
            }
        });

        outside.setOnTouchListener(new View.OnTouchListener() {
            @Override
                public boolean onTouch(View v, MotionEvent event) {
                txtAddBook.setVisibility(View.INVISIBLE);
                fabAddBook.startAnimation(fab_close);
                fab_main.startAnimation(fab_anticlock);
                fabAddBook.setClickable(false);
                isOpen = false;
                return false;
            }
        });

    }

//    private void showFABMenu(){
//        isAllFabVisible =true;
//        fabAddBook.setVisibility(View.VISIBLE);
//        txtAddBook.setVisibility(View.VISIBLE);
//        fabAddBook.animate().translationY(-getResources().getDimension(R.dimen.standard_65));
//        txtAddBook.animate().translationY(-getResources().getDimension(R.dimen.standard_65));
//    }

//    private void closeFABMenu(){
//        isAllFabVisible =false;
//        fabAddBook.animate().translationY(0);
//        txtAddBook.animate().translationY(0);
//    }
//
    private void initViews() {
        btnAllBooks = findViewById(R.id.btnAllBooks);
        btnCurrent = findViewById(R.id.btnCurrent);
        btnRead = findViewById(R.id.btnRead);
        btnWish = findViewById(R.id.btnWish);
        btnFav = findViewById(R.id.btnFav);
        btnAbout = findViewById(R.id.btnAbout);
    }
}