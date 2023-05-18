package com.lgr.backend.model.collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Li Gengrun
 * @date 2023/5/9 22:13
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Rating {

    @JsonIgnore
    private String _id;

    private int movieId;

    private int userId;

    private int score;

    private int timeStamp;

    public Rating() {
    }

    public Rating(String _id, int movieId, int userId, int score, int timeStamp) {
        this._id = _id;
        this.movieId = movieId;
        this.userId = userId;
        this.score = score;
        this.timeStamp = timeStamp;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }
}
