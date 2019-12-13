package com.example.android.newsfeed;

public class NewsFeed {

    private String mName;
    private String mTitle;
    private String mDate;
    private String mUrl;
    private String mAuthor;

    public  NewsFeed (String name, String title, String date, String url, String author) {
        mName = name;
        mTitle = title;
        mDate = date;
        mUrl = url;
        mAuthor = author;

    }

    public String getName() {
        return mName;
    }

    public String getTitle() {
        return  mTitle;}

    public String getDate() {
        return mDate;
    }

    public String getUrl() {return  mUrl;}

    public String getAuthor() { return mAuthor;}
}
