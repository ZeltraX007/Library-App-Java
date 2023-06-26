package com.example.mylibrary;

import static com.example.mylibrary.BookActivity.BOOK_ID_KEY;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BooksRecViewAdapter extends RecyclerView.Adapter<BooksRecViewAdapter.ViewHolder>{


    private static final String TAG = "BookRecViewAdapter";
    private static final String all_books= "allBooks";
    private static final String already_read = "alreadyRead";
    private static final String want_to_read = "wantToRead";
    private static final String curr_read = "currentlyReading";
    private static final String fav = "favourite";
    private ArrayList<Book> books = new ArrayList<>();
    private Context mContext;
    private String parentActivity;

    public BooksRecViewAdapter(Context mContext, String parentActivity) {
        this.mContext = mContext;
        this.parentActivity = parentActivity;
        updateBooks();
    }

    private void updateBooks() {
        switch (parentActivity) {
            case all_books:
                this.books = Utils.getInstance(mContext).getAllBooks();
                break;
            case want_to_read:
                this.books = Utils.getInstance(mContext).getWantToReadBooks();
                break;
            case already_read:
                this.books = Utils.getInstance(mContext).getAlreadyReadBooks();
                break;
            case fav:
                this.books = Utils.getInstance(mContext).getFavouriteBooks();
                break;
            case curr_read:
                this.books = Utils.getInstance(mContext).getCurrentlyReadingBooks();
                break;
            default:
                break;
        }
        this.notifyDataSetChanged();
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    public BooksRecViewAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_books, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        holder.txtName.setText(books.get(position).getName());
        Glide.with(mContext)
                .asBitmap()
                .load(books.get(position).getImageUrl())
                .into(holder.imgBook);
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,BookActivity.class);
                intent.putExtra(BOOK_ID_KEY,books.get(holder.getAdapterPosition()).getId());
                mContext.startActivity(intent);
            }
        });

        holder.txtAuthor.setText(books.get(position).getAuthor());
        holder.txtDesc.setText(books.get(position).getShortDesc());

//        holder.btnDown.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.expandedRelLayout.setVisibility(View.VISIBLE);
//                holder.btnDown.setVisibility(View.GONE);
//            }
//        });
//
//        holder.btnUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.expandedRelLayout.setVisibility(View.GONE);
//                holder.btnDown.setVisibility(View.VISIBLE);
//            }
//        });

        if(books.get(position).isExpanded()){
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.btnDown.setVisibility(View.GONE);
            holder.expandedRelLayout.setVisibility(View.VISIBLE);
        } else {
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.btnDown.setVisibility(View.VISIBLE);
            holder.expandedRelLayout.setVisibility(View.GONE);
        }

        switch (parentActivity) {
            case all_books:
                holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("Are you sure you want to delete " + books.get(holder.getAdapterPosition()).getName() + "?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name = books.get(holder.getAdapterPosition()).getName();
                                if (Utils.getInstance(mContext).removeAllBooks(books.get(holder.getAdapterPosition()))) {
                                    Toast.makeText(mContext, name + " removed", Toast.LENGTH_SHORT).show();
                                    updateBooks();
                                } else {
                                    Toast.makeText(mContext, "Something went wrong, try again!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        builder.create().show();
                    }
                });
                break;
            case already_read:
                holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("Are you sure you want to delete " + books.get(holder.getAdapterPosition()).getName() + "?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name = books.get(holder.getAdapterPosition()).getName();
                                if (Utils.getInstance(mContext).removeAlreadyRead(books.get(holder.getAdapterPosition()))) {
                                    Toast.makeText(mContext, name + " removed", Toast.LENGTH_SHORT).show();
                                    updateBooks();
                                } else {
                                    Toast.makeText(mContext, "Something went wrong, try again!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        builder.create().show();
                    }
                });
                break;
            case want_to_read:
                holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("Are you sure you want to delete " + books.get(holder.getAdapterPosition()).getName() + "?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name = books.get(holder.getAdapterPosition()).getName();
                                if (Utils.getInstance(mContext).removeWantRead(books.get(holder.getAdapterPosition()))) {
                                    Toast.makeText(mContext, name + " removed", Toast.LENGTH_SHORT).show();
                                    updateBooks();
                                } else {
                                    Toast.makeText(mContext, "Something went wrong, try again!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        builder.create().show();
                    }
                });
                break;
            case curr_read:
                holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("Are you sure you want to delete " + books.get(holder.getAdapterPosition()).getName() + "?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name = books.get(holder.getAdapterPosition()).getName();
                                if (Utils.getInstance(mContext).removeCurrentlyRead(books.get(holder.getAdapterPosition()))) {
                                    Toast.makeText(mContext, name + " removed", Toast.LENGTH_SHORT).show();
                                    updateBooks();
                                } else {
                                    Toast.makeText(mContext, "Something went wrong, try again!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        builder.create().show();
                    }
                });
                break;
            case fav:
                holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("Are you sure you want to delete " + books.get(holder.getAdapterPosition()).getName() + "?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name = books.get(holder.getAdapterPosition()).getName();
                                if (Utils.getInstance(mContext).removeFav(books.get(holder.getAdapterPosition()))) {
                                    Toast.makeText(mContext, name + " removed", Toast.LENGTH_SHORT).show();
                                    updateBooks();
                                } else {
                                    Toast.makeText(mContext, "Something went wrong, try again!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        builder.create().show();
                    }
                });
            default:

                break;
        }

    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CardView parent;
        private ImageView imgBook;
        private TextView txtName;
        private ImageView btnDown,btnUp,btnDelete;
        private RelativeLayout expandedRelLayout;
        private TextView txtAuthor, txtDesc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            imgBook = itemView.findViewById(R.id.imgBook);
            txtName = itemView.findViewById(R.id.txtName);
            btnDown = itemView.findViewById(R.id.btnDown);
            btnUp = itemView.findViewById(R.id.btnUp);
            expandedRelLayout = itemView.findViewById(R.id.expandedRelLayout);
            txtDesc = itemView.findViewById(R.id.txtDesc);
            txtAuthor = itemView.findViewById(R.id.txtAuthor);
            btnDelete = itemView.findViewById(R.id.btnDelete);

            btnDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Book book = books.get(getAdapterPosition());
                    book.setExpanded(!book.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            btnUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Book book = books.get(getAdapterPosition());
                    book.setExpanded(!book.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }


}
