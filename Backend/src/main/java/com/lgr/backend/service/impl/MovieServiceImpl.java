package com.lgr.backend.service.impl;

import com.lgr.backend.model.Display.MovieDisplay;
import com.lgr.backend.model.collection.Movie;
import com.lgr.backend.repository.MovieRepository;
import com.lgr.backend.service.MovieService;
import com.lgr.backend.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public Result getMovieInfo(int movieId) {
        return null;
    }

    @Override
    public Result add(Movie movie) {
        return null;
    }

//    @Override
//    public Result logicDelete(int movieId) {
//        return null;
//    }

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
        return null;
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
}
