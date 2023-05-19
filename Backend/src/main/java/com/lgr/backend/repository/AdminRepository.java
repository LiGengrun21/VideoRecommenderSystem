package com.lgr.backend.repository;

import com.lgr.backend.model.Display.MovieCount;
import com.lgr.backend.model.Display.MovieScore;
import com.lgr.backend.model.collection.Admin;
import com.lgr.backend.model.request.LoginRequest;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * @author Li Gengrun
 * @date 2023/4/22 16:21
 */
@Repository
public class AdminRepository {

    @Autowired
    private MongoDatabase mongoDatabase;

    public int add(Admin admin){
        MongoCollection<Document> collection = mongoDatabase.getCollection("Admin");
        //一个新的解决方案：加进去的前adminId转为double，保证mongo数据库数据类型的一致性
        //查询的时候，再转回int
        //注意：不光adminId要这样操作，permission和deleted在最初建表的时候也存成了double，操
        Document document = new Document("adminId", (double)admin.getAdminId())
                .append("adminName", admin.getAdminName())
                .append("password",admin.getPassword())
                .append("email",admin.getEmail())
                .append("avatar",admin.getAvatar())
                .append("deleted",(double)admin.getDeleted())
                .append("permission",(double)admin.getPermission());
        collection.insertOne(document);
        return admin.getAdminId();
    }

    /**
     * 逻辑删除
     * 不是物理删除，而是将deleted字段设置为1
     * @param adminId
     * @return 实际更新的文档个数
     */
    public int logicDeleteById(int adminId){
        MongoCollection<Document> collection = mongoDatabase.getCollection("Admin");
        // 创建查询条件
        Bson filter = Filters.eq("adminId", adminId);
        // 创建更新操作
        Bson update = Updates.set("deleted", (double)1);//为了保持数据一致性
        // 执行更新操作
        UpdateResult result = collection.updateOne(filter, update);
        //返回实际更新的文档个数，若成功，应当返回1
        return (int)result.getModifiedCount();
    }

    /**
     * 更新一个管理员信息
     * @param admin
     * @return 实际更新的文档个数
     */
    public int update(Admin admin){
        MongoCollection<Document> collection = mongoDatabase.getCollection("Admin");
        // 创建查询条件
        Bson filter = Filters.eq("adminId", admin.getAdminId());
        Bson update = Updates.combine(
                Updates.set("adminName", admin.getAdminName()),
                Updates.set("password",admin.getPassword()),
                Updates.set("email",admin.getEmail()),
                Updates.set("avatar",admin.getAvatar()),
                Updates.set("deleted",(double)admin.getDeleted()),
                Updates.set("permission",(double)admin.getPermission())
        );
        UpdateResult result = collection.updateOne(filter, update);
        return (int)result.getModifiedCount();
    }

    public Admin getAdminById(int adminId){
        MongoCollection<Document> collection = mongoDatabase.getCollection("Admin");
        Document query = new Document("adminId", adminId);
        Document result = collection.find(query).first();
        if (result == null) {
            return null;
        }
        Admin admin=new Admin();
        //注意：数据库存的是double类型，加入admin之前转回int
        admin.setAdminId((int) result.getDouble("adminId").doubleValue());
        admin.setAdminName(result.getString("adminName"));
        admin.setEmail(result.getString("email"));
        admin.setPassword(result.getString("password"));
        admin.setDeleted((int) result.getDouble("deleted").doubleValue());
        admin.setAvatar(result.getString("avatar"));
        admin.setPermission((int) result.getDouble("permission").doubleValue());
        return admin;
    }

    public Admin login(LoginRequest loginRequest){
        MongoCollection<Document> collection = mongoDatabase.getCollection("Admin");
        Document query = new Document("email", loginRequest.getEmail())
                .append("password",loginRequest.getPassword());
        Document result = collection.find(query).first();
        if (result==null){
            return null;
        }
        //如果已经被逻辑删除，则返回空
        if (result.getDouble("deleted") ==1){
            return null;
        }
        Admin admin=new Admin();
        //注意：数据库存的是double类型，加入admin之前转回int
        admin.setAdminId((int) result.getDouble("adminId").doubleValue());
        admin.setAdminName(result.getString("adminName"));
        admin.setPassword(result.getString("password"));
        admin.setEmail(result.getString("email"));
        admin.setAvatar(result.getString("avatar"));
        admin.setDeleted((int) result.getDouble("deleted").doubleValue());
        admin.setPermission((int) result.getDouble("permission").doubleValue());
        return admin;
    }

