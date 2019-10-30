package edu.temple.bookcase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.fragment.app.Fragment;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements BookListFragment.OnBookInteractionListener {

    boolean singlePane;
    ViewPagerFragment viewPagerFragment;
    BookDetailsFragment bookDetailsFragment;
    BookListFragment bookListFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    }

    public void addFragment(Fragment fragment, int id){
        getSupportFragmentManager().
                beginTransaction().
                replace(id, fragment).
                addToBackStack(null).
                commit();
    }

    @Override
    public void onBookInteraction(String book) {
        bookDetailsFragment.displayBook(book);
    }
}

