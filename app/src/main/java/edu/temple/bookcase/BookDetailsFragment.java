package edu.temple.bookcase;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * CIS 3515 - Lab 9 BookCase-Audio
 * Toi Do 12/2/2019
 */
public class BookDetailsFragment extends Fragment {
    public static final String BOOK_KEY = "book";
    TextView textView, progressText;
    ImageView imageView;
    int currentTime;
    String title, author, published;
    Books pagerBooks;
    ImageButton playButton, stopButton, pauseButton;
    SeekBar seekBar;
    ProgressBar progressBar;
    File file;
    Button downloadButton, deleteButton;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private OnBookInteractionListener mListener;
    Context context;

    public BookDetailsFragment() {
    }

    public static BookDetailsFragment newInstance(Books bookList) {
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
        downloadButton = view.findViewById(R.id.downloadButton);
        deleteButton = view.findViewById(R.id.deleteButton);
        seekBar = view.findViewById(R.id.seekBar);
        progressBar = view.findViewById(R.id.progressBar);
        progressText = view.findViewById(R.id.progressText);
        preferences = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = preferences.edit();
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
                currentTime = preferences.getInt("SAVED_PROGRESS" + bookObj.getId(), 0);
                if(currentTime <= 10){
                    currentTime = 0;
                } else {
                    currentTime = currentTime - 10;
                }
                Log.d("Current Time", String.valueOf(currentTime));
                if(file != null){
                    Toast.makeText(getActivity(), "Playing Audio Book File", Toast.LENGTH_SHORT).show();
                    //((BookDetailsInterface) c).playBookFile(file);
                    ((OnBookInteractionListener) context).playAudioFilePosition(file, currentTime);
                }else {
                    Toast.makeText(getActivity(), "Streaming Audio Book", Toast.LENGTH_SHORT).show();
                    //((BookDetailsInterface) c).playAudio(bookObj.getId());
                    ((OnBookInteractionListener) context).playAudioPosition(bookObj.getId(), currentTime);
                }
            }
            //((OnBookInteractionListener) context).playAudio(bookObj.getId());
            //((OnBookInteractionListener) context).setProgress(progressHandler);


        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putInt("SAVED_PROGRESS" + bookObj.getId(), seekBar.getProgress());
                editor.apply();
                ((OnBookInteractionListener) context).pauseAudio();
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putInt("SAVED_PROGRESS" + bookObj.getId(), 0);
                editor.apply();
                seekBar.setProgress(0);
                progressText.setText("0s");
                ((OnBookInteractionListener) context).stopAudio();
            }
        });
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(file == null) {
                    Toast.makeText(getActivity(), "Downloading...", Toast.LENGTH_LONG).show();
                    int bookId = bookObj.getId();
                    downloadBook(bookId);
                } else {
                    Toast.makeText(getActivity(), "Already Downloaded", Toast.LENGTH_SHORT).show();
                }
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(file != null) {
                    file.delete();
                    file = null;
                    Toast.makeText(getActivity(), "File Deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "File does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) {
                    updateSeekbar(currentTime);
                    progressText.setText("" + progress + "s");
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



    public void updateSeekbar(int currentTime){
        seekBar.setProgress(currentTime);
        Log.d("Progress", ":" + seekBar.getProgress());
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
    public void downloadBook(final int search) {
        new Thread() {
            public void run() {
                try {
                    String urlString = "https://kamorris.com/lab/audlib/download.php?id=" + search;
                    URL url = new URL(urlString);
                    InputStream inputStream = url.openStream();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int num;
                    while((num = inputStream.read(buffer)) > 0){
                        byteArrayOutputStream.write(buffer, 0, num);
                    }
                    file = new File(getActivity().getFilesDir(), String.valueOf(search));
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(byteArrayOutputStream.toByteArray());

                    Log.d("File downloaded", file.toString());

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public interface OnBookInteractionListener{
        void playBookFile(File file);
        void playAudio(int id);
        void pauseAudio();
        void stopAudio();
        void seekAudio(int position);
        void setProgress(Handler progressHandler);
        void playAudioPosition(int id, int position);
        void playAudioFilePosition(File file, int position);
    }


}
