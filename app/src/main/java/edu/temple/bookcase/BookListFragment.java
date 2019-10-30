package edu.temple.bookcase;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class BookListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String BOOK_KEY = "books";

    private String books;

    private OnBookInteractionListener mListener;

    public BookListFragment() {
        // Required empty public constructor
    }


    public static BookListFragment newInstance(String books, String param2) {
        BookListFragment fragment = new BookListFragment();
        Bundle args = new Bundle();
        args.putString(BOOK_KEY, books);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            books = getArguments().getString(BOOK_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_list,container,false);
        ListView listView = view.findViewById(R.id.listBook);
        final String[] books = this.getResources().getStringArray(R.array.booktitle);

        ArrayAdapter adapter = new ArrayAdapter((Context) mListener, android.R.layout.simple_list_item_1, books);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String book = (String) adapterView.getItemAtPosition(position);
                ((OnBookInteractionListener) mListener).onBookInteraction(book);
            }

        });
        return view;
    }
    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBookInteractionListener) {
            mListener = (OnBookInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBookInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnBookInteractionListener {
        void onBookInteraction(String book);
    }
}
