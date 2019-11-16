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
 * CIS 3515 - Lab 8 BookCase
 * Toi Do 11/15/2019
 */
public class ViewPagerFragment extends Fragment {

    public ViewPagerFragment() {
    }
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private BookDetailsFragment newFragment;
    private Books bookObj;
    private ArrayList<Books> books;


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

        return view;
    }

    void addPager(final ArrayList bookArray){
        books.clear();
        books.addAll(bookArray);
        for(int i = 0; i < books.size(); i++) {
            bookObj = books.get(i);
            newFragment = BookDetailsFragment.newInstance(bookObj);
            pagerAdapter.add(newFragment);
        }
        pagerAdapter.getItemPosition(bookObj);
        pagerAdapter.notifyDataSetChanged();
    }

    class PagerAdapter extends FragmentStatePagerAdapter{

        ArrayList<BookDetailsFragment> pagerFragments;

        public PagerAdapter(FragmentManager fm) {
            super(fm);
            pagerFragments = new ArrayList<>();
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
