package com.example.api;

import java.util.List;

import Models.NewsHeadlines;

public interface OnFetchDataListener <NewsApiResponse>{
    void onFetchData(List<NewsHeadlines>list);
    void onError(String message);
}
