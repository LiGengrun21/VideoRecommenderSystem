package com.lgr.backend.repository;

import com.lgr.backend.model.collection.User;
import com.lgr.backend.model.request.LoginRequest;
import com.lgr.backend.model.request.RegisterRequest;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Li Gengrun
 * @date 2023/4/29 22:10
 */
@Repository
public class UserRepository {

    @Autowired
    private MongoDatabase mongoDatabase;

    /**
     *
     * @param loginRequest
     * @return 找到的对应用户
     */
    public User login(LoginRequest loginRequest){
        MongoCollection<Document> collection = mongoDatabase.getCollection("User");
        Document query = new Document("email", loginRequest.getEmail())
                .append("password",loginRequest.getPassword());
        Document result = collection.find(query).first();
        if (result==null){
            return null;
        }
        if (result.getDouble("deleted").doubleValue()==1){
            return null;
        }
        User user=new User();
        //注意：数据库存的是double类型，加入user之前转回int
        user.setUserId((int)result.getDouble("userId").doubleValue());
        user.setUsername(result.getString("username"));
        user.setEmail(result.getString("email"));
        user.setPassword(result.getString("password"));
        user.setAvatar(result.getString("avatar"));
        user.setDeleted((int)result.getDouble("deleted").doubleValue());
        return user;
    }

    /**
     * 注册新用户
     * @param registerRequest
     * @return 新用户ID
     */
    public int register(RegisterRequest registerRequest){
        MongoCollection<Document> collection = mongoDatabase.getCollection("User");
        int newId=getUserNumber()+1; //得到新id
        Document document = new Document("userId", (double)newId)
                .append("username",registerRequest.getUsername())
                .append("password",registerRequest.getPassword())
                .append("email",registerRequest.getEmail())
                .append("avatar","http://localhost:8099/userAvatars/default.jpg") //默认图片地址
                .append("deleted",0.0); //数据库里的deleted存为double类型
        collection.insertOne(document);
        return newId;
    }

    /**
     * 根据ID获取用户
     * @param userId
     * @return 用户
     */
    public User getUserById(int userId){
        MongoCollection<Document> collection = mongoDatabase.getCollection("User");
        Document query = new Document("userId", userId);
        Document result = collection.find(query).first();
        if (result == null) {
            return null;
        }
        User user=new User();
        //注意：数据库存的是double类型，加入admin之前转回int
        user.setUserId((int)result.getDouble("userId").doubleValue());
        user.setUsername(result.getString("username"));
        user.setEmail(result.getString("email"));
        user.setPassword(result.getString("password"));
        user.setDeleted((int) result.getDouble("deleted").doubleValue());
        user.setAvatar(result.getString("avatar"));
        return user;
    }

    /**
     * 获取User集合里文档的总数
     * @return 文档数
     */
    private int getUserNumber(){
        MongoCollection<Document> collection = mongoDatabase.getCollection("User");
        return (int) collection.count();
    }

    public int update(User user){
        MongoCollection<Document> collection = mongoDatabase.getCollection("User");
        // 创建查询条件
        Bson filter = Filters.eq("userId", user.getUserId());
        Bson update = Updates.combine(
                Updates.set("username", user.getUsername()),
                Updates.set("password",user.getPassword()),
                Updates.set("email",user.getEmail()),
                Updates.set("avatar",user.getAvatar()),
                Updates.set("deleted",(double)user.getDeleted())
        );
        UpdateResult result = collection.updateOne(filter, update);
        return (int)result.getModifiedCount();
    }
}
