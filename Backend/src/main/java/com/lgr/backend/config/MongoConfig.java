package com.lgr.backend.config;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Li Gengrun
 * @date 2023/4/21 21:51
 */
@Configuration
public class MongoConfig {

    @Bean
    public MongoClient mongoClient() {
        return new MongoClient("localhost", 27017);
    }

    @Bean
    public MongoDatabase mongoDatabase() {
        return mongoClient().getDatabase("recommender");
    }
}

