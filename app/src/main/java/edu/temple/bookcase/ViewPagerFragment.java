package edu.temple.bookcase;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
/**
 * CIS 3515 - Lab 10 BookCase-Audio
 * Toi Do 12/12/2019
 */
public class ViewPagerFragment extends Fragment {

    public ViewPagerFragment() {
    }
    ViewPager viewPager;
    PagerAdapter pagerAdapter;
    BookDetailsFragment newFragment;
    Books bookObj;
    ArrayList<Books> books;
    ArrayList<BookDetailsFragment> bookDetailsFragments;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);
        pagerAdapter = new PagerAdapter(getFragmentManager());
        books = new ArrayList<>();
        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(pagerAdapter);
        bookDetailsFragments = new ArrayList<>();

        return view;
    }

    public void addPager(final ArrayList<Books> bookArray){
        for(int i = 0; i < bookArray.size(); i++) {
            bookObj = bookArray.get(i);
            newFragment = BookDetailsFragment.newInstance(bookObj);
            bookDetailsFragments.add(newFragment);
        }
        pagerAdapter.addBooks(bookDetailsFragments);
    }

    class PagerAdapter extends FragmentStatePagerAdapter{

        ArrayList<BookDetailsFragment> pagerFragments;

        public PagerAdapter(FragmentManager fm) {
            super(fm);
            pagerFragments = new ArrayList<>();
        }
        public void addBooks(ArrayList<BookDetailsFragment> books) {
            pagerFragments.clear();
            pagerFragments.addAll(books);
            notifyDataSetChanged();
        }
        public void add(BookDetailsFragment fragment){
            pagerFragments.add(fragment);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public Fragment getItem(int i) {
            return pagerFragments.get(i);
        }

        @Override
        public int getCount() {
            return pagerFragments.size();
        }
    }



}
