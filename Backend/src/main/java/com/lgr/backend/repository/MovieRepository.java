package com.lgr.backend.repository;

import com.lgr.backend.model.collection.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Li Gengrun
 * @date 2023/4/4 10:36
 */
@Repository
public interface MovieRepository extends MongoRepository<Movie,String> {
}
