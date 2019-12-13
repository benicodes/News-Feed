package com.example.android.newsfeed;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;

import java.util.List;

class NewsFeedLoader extends AsyncTaskLoader<List<NewsFeed>> {
    /**
     * Tag for log messages
     */
    private static final String LOG_TAG = NewsFeedLoader.class.getName();

    /**
     * Query URL
     */
    private String mUrl;

    public NewsFeedLoader(Context context, String url) {

        super(context);
        mUrl=url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<NewsFeed> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        List<NewsFeed> newsUpdate = QueryUtils.fetchNewsData(mUrl);
        return newsUpdate;
    }
}