    public Admin getAdminByEmail(String newEmail) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("Admin");
        Document query = new Document("email", newEmail);
        Document result = collection.find(query).first();
        if (result == null) {
            return null;
        }
        Admin admin=new Admin();
        //注意：数据库存的是double类型，加入admin之前转回int
        admin.setAdminId((int) result.getDouble("adminId").doubleValue());
        admin.setAdminName(result.getString("adminName"));
        admin.setPassword(result.getString("password"));
        admin.setEmail(result.getString("email"));
        admin.setAvatar(result.getString("avatar"));
        admin.setDeleted((int) result.getDouble("deleted").doubleValue());
        admin.setPermission((int) result.getDouble("permission").doubleValue());

        return admin;
    }

    public int getUserNumber(){
        MongoCollection<Document> collection = mongoDatabase.getCollection("User");
        return (int) collection.count();
    }

    public int getMovieNumber(){
        MongoCollection<Document> collection = mongoDatabase.getCollection("Movie");
        return (int) collection.count();
    }

    public int getAdminNumber(){
        MongoCollection<Document> collection = mongoDatabase.getCollection("Admin");
        return (int) collection.count();
    }

    public int getRatingNumber(){
        MongoCollection<Document> collection = mongoDatabase.getCollection("Rating");
        return (int) collection.count();
    }

    /**
     * 最多评价的20部电影
     *
     * 用于管理系统
     * @return
     */
    public List<MovieCount> getMovieViewedData(){
        MongoCollection<Document> collection = mongoDatabase.getCollection("MostViewed");
        List<Document> results=collection.aggregate(
                Arrays.asList(
                        new Document("$sort", new Document("count", -1)),
                        new Document("$limit", 20)
                )
        ).into(new ArrayList<>());

        List<MovieCount> movieCountList=new ArrayList<>();
        //根据movieId查询movieName
        MongoCollection<Document> collection2 = mongoDatabase.getCollection("Movie");
        for (Document document : results) {
            MovieCount movieCount=new MovieCount();
            //根据movieId查询电影名
            int movieId=document.getInteger("movieId");
            Document query = new Document("movieId", movieId);
            Document movieDocument = collection2.find(query).first();
            movieCount.setMovieName(movieDocument.getString("name"));
            movieCount.setCount(document.getLong("count"));
            movieCountList.add(movieCount);
        }
        return movieCountList;
    }

    /**
     * 各个评分区间的数量
     *
     * 用于管理系统
     * @return
     */
    public List<MovieScore> getMovieTopRatedData(){
        MongoCollection<Document> collection = mongoDatabase.getCollection("TopRated");
        List<MovieScore> movieScoreList=new ArrayList<>();
        //各个区间的数量
        Document query1 = new Document("average", new Document("$gt", 4).append("$lte", 5));
        Document query2 = new Document("average", new Document("$gt", 3).append("$lte", 4));
        Document query3 = new Document("average", new Document("$gt", 2).append("$lte", 3));
        Document query4 = new Document("average", new Document("$gt", 1).append("$lte", 2));
        Document query5 = new Document("average", new Document("$gt", 0).append("$lte", 1));
        long count1 = collection.count(query1);
        long count2 = collection.count(query2);
        long count3 = collection.count(query3);
        long count4 = collection.count(query4);
        long count5 = collection.count(query5);
        long [] counts={count1,count2,count3,count4,count5};
        String[] intervals={"4.0~5.0","3.0~4.0","2.0~3.0","1.0~2.0","0.0~1.0"};
        for (int i=0;i<=4;i++){
            MovieScore movieScore=new MovieScore();
            movieScore.setScoreInterval(intervals[i]);
            movieScore.setNumber((int)counts[i]);//强制转化为int
            movieScoreList.add(movieScore);
        }
        return movieScoreList;
    }
}
