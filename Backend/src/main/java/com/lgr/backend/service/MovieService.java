package com.lgr.backend.service;

import com.lgr.backend.model.collection.Movie;
import com.lgr.backend.util.Result;
import org.springframework.stereotype.Service;

/**
 * @author Li Gengrun
 * @date 2023/4/4 10:39
 */
@Service
public interface MovieService {
    Result searchMovies(String string);

    Result getMovieInfo(int movieId);

    Result add(Movie movie);

//    Result logicDelete(int movieId);

    Result update(Movie movie);

    Result getMovieList();

    Result getCFRec(int userId);

    Result getMostViewedRec();

    Result getRecentlyMostViewedRec();

    Result getTopRatedRec();

    Result updateMoviePictureUrl(int movieId, String picture);

    Result updateMovieVideo(int movieId, String video);

    Result rateMovie(int userId, int movieId);

    Result getMovieAverageScore(int movieId);
}
