package com.lgr.backend.controller;

import com.lgr.backend.model.collection.Comment;
import com.lgr.backend.service.CommentService;
import com.lgr.backend.util.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Li Gengrun
 * @date 2023/5/9 16:01
 */
@Tag(name="Comment",description = "评论模块")
@RestController
@RequestMapping("/comment")
@CrossOrigin("*")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Operation(summary = "视频详情页显示评论")
    @ResponseBody
    @GetMapping("/movie")
    public Result getCommentListByMovieId(int movieId){
        return commentService.getCommentListByMovieId(movieId);
    }

    @Operation(summary = "根据评论ID删除评论",description = "逻辑删除")
    @ResponseBody
    @DeleteMapping()
    public Result deleteComment(int commentId){
        return commentService.deleteComment(commentId);
    }

    @Operation(summary = "根据评论ID更新评论")
    @ResponseBody
    @PutMapping()
    public Result updateComment(Comment comment){
        return commentService.updateComment(comment);
    }

    @Operation(summary = "根据评论ID获取评论")
    @ResponseBody
    @GetMapping()
    public Result getCommentById(int commentId){
        return commentService.getCommentById(commentId);
    }

    @Operation(summary = "添加评论")
    @ResponseBody
    @PostMapping()
    public Result addComment(Comment comment){
        return commentService.addComment(comment);
    }
}
