package edu.temple.bookcase;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

/**
 * CIS 3515 - Lab 8 BookCase
 * Toi Do 11/15/2019
 */
public class BookListFragment extends Fragment {

    private ListView listView;
    private Context c;
    ArrayList<Books> listBook;
    private Books book;
    private BookAdapter adapter;

    private OnBookInteractionListener mListener;
    public BookListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list,container,false);
        listView = view.findViewById(R.id.listBooks);
        listBook = new ArrayList<>();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBookInteractionListener) {
            mListener = (OnBookInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBookInteractionListener");
        }
        this.c = context;
    }

    public interface OnBookInteractionListener {
        void onBookInteraction(Books bookObj);
    }

    public void getBooks(final ArrayList<Books> bookArray){
        adapter = new BookAdapter(c, bookArray);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                book = bookArray.get(position);
                ((OnBookInteractionListener) c).onBookInteraction(book);
            }
        });

    }
}