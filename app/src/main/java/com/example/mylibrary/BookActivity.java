package com.example.mylibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {

    public static final String BOOK_ID_KEY = "bookId";

    private ImageView imgBook2;
    private Button btnAddToCurr,btnWantToRead,btnAlreadyRead,btnAddToFav;
    private TextView txtBookName, txtAuthorName, txtPages, txtDescription;
    private FloatingActionButton fab_main, fabAddBook;
    private Animation fab_open, fab_close, fab_clock, fab_anticlock;
    TextView txtAddBook;
    Boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        initViews();

        Intent intent = getIntent();
        if(null!=intent){
            int bookId = intent.getIntExtra(BOOK_ID_KEY,-1);
            if(bookId != -1){
                Book incomingBook = Utils.getInstance(this).getBookById(bookId);
                if(null!=incomingBook) {
                    setData(incomingBook);

                    handleAlreadyRead(incomingBook);
                    handleCur(incomingBook);
                    handleWant(incomingBook);
                    handleFav(incomingBook);
                }
            }
        }
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
                Intent intent = new Intent(BookActivity.this,AddNewBookActivity.class);
                startActivity(intent);
            }
        });
    }

    private void handleFav(Book book) {
        ArrayList<Book> fav = Utils.getInstance(this).getFavouriteBooks();
        boolean existsInFav = false;
        for(Book b:fav){
            if(b.getId() == book.getId()){
                existsInFav = true;
                break;
            }
        }

        if(existsInFav){
            btnAddToFav.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            btnAddToFav.setText(R.string.remove);
            btnAddToFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Utils.getInstance(BookActivity.this).removeFav(book)){
                        Toast.makeText(BookActivity.this, "Book Removed from Favourites", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(BookActivity.this, "Something went wrong, Try again", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                }
            });
        } else {
            btnAddToFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Utils.getInstance(BookActivity.this).addToFav(book)){
                        Toast.makeText(BookActivity.this, "Book Added in Favourites", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(BookActivity.this, "Something went wrong, try again", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                }
            });
        }
    }

    private void handleWant(Book book) {
        ArrayList<Book> wantToRead = Utils.getInstance(this).getWantToReadBooks();
        boolean existsInWantToRead = false;
        for(Book b: wantToRead){
            if(b.getId() == book.getId()){
                existsInWantToRead = true;
                break;
            }
        }

        if(existsInWantToRead){
            btnAlreadyRead.setEnabled(false);
            btnAddToCurr.setEnabled(false);
            btnWantToRead.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            btnWantToRead.setText(R.string.remove);
            btnWantToRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Utils.getInstance(BookActivity.this).removeWantRead(book)){
                        Toast.makeText(BookActivity.this, "Book Removed from Wishlist", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(BookActivity.this, "Something went wrong, Try again", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                }
            });
        } else {
            btnWantToRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Utils.getInstance(BookActivity.this).addToWantRead(book)){
                        Toast.makeText(BookActivity.this, "Book Added in Wishlist", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(BookActivity.this, "Something went wrong, try again", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                }
            });
        }
    }

    private void handleCur(Book book) {
        ArrayList<Book> currentlyReading = Utils.getInstance(this).getCurrentlyReadingBooks();
        boolean existsInCurrentlyReading = false;
        for(Book b:currentlyReading){
            if(b.getId() == book.getId()){
                existsInCurrentlyReading = true;
                break;
            }
        }
        
        if(existsInCurrentlyReading){
            btnAlreadyRead.setEnabled(false);
            btnWantToRead.setEnabled(false);
            btnAddToCurr.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            btnAddToCurr.setText(R.string.remove);
            btnAddToCurr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Utils.getInstance(BookActivity.this).removeCurrentlyRead(book)){
                        Toast.makeText(BookActivity.this, "Book Removed from Currently Reading", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(BookActivity.this, "Something went wrong, Try agian", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                }
            });
        } else {
            btnAddToCurr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Utils.getInstance(BookActivity.this).addToCurrentlyRead(book)){
                        Toast.makeText(BookActivity.this, "Book Added in Currently Reading", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(BookActivity.this, "Something went wrong, try again", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                }
            });
        }
    }

    private void handleAlreadyRead(Book book) {
        ArrayList<Book> alreadyReadBooks = Utils.getInstance(this).getAlreadyReadBooks();
        boolean existsInAlreadyReadBooks = false;
        for(Book b:alreadyReadBooks){
            if (b.getId() == book.getId()) {
                existsInAlreadyReadBooks = true;
                break;
            }
        }

        if(existsInAlreadyReadBooks){
            btnWantToRead.setEnabled(false);
            btnAddToCurr.setEnabled(false);
            btnAlreadyRead.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            btnAlreadyRead.setText(R.string.remove);
            btnAlreadyRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Utils.getInstance(BookActivity.this).removeAlreadyRead(book)){
                        Toast.makeText(BookActivity.this, "Book Removed from Already Read", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(BookActivity.this, "Something went wrong, Try again", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                }
            });
        } else {
            btnAlreadyRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Utils.getInstance(BookActivity.this).addToAlreadyRead(book)){
                        Toast.makeText(BookActivity.this, "Book Added in Already Read", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(BookActivity.this, "Something went wrong, try again", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                }
            });
        }
    }

    private void setData(Book book){
        Glide.with(this)
                .asBitmap().load(book.getImageUrl())
                .into(imgBook2);

        txtBookName.setText(book.getName());
        txtAuthorName.setText(book.getAuthor());
        txtPages.setText(String.valueOf(book.getPages()));
        txtDescription.setText(book.getLongDesc());
    }

    private void initViews() {
        imgBook2 = findViewById(R.id.imgBook2);

        btnAddToCurr = findViewById(R.id.btnAddToCurr);
        btnWantToRead = findViewById(R.id.btnWantToRead);
        btnAlreadyRead = findViewById(R.id.btnAlreadyRead);
        btnAddToFav = findViewById(R.id.btnAddToFav);

        txtBookName = findViewById(R.id.txtBookName);
        txtAuthorName = findViewById(R.id.txtAuthorName);
        txtPages = findViewById(R.id.txtPages);
        txtDescription = findViewById(R.id.txtDescription);
    }
}