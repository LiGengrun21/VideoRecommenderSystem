package com.lgr.backend.service.impl;

import com.lgr.backend.model.collection.Comment;
import com.lgr.backend.service.CommentService;
import com.lgr.backend.util.Result;
import org.springframework.stereotype.Service;

/**
 * @author Li Gengrun
 * @date 2023/4/13 17:53
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Override
    public Result getCommentListByMovieId(int movieId) {
        return null;
    }

    @Override
    public Result deleteComment(int commentId) {
        return null;
    }

    @Override
    public Result updateComment(Comment comment) {
        return null;
    }

    @Override
    public Result getCommentById(int commentId) {
        return null;
    }

    @Override
    public Result addComment(Comment comment) {
        return null;
    }
}
