package edu.temple.bookcase;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
/**
 * CIS 3515 - Lab 8 BookCase
 * Toi Do 11/15/2019
 */
public class BookAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Books> info ;

    BookAdapter(Context context, ArrayList<Books> info) {
        this.context = context;
        this.info = info;
    }

    @Override
    public int getCount() {
        return info.size();
    }

    @Override
    public Object getItem(int position) {
        return info.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView textView = new TextView(context);
        textView.setText(info.get(position).getTitle());
        textView.setTextSize(30);

        return textView;
    }
}
