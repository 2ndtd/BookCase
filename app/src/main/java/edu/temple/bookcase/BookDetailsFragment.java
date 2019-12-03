package edu.temple.bookcase;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;
/**
 * CIS 3515 - Lab 9 BookCase-Audio
 * Toi Do 12/2/2019
 */
public class BookDetailsFragment extends Fragment {
    private static final String BOOK_KEY = "book";
    private TextView textView, progressText;
    private ImageView imageView;
    private String title, author, published;
    private Books pagerBooks;
    ImageButton playButton, stopButton, pauseButton;
    SeekBar seekBar;

    private OnBookInteractionListener mListener;
    Context context;

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
        playButton = view.findViewById(R.id.playButton);
        pauseButton = view.findViewById(R.id.pauseButton);
        stopButton = view.findViewById(R.id.stopButton);
        seekBar = view.findViewById(R.id.seekBar);
        progressText = view.findViewById(R.id.progressText);
        if(getArguments()!= null){
            displayBook(pagerBooks);
        }
        return view;
    }

    public void displayBook(final Books bookObj) {
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
        seekBar.setMax(bookObj.getDuration());

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ((OnBookInteractionListener) context).playAudio(bookObj.getId());
               ((OnBookInteractionListener) context).setProgress(progressHandler);
            }
        });
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((OnBookInteractionListener) context).pauseAudio();
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seekBar.setProgress(0);
                progressText.setText("0 s");
                ((OnBookInteractionListener) context).stopAudio();
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) {
                    progressText.setText(progress + "s");
                    ((OnBookInteractionListener) context).seekAudio(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    Handler progressHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            updateSeekbar(msg.what);
            return false;
        }
    });

    public void updateSeekbar(int currentTime){
        seekBar.setProgress(currentTime);
        Log.e("Progress", ":" + seekBar.getProgress());
        progressText.setText("" + currentTime + "s");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BookListFragment.OnBookInteraction) {
            mListener = (OnBookInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        this.context = context;
    }

    public interface OnBookInteractionListener{
        void playAudio(int id);
        void pauseAudio();
        void stopAudio();
        void seekAudio(int position);
        void setProgress(Handler progressHandler);
    }

}
