package com.example.android.newsfeed;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsFeedActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<NewsFeed>> {

    private static final String LOG_TAG = NewsFeedActivity.class.getName();


    private static final String NEWS_REQUEST_URL =
            "https://content.guardianapis.com/search?api-key=88f98a50-5748-4b05-a1a8-c7d46a917734";

    private static final int NEWS_LOADER_ID = 1;

    private NewsFeedAdapter mAdapter;

    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView newsListView = (ListView) findViewById(R.id.list);

        mAdapter = new NewsFeedAdapter(this, new ArrayList<NewsFeed>());
        newsListView.setAdapter(mAdapter);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        newsListView.setEmptyView(mEmptyStateTextView);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                NewsFeed currentNews = mAdapter.getItem(position);

                Uri newsUrl = Uri.parse(currentNews.getUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUrl);

                startActivity(websiteIntent);
            }
        });

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);

        } else {
            View loadingIndicator = findViewById(R.id.loading_spinner);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);

        }
    }

    @Override
    public Loader<List<NewsFeed>> onCreateLoader(int i, Bundle bundle) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        // parse breaks apart the URI string that's passed into its parameter
        Uri baseUri = Uri.parse(NEWS_REQUEST_URL);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();

        // Append query parameter and its value. For example, the `format=geojson`
        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("date", "0,10");
        uriBuilder.appendQueryParameter("time", "11,19");
        uriBuilder.appendQueryParameter("author", "20,29");


        return new NewsFeedLoader(this, NEWS_REQUEST_URL);
    }


    @Override
    public void onLoadFinished(Loader<List<NewsFeed>> loader, List<NewsFeed> newsUpdate) {
        View loadingIndicator = findViewById(R.id.loading_spinner);
        loadingIndicator.setVisibility(View.GONE);

        mEmptyStateTextView.setText(R.string.no_news);

        mAdapter.clear();

        if (newsUpdate != null && !newsUpdate.isEmpty()) {
            mAdapter.addAll(newsUpdate);

        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsFeed>> loader) {
        mAdapter.clear();

    }
}
