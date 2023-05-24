package com.lgr.backend.repository;

import com.lgr.backend.model.Display.MovieCount;
import com.lgr.backend.model.Display.MovieDisplay;
import com.lgr.backend.model.collection.Admin;
import com.lgr.backend.model.collection.Movie;
import com.lgr.backend.model.collection.Rating;
import com.lgr.backend.model.collection.User;
import com.lgr.backend.util.Result;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
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
            //数据库读到空地址，则填入默认地址
            String picture=(document.getString("picture"));
            if (picture==null || picture=="" || picture.isEmpty()){
                movieDisplay.setPictureUrl("https://images.unsplash.com/photo-1522770179533-24471fcdba45");
            }
            else{
                movieDisplay.setPictureUrl(picture);
            }
            movieDisplayList.add(movieDisplay);
        }
        return movieDisplayList;
    }

    /**
     * 个性化推荐
     *
     * @param userId
     * @return
     */
    public List<MovieDisplay> getCFRec(int userId){
        List<MovieDisplay> movieDisplayList = new ArrayList<>();
        MongoCollection<Document> collection = mongoDatabase.getCollection("RecommendationResults");
        MongoCollection<Document> collection1 = mongoDatabase.getCollection("Movie");
        Document query = new Document("userId", userId);
        Document document = collection.find(query).first();
        //获取推荐数组
        List<Document> recs = (List<Document>) document.get("recs");
        if (recs==null){
            return null; //有的用户没有任何评分记录，所以没有个性化推荐
        }
        for (Document rec : recs) {
            MovieDisplay movieDisplay=new MovieDisplay();
            int movieId=rec.getInteger("movieId");
            movieDisplay.setMovieId(movieId);
            Document query1 = new Document("movieId", movieId);
            Document movieDocument = collection1.find(query1).first();
            movieDisplay.setMovieId(movieDocument.getInteger("movieId"));
            movieDisplay.setMovieName(movieDocument.getString("name"));
            //数据库读到空地址，则填入默认地址
            String picture=(movieDocument.getString("picture"));
            //System.out.println("CFRec picture:"+picture);
            if (picture==null || picture=="" ||picture.isEmpty()){
                movieDisplay.setPictureUrl("https://images.unsplash.com/photo-1522770179533-24471fcdba45");
            }
            else{
                movieDisplay.setPictureUrl(picture);
            }
            movieDisplayList.add(movieDisplay);
        }
        return movieDisplayList;
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
            //数据库读到空地址，则填入默认地址
            String picture=(movieDocument.getString("picture"));
            //System.out.println("MostViewedRec picture:"+picture);
            if (picture==null || picture=="" ||picture.isEmpty()){
                movieDisplay.setPictureUrl("https://images.unsplash.com/photo-1522770179533-24471fcdba45");
            }
            else{
                movieDisplay.setPictureUrl(picture);
            }
            movieDisplayList.add(movieDisplay);
        }
        return movieDisplayList;
    }

    /**
     * 最高评价的6部电影
     *
     * @return
     */
    public List<MovieDisplay> getTopRatedRec(){
        MongoCollection<Document> collection = mongoDatabase.getCollection("TopRated");
        //查询movieId最小的6个影片(movieId, average)
        List<Document> results=collection.aggregate(
                Arrays.asList(
                        new Document("$sort", new Document("movieId", 1)),
                        new Document("$limit", 6)
                )
        ).into(new ArrayList<>());

        List<MovieDisplay> movieDisplayList = new ArrayList<>();
        //根据movieId查询movieName和picture
        MongoCollection<Document> collection3 = mongoDatabase.getCollection("Movie");
        for (Document document : results) {
            MovieDisplay movieDisplay=new MovieDisplay();
            //根据movieId查询电影名和图片
            int movieId=document.getInteger("movieId");
            Document query = new Document("movieId", movieId);
            Document movieDocument = collection3.find(query).first();
            movieDisplay.setMovieId(movieDocument.getInteger("movieId"));
            movieDisplay.setMovieName(movieDocument.getString("name"));
            //数据库读到空地址，则填入默认地址
            String picture=(movieDocument.getString("picture"));
            //System.out.println("TopRated picture:"+picture);
            if (picture==null || picture=="" ||picture.isEmpty()){
                movieDisplay.setPictureUrl("https://images.unsplash.com/photo-1522770179533-24471fcdba45");
            }
            else{
                movieDisplay.setPictureUrl(picture);
            }
            movieDisplayList.add(movieDisplay);
        }
        return movieDisplayList;
    }

    public Movie getMovieById(int movieId){
        MongoCollection<Document> collection = mongoDatabase.getCollection("Movie");
        Document query = new Document("movieId", movieId);
        Document result = collection.find(query).first();
        if (result == null) {
            return null;
        }
        Movie movie=new Movie();
        //movie里int存得是正常的
        movie.setMovieId(result.getInteger("movieId"));
        movie.setName(result.getString("name"));
        movie.setDescription(result.getString("description"));
        movie.setDuration(result.getString("duration"));
        movie.setReleaseDate(result.getString("releaseDate"));
        movie.setShootDate(result.getString("shootDate"));
        movie.setLanguage(result.getString("language"));
        movie.setGenre(result.getString("genre"));
        movie.setActor(result.getString("actor"));
        movie.setDirector(result.getString("director"));
        movie.setVideo(result.getString("video"));
        movie.setPicture(result.getString("picture"));
        return movie;
    }

    public int updateMovie(Movie movie) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("Movie");
        // 创建查询条件
        Bson filter = Filters.eq("movieId", movie.getMovieId());
        Bson update = Updates.combine(
                Updates.set("name", movie.getName()),
                Updates.set("description",movie.getDescription()),
                Updates.set("duration",movie.getDuration()),
                Updates.set("releaseDate",movie.getReleaseDate()),
                Updates.set("shootDate",movie.getShootDate()),
                Updates.set("language",movie.getLanguage()),
                Updates.set("genre",movie.getGenre()),
                Updates.set("actor",movie.getActor()),
                Updates.set("director",movie.getDirector()),
                Updates.set("video",movie.getVideo()),
                Updates.set("picture",movie.getPicture())
        );
        UpdateResult result = collection.updateOne(filter, update);
        return (int)result.getModifiedCount();
    }

    /**
     * 从TopRated表里获取评分
     * @param movieId
     * @return
     */
    public double getMovieScore(int movieId){
        MongoCollection<Document> collection = mongoDatabase.getCollection("TopRated");
        Document query = new Document("movieId", movieId);
        Document result = collection.find(query).first();
        //找不到电影
        if (result == null) {
            return 0;
        }
        return result.getDouble("average");
    }

    /**
     * 更新评分
     *
     * @param rating
     * @return
     */
    public Rating updateRating(Rating rating){
//        MongoCollection<Document> collection = mongoDatabase.getCollection("Rating");
//        Document query = new Document("movieId", rating.getMovieId()).
//                append("userId",rating.getUserId());
//        Document result = collection.find(query).first();
//        //用户没有对这个电影的评分记录
//        if (result == null) {
//            //添加评分
//            return null;
//        }
//        //修改score
//        Rating rating=new Rating();
//        rating.setMovieId(result.getInteger("movieId"));
//        rating.setUserId(result.getInteger("userId"));
//        rating.setScore(result.getInteger("score"));
//        return rating;
        return null;
    }

    /**
     * 第一次评分
     *
     * @param rating
     * @return
     */
    public Rating addRating(Rating rating){
        return null;
    }

    /**
     * 查询用户对电影的评分
     *
     * @param movieId
     * @param userId
     * @return
     */
    public Rating getRatingByIds(int movieId, int userId){
        return null;
    }

    public List<Movie> getMovieList(){
        MongoCollection<Document> collection = mongoDatabase.getCollection("Movie");
        FindIterable<Document> result = collection.find();//查询全部文档
        if (result == null) {
            return null;
        }
        List<Movie> movieList=new ArrayList<>();
        for (Document document : result) {
            Movie movie=new Movie();
            //movie里int存得是正常的
            movie.setMovieId(document.getInteger("movieId"));
            movie.setName(document.getString("name"));
            movie.setDescription(document.getString("description"));
            movie.setDuration(document.getString("duration"));
            movie.setReleaseDate(document.getString("releaseDate"));
            movie.setShootDate(document.getString("shootDate"));
            movie.setLanguage(document.getString("language"));
            movie.setGenre(document.getString("genre"));
            movie.setActor(document.getString("actor"));
            movie.setDirector(document.getString("director"));
            movie.setVideo(document.getString("video"));
            movie.setPicture(document.getString("picture"));
            movieList.add(movie);
        }
        return movieList;
    }
}
