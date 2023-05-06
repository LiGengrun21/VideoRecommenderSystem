package com.lgr.backend.repository;

import com.lgr.backend.model.Display.MovieDisplay;
import com.lgr.backend.model.collection.Movie;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Li Gengrun
 * @date 2023/5/1 10:25
 */
@Repository
public class MovieRepository {

    @Autowired
    private MongoDatabase mongoDatabase;

    /**
     * 模糊查询电影名字
     * @param string
     * @return 电影列表
     */
    public List<MovieDisplay> searchMovies(String string){
        MongoCollection<Document> collection = mongoDatabase.getCollection("Movie");
        // 创建正则表达式模式
        Pattern pattern = Pattern.compile(string, Pattern.CASE_INSENSITIVE);//忽略字符大小写
        // 使用正则表达式模式执行模糊查询
        FindIterable<Document> iterable = collection.find(Filters.regex("name", pattern));
        //没有检索到结果
        if (iterable==null){
            return null;
        }
        //检索到结果
        List<MovieDisplay> movieDisplayList = new ArrayList<>();
        for (Document document : iterable) {
            MovieDisplay movieDisplay=new MovieDisplay();
            movieDisplay.setMovieId(document.getInteger("movieId"));
            movieDisplay.setMovieName(document.getString("name"));
            movieDisplay.setPictureUrl(document.getString("picture"));
            movieDisplayList.add(movieDisplay);
        }
        return movieDisplayList;
    }

    /**
     * 个性化推荐
     * @param userId
     * @return
     */
    public List<MovieDisplay> getCFRec(int userId){
        return null;
    }

    /**
     * 最多评价的6部电影
     *
     * @return
     */
    public List<MovieDisplay> getMostViewedRec(){
        MongoCollection<Document> collection = mongoDatabase.getCollection("MostViewed");
        //查询count最大的6个影片(movieId, count)
        List<Document> results=collection.aggregate(
                Arrays.asList(
                        new Document("$sort", new Document("count", -1)),
                        new Document("$limit", 6)
                )
        ).into(new ArrayList<>());

        List<MovieDisplay> movieDisplayList = new ArrayList<>();
        //根据movieId查询movieName和picture
        MongoCollection<Document> collection2 = mongoDatabase.getCollection("Movie");
        for (Document document : results) {
            MovieDisplay movieDisplay=new MovieDisplay();
            //根据movieId查询电影名和图片
            int movieId=document.getInteger("movieId");
            Document query = new Document("movieId", movieId);
            Document movieDocument = collection2.find(query).first();
            movieDisplay.setMovieId(movieDocument.getInteger("movieId"));
            movieDisplay.setMovieName(movieDocument.getString("name"));
            movieDisplay.setPictureUrl(movieDocument.getString("picture"));
            movieDisplayList.add(movieDisplay);
        }
        return movieDisplayList;
    }
}
