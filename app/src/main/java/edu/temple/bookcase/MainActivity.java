package edu.temple.bookcase;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * CIS 3515 - Lab 8 BookCase
 * Toi Do 11/15/2019
 */
public class MainActivity extends AppCompatActivity implements BookListFragment.OnBookInteractionListener {

    boolean singlePane;
    ViewPagerFragment viewPagerFragment;
    BookDetailsFragment bookDetailsFragment;
    BookListFragment bookListFragment;
    EditText infoText;
    Button button;
    JSONArray bookArray;
    String info;
    ArrayList<Books> listBook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        infoText = findViewById(R.id.infoText);
        button = findViewById(R.id.searchButton);
        listBook = new ArrayList<>();

        singlePane = findViewById(R.id.container2) == null;
        bookDetailsFragment = new BookDetailsFragment();
        bookListFragment = new BookListFragment();
        viewPagerFragment = new ViewPagerFragment();

        if(!singlePane){
            addFragment(bookListFragment, R.id.container1);
            addFragment(bookDetailsFragment, R.id.container2);
        } else {
            addFragment(viewPagerFragment, R.id.container3);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info = infoText.getText().toString();
                searchBook(info);
            }
        });

    }

    public void addFragment(Fragment fragment, int id){
        getSupportFragmentManager().
                beginTransaction().
                replace(id, fragment).
                addToBackStack(null).
                commit();
    }
    public void searchBook(final String search) {
        new Thread() {
            public void run() {
                try {
                    String urlString = "https://kamorris.com/lab/audlib/booksearch.php?search=" + search;
                    URL url = new URL(urlString);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                    StringBuilder builder = new StringBuilder();
                    String tmpString;
                    while ((tmpString = reader.readLine()) != null) {
                        builder.append(tmpString);
                    }
                    Message msg = Message.obtain();
                    msg.obj = builder.toString();
                    urlHandler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    Handler urlHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            try {
                bookArray = new JSONArray((String) msg.obj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            listBook.clear();
            for(int i = 0; i < bookArray.length(); i++){
                try {
                    listBook.add(new Books(bookArray.getJSONObject(i)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if(singlePane) {
                viewPagerFragment.addPager(listBook);
            } else {
                bookListFragment.getBooks(listBook);
            }
            return false;
        }
    });
    @Override
    public void onBookInteraction(Books bookObj) {
        bookDetailsFragment.displayBook(bookObj);
    }
}

