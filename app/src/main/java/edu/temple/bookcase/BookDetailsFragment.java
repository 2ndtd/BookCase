package edu.temple.bookcase;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;
/**
 * CIS 3515 - Lab 8 BookCase
 * Toi Do 11/15/2019
 */
public class BookDetailsFragment extends Fragment {
    private static final String BOOK_KEY = "book";
    private TextView textView;
    private ImageView imageView;
    private String title, author, published;
    private Books pagerBooks;
    private BookListFragment.OnBookInteractionListener mListener;

    public BookDetailsFragment() {
    }

    static BookDetailsFragment newInstance(Books bookList) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(BOOK_KEY, bookList);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pagerBooks = getArguments().getParcelable(BOOK_KEY);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_details, container, false);
        textView = view.findViewById(R.id.bookTitle);
        imageView = view.findViewById(R.id.bookImage);
        if(getArguments()!= null){
            displayBook(pagerBooks);
        }
        return view;
    }

    public void displayBook(Books bookObj) {
        author = bookObj.getAuthor();
        title = bookObj.getTitle();
        published = bookObj.getPublished();
        textView.setText(" \"" + title + "\" ");
        textView.append(", " + author);
        textView.append(", " + published);
        textView.setTextSize(25);
        textView.setTextColor(Color.BLACK);
        String imageURL = bookObj.getCoverURL();
        Picasso.get().load(imageURL).into(imageView);
    }
}