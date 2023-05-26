package com.lgr.backend.service.impl;

import com.lgr.backend.model.Display.MovieDisplay;
import com.lgr.backend.model.collection.Movie;
import com.lgr.backend.model.collection.Rating;
import com.lgr.backend.model.collection.User;
import com.lgr.backend.repository.MovieRepository;
import com.lgr.backend.service.MovieService;
import com.lgr.backend.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Li Gengrun
 * @date 2023/4/4 10:39
 */
@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public Result searchMovies(String string) {
        List<MovieDisplay> movieDisplayList=movieRepository.searchMovies(string);
        if (movieDisplayList==null||movieDisplayList.isEmpty()){
            return Result.FAIL("查询不到视频");
        }
        return Result.SUCCESS(movieDisplayList);
    }

    /**
     * 视频详情页调用，所以要处理默认视频资源
     * @param movieId
     * @return
     */
    @Override
    public Result getMovieInfo(int movieId) {
        Movie movie=movieRepository.getMovieById(movieId);
        if (movie==null){
            return Result.FAIL("找不到电影，ID为"+movieId);
        }

        //如果视频字段是空，要添加默认资源
        String video= movie.getVideo();
        if (video==null || video=="" ||video.isEmpty()){
            movie.setVideo("http://localhost:8099/videos/default.mp4");
        }
        return Result.SUCCESS(movie);
    }

    @Override
    public Result add(Movie movie) {
        return null;
    }

    @Override
    public Result update(Movie movie) {
        Movie movieResult=movieRepository.getMovieById(movie.getMovieId());
        if (movieResult==null){
            return Result.FAIL("找不到电影,ID:"+movieResult.getMovieId());
        }
        movieRepository.updateMovie(movie);
        return Result.SUCCESS(movie);
    }

    @Override
    public Result getMovieList() {
        List<Movie> movieList=movieRepository.getMovieList();
        if (movieList==null){
            return Result.FAIL("获取电影列表失败");
        }
        return Result.SUCCESS(movieList);
    }

    @Override
    public Result getCFRec(int userId) {
        List<MovieDisplay> movieDisplayList=movieRepository.getCFRec(userId);
        if (movieDisplayList==null||movieDisplayList.isEmpty()){
            return Result.FAIL("查询不到视频");
        }
        return Result.SUCCESS(movieDisplayList);
    }

    @Override
    public Result getMostViewedRec() {
        List<MovieDisplay> movieDisplayList=movieRepository.getMostViewedRec();
        if (movieDisplayList==null||movieDisplayList.isEmpty()){
            return Result.FAIL("查询不到视频");
        }
        return Result.SUCCESS(movieDisplayList);
    }

    @Override
    public Result getRecentlyMostViewedRec() {
        return null;
    }

    @Override
    public Result getTopRatedRec() {
        List<MovieDisplay> movieDisplayList=movieRepository.getTopRatedRec();
        if (movieDisplayList==null||movieDisplayList.isEmpty()){
            return Result.FAIL("查询不到视频");
        }
        return Result.SUCCESS(movieDisplayList);
    }

    @Override
    public Result updateMoviePictureUrl(int movieId, String picture) {
        Movie movie=movieRepository.getMovieById(movieId);
        System.out.println(movie.getMovieId()+movie.getDescription());
        if (movie==null) {
            return Result.FAIL("找不到这个电影，ID为"+movieId);
        }
        //修改电影照片地址
        movie.setPicture(picture);
        movieRepository.updateMovie(movie);
        return Result.SUCCESS(movie);
    }

    @Override
    public Result updateMovieVideo(int movieId, String video) {
        Movie movie=movieRepository.getMovieById(movieId);
        System.out.println(movie.getMovieId()+movie.getDescription());
        if (movie==null) {
            return Result.FAIL("找不到这个电影，ID为"+movieId);
        }
        //修改视频地址
        movie.setVideo(video);
        movieRepository.updateMovie(movie);
        return Result.SUCCESS(movie);
    }

    @Override
    public Result rateMovie(Rating rating) {
        if (rating==null){
            return Result.FAIL("不能传入空的Rating对象");
        }
        Rating historyRating=movieRepository.getMovieScoreById(rating.getUserId(),rating.getMovieId());
        if (historyRating==null){
            //没有评分记录，所以进行添加操作
            movieRepository.addScore(rating);
        }
        //有评分记录，所以进行更新操作
        movieRepository.updateScore(rating);
        //返回评分数据
        return Result.SUCCESS(movieRepository.getMovieScoreById(rating.getUserId(), rating.getMovieId()));
    }

    @Override
    public Result getMovieAverageScore(int movieId) {
        double score=movieRepository.getMovieScore(movieId);
        if (score==0){
            return Result.FAIL("找不到这个电影，ID为："+movieId+"，或者是有的电影没有被评价");
        }
        return Result.SUCCESS(score);
    }

    /**
     * http://localhost:8099/videos/1.mp4 数据库存储视频地址样例
     * D://WebServer//resources//videos//1.mp4 视频本地存储的地址
     * @param movieId
     * @param file
     * @return
     */
    @Override
    public Result uploadVideo(int movieId, MultipartFile file) {
        String fileName=movieId+".mp4";
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get("D:\\WebServer\\resources\\videos\\" + fileName);
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //视频上传成功后，还要更新数据库，将这个movie的视频地址修改为新的地址
        Movie movie=movieRepository.getMovieById(movieId);
        movie.setVideo("http://localhost:8099/videos/"+fileName);
        movieRepository.updateMovie(movie);
        return Result.SUCCESS(movie);
    }

    /**
     * http://localhost:8099/images/1.jpg 数据库存储图片地址样例
     * D://WebServer//resources//images//1.jpg 图像本地存储的地址
     * @param movieId
     * @param file
     * @return
     */
    @Override
    public Result uploadPicture(int movieId, MultipartFile file) {
        String fileName=movieId+".jpg";
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get("D:\\WebServer\\resources\\images\\" + fileName);
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //图片上传成功后，还要更新数据库，将这个movie的图片地址修改为新的地址
        Movie movie=movieRepository.getMovieById(movieId);
        movie.setPicture("http://localhost:8099/images/"+fileName);
        movieRepository.updateMovie(movie);
        return Result.SUCCESS(movie);
    }

    @Override
    public Result getMovieScoreByUser(int userId, int movieId) {
        Rating rating=movieRepository.getMovieScoreById(userId,movieId);
        if (rating==null){
            return Result.FAIL("没有找到这个用户对这个电影的评分，用户ID:"+userId+"，电影ID:"+movieId);
        }
        return Result.SUCCESS(rating);
    }
}
