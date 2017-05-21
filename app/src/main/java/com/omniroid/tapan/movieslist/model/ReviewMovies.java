package com.omniroid.tapan.movieslist.model;

import java.io.Serializable;

/**
 * Created by DELL on 5/17/2017.
 */

public class ReviewMovies implements Serializable {

    String author;
    String content;
    String url;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
