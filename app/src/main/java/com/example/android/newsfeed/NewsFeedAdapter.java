package com.example.android.newsfeed;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NewsFeedAdapter extends ArrayAdapter<NewsFeed> {

    private static final String DATE_SEPARATOR = " T ";

    public NewsFeedAdapter (Context context, ArrayList<NewsFeed> earthquakes) {
        super(context, 0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listView = convertView;
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);

            NewsFeed currentNews = getItem(position);

            TextView sectionNameText = listView.findViewById(R.id.section_name);
            sectionNameText.setText(currentNews.getName());

            TextView newsTitle = listView.findViewById(R.id.news_title);
            newsTitle.setText(currentNews.getTitle());

            String newDate= currentNews.getDate().substring(0,10);

            String newTime= currentNews.getDate().substring(11,19);

            String newAuthor = currentNews.getAuthor().substring(20,29);

            TextView dateView = (TextView) listView.findViewById(R.id.date);
            dateView.setText(newDate);

            TextView timeView = (TextView) listView.findViewById(R.id.time);
            timeView.setText(newTime);

            TextView authorView = (TextView) listView.findViewById(R.id.author);
            authorView.setText(newAuthor);
        }
        return listView;
    }

}
