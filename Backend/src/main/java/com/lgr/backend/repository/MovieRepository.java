package com.lgr.backend.repository;

import com.lgr.backend.model.Display.MovieDisplay;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
            movieDisplay.setMovieName(document.getString("name"));
            movieDisplay.setPictureUrl(document.getString("picture"));
            movieDisplayList.add(movieDisplay);
        }
        return movieDisplayList;
    }
}
