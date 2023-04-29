package com.lgr.backend.repository;

import com.lgr.backend.model.collection.Admin;
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
}
