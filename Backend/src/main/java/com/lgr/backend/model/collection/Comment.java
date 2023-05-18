package com.lgr.backend.model.collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lgr.backend.util.Result;
import lombok.Builder;
import lombok.Data;
//import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Li Gengrun
 * @date 2023/4/3 21:59
 */
@Data
@Builder
//@Document(collection = "Comment")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Comment {

    @JsonIgnore
    private String _id;

    private int commentId;//真实的ID

    private int userId;

    private int movieId;

    private long timeStamp; //评论时间

    private String content; //评论内容

    public Comment() {
    }

    public Comment(String _id, int commentId, int userId, int movieId, long timeStamp, String content) {
        this._id = _id;
        this.commentId = commentId;
        this.userId = userId;
        this.movieId = movieId;
        this.timeStamp = timeStamp;
        this.content = content;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
