package com.lgr.backend.service;

import com.lgr.backend.model.collection.Comment;
import com.lgr.backend.util.Result;

/**
 * @author Li Gengrun
 * @date 2023/4/13 17:53
 */
public interface CommentService{
    Result getCommentListByMovieId(int movieId);

    Result deleteComment(int commentId);

    Result updateComment(Comment comment);

    Result getCommentById(int commentId);

    Result addComment(Comment comment);
}
