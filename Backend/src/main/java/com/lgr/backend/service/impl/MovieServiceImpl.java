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
        return null;
    }

    @Override
    public Result getMovieList() {
        return null;
    }

    @Override
    public Result getCFRec(int userId) {
        return null;
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
        return null;
    }
}
