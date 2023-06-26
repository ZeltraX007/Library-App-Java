package com.example.mylibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AddNewBookActivity extends AppCompatActivity {

    private EditText editTitle,editAuthor,editPages, editUrl, editShort, editLong;
    private Button btnSubmit;

    public AddNewBookActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_book);

        initViews();

        ArrayList<Book> allBooks = Utils.getInstance(this).getAllBooks();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editAuthor.getText().toString().equals("") || editTitle.getText().toString().equals("") || editPages.getText().toString().equals("") || editShort.getText().toString().equals("") || editLong.getText().toString().equals(""))
                    Toast.makeText(AddNewBookActivity.this, "Fill in all the Details", Toast.LENGTH_SHORT).show();
                else {
                    int count=0;
                    for(Book b:allBooks){
                        count++;
                    }
                    Book book = new Book(++count,editTitle.getText().toString(),editAuthor.getText().toString(),Integer.parseInt(editPages.getText().toString()),
                            editUrl.getText().toString(),editShort.getText().toString(),editLong.getText().toString());
                    if(Utils.getInstance(AddNewBookActivity.this).addToAllBooks(book)){
                        Toast.makeText(AddNewBookActivity.this, "Book added Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddNewBookActivity.this, "Something went wrong, Try Again", Toast.LENGTH_SHORT).show();
                    }
                    editAuthor.getText().clear();
                    editTitle.getText().clear();
                    editPages.getText().clear();
                    editShort.getText().clear();
                    editLong.getText().clear();
                }
            }
        });
    }

    private void initViews() {
        editTitle = findViewById(R.id.editTitle);
        editAuthor = findViewById(R.id.editAuthor);
        editPages = findViewById(R.id.editPages);
        editShort = findViewById(R.id.editShort);
        editUrl = findViewById(R.id.editUrl);
        editLong = findViewById(R.id.editLong);
        btnSubmit =(Button) findViewById(R.id.btnSubmit);
    }
}