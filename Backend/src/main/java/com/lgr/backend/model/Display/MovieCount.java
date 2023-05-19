package com.lgr.backend.model.Display;

/**
 * @author Li Gengrun
 * @date 2023/5/19 13:50
 */
public class MovieCount {

    private String movieName;

    private long count;

    public MovieCount() {
    }

    public MovieCount(String movieName, long count) {
        this.movieName = movieName;
        this.count = count;
    }


    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
