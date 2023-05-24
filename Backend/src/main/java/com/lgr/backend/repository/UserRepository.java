package com.lgr.backend.repository;

import com.lgr.backend.model.collection.User;
import com.lgr.backend.model.request.LoginRequest;
import com.lgr.backend.model.request.RegisterRequest;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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
     * 根据邮箱获取用户
     *
     * 运用在用户注册和用户信息修改
     *
     * 因为邮箱不能重复注册
     * @param email
     * @return
     */
    public User getUserByEmail(String email){
        MongoCollection<Document> collection = mongoDatabase.getCollection("User");
        Document query = new Document("email", email);
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
    public int getUserNumber(){
        MongoCollection<Document> collection = mongoDatabase.getCollection("User");
        return (int) collection.count();
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
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

    public List<User> getUserList(){
        MongoCollection<Document> collection = mongoDatabase.getCollection("User");
        FindIterable<Document> result = collection.find();//查询全部文档
        if (result == null) {
            return null;
        }
        List<User> userList=new ArrayList<>();
        for (Document document : result) {
            User user=new User();
            user.setUserId((int)document.getDouble("userId").doubleValue());
            user.setUsername(document.getString("username"));
            user.setEmail(document.getString("email"));
            user.setPassword(document.getString("password"));
            user.setDeleted((int) document.getDouble("deleted").doubleValue());
            user.setAvatar(document.getString("avatar"));
            userList.add(user);
        }
        return userList;
    }

    public int logicDeleteUser(int userId) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("User");
        // 创建查询条件
        Bson filter = Filters.eq("userId", userId);
        // 创建更新操作
        Bson update = Updates.set("deleted", (double)1);//为了保持数据一致性
        // 执行更新操作
        UpdateResult result = collection.updateOne(filter, update);
        //返回实际更新的文档个数，若成功，应当返回1
        return (int)result.getModifiedCount();
    }

    public int recoverUser(int userId) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("User");
        // 创建查询条件
        Bson filter = Filters.eq("userId", userId);
        // 创建更新操作
        Bson update = Updates.set("deleted", (double)0);//为了保持数据一致性
        // 执行更新操作
        UpdateResult result = collection.updateOne(filter, update);
        //返回实际更新的文档个数，若成功，应当返回1
        return (int)result.getModifiedCount();
    }
}
