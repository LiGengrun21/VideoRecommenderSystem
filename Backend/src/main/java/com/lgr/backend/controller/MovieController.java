package com.lgr.backend.controller;

import com.lgr.backend.model.collection.Movie;
import com.lgr.backend.service.MovieService;
import com.lgr.backend.util.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Li Gengrun
 * @date 2023/4/4 10:40
 */
@Tag(name="Movie",description = "视频模块")
@RestController
@RequestMapping("/movie")
@CrossOrigin("*")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @Operation(summary = "电影模糊搜索", description = "通过视频（电影）名称搜索")
    @ResponseBody
    @GetMapping("/search")
    public Result searchMovies(@RequestParam("movieName") String string){
        return movieService.searchMovies(string);
    }

    @Operation(summary = "根据ID获取电影信息",description = "在搜索页面和首页里点击电影，跳转到电影详情页，使用本接口获取改电影的数据,然后展示；或者在视频详情页里获取电影信息以及资源")
    @ResponseBody
    @GetMapping("/info")
    public Result getMovieInfo(@RequestParam("movieId") int movieId){
        return movieService.getMovieInfo(movieId);
    }

    @Operation(summary = "获取个性化推荐结果",description = "返回20部电影")
    @ResponseBody
    @GetMapping("/recommendation/cf")
    public Result getCFRec(@RequestParam("userId") int userId){
        return movieService.getCFRec(userId);
    }

    @Operation(summary = "获取最多评价的推荐结果",description = "返回6部电影")
    @ResponseBody
    @GetMapping("/recommendation/mostViewed")
    public Result getMostViewedRec(){
        return movieService.getMostViewedRec();
    }

    @Operation(summary = "获取最高评分的推荐结果",description = "返回6部电影")
    @ResponseBody
    @GetMapping("/recommendation/topRated")
    public Result getTopRatedRec(){
        return movieService.getTopRatedRec();
    }

    @Operation(summary = "管理员添加一个新电影",description = "视频管理功能")
    @ResponseBody
    @PostMapping
    public Result add(Movie movie){
        return movieService.add(movie);
    }

    @Operation(summary = "管理员获取电影列表",description = "视频管理功能")
    @ResponseBody
    @GetMapping("/list")
    public Result getMovieList(){
        return movieService.getMovieList();
    }

    @Operation(summary = "用户给电影评分",description = "给Rating集合添加新数据")
    @ResponseBody
    @PutMapping("/rating")
    public Result rateMovie(@RequestParam("userId") int userId, @RequestParam("movieId") int movieId){
        return movieService.rateMovie(userId, movieId);
    }

    @Operation(summary = "视频页面展示平均分")
    @ResponseBody
    @GetMapping("/rating")
    public Result getMovieAverageScore(@RequestParam("movieId") int movieId){
        return movieService.getMovieAverageScore(movieId);
    }

    @Operation(summary = "管理员更新一个电影的信息",description = "只能修改文字，不上传文件资源")
    @ResponseBody
    @PutMapping("/info")
    public Result update(Movie movie){
        return movieService.update(movie);
    }

    @Operation(summary = "上传视频电影视频资源",description = "在电影管理中使用")
    @ResponseBody
    @PostMapping(value = "/video",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result uploadVideo(@RequestParam("movieId") int movieId, @RequestParam("movieVideo") MultipartFile file){
        return movieService.uploadVideo(movieId,file);
    }

    @Operation(summary = "上传视频电影图片",description = "在电影管理中使用")
    @ResponseBody
    @PostMapping(value = "/picture",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result uploadPicture(@RequestParam("movieId") int movieId, @RequestParam("moviePicture") MultipartFile file){
        return movieService.uploadPicture(movieId,file);
    }


    /**
     * 我自己调用的接口，在网站里不调用
     *
     */
    @Operation(summary = "给特定ID的电影修改图片地址",description = "我自己调用的，网站里不调用")
    @ResponseBody
    @PutMapping(value = "/developer/picture")
    public Result updateMoviePictureUrl(@RequestParam("movieId") int movieId, @RequestParam("picture") String picture){
        return movieService.updateMoviePictureUrl(movieId,picture);
    }

    @Operation(summary = "给特定ID的电影修改视频地址",description = "我自己调用的，网站里不调用")
    @ResponseBody
    @PutMapping("/developer/video")
    public Result updateMovieVideo(@RequestParam("movieId") int movieId, @RequestParam("video") String video){
        return movieService.updateMovieVideo(movieId,video);
    }
}
