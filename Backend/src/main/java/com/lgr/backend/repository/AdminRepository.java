package com.lgr.backend.repository;

import com.lgr.backend.model.collection.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Li Gengrun
 * @date 2023/4/13 10:49
 */
@Repository
public interface AdminRepository extends MongoRepository<Admin,String> {
}
