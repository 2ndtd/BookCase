package edu.temple.bookcase;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
/**
 * CIS 3515 - Lab 7 BookCase
 * Toi Do 11/1/2019
 */

public class ViewPagerFragment extends Fragment {


    private ViewPager viewPager;
    PagerAdapter pagerAdapter;
    BookDetailsFragment newFragment;
    TextView textView;

    public ViewPagerFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_pager, container, false);
        textView = v.findViewById(R.id.textView);
        textView.setText("Swipe to see more");
        Resources res = this.getResources();
        final String[] bookList = res.getStringArray(R.array.booktitle);
        newFragment = new BookDetailsFragment();
        viewPager = v.findViewById(R.id.viewPager);
        pagerAdapter = new PagerAdapter(getChildFragmentManager());
        for(int j = 0; j < bookList.length; j++){
            newFragment = BookDetailsFragment.newInstance(bookList[j]);
            pagerAdapter.add(newFragment);
        }
        viewPager.setAdapter(pagerAdapter);
        return v;
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
        public Fragment getItem(int i) {
            return pagerFragments.get(i);
        }

        @Override
        public int getCount() {
            return pagerFragments.size();
        }
    }

}
