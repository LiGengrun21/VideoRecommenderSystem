//package com.lgr.backend.util;
//
//import com.mongodb.MongoClient;
//import com.mongodb.client.MongoCollection;
//import org.bson.Document;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// * @author Li Gengrun
// * @date 2023/4/21 21:21
// */
//public class MongoUtil {
//    @Autowired
//    private MongoClient mongoClient;
//
//    private MongoCollection<Document> getCollection(String collectionName){
//        MongoCollection<Document> collection = null;
//        collection = mongoClient.getDatabase("recommender").getCollection(collectionName);
//        return collection;
//    }
//}
