package com.lgr.backend.repository;

import com.lgr.backend.model.collection.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Li Gengrun
 * @date 2023/4/13 17:53
 */
@Repository
public interface CommentRepository extends MongoRepository<Comment,String> {
}
