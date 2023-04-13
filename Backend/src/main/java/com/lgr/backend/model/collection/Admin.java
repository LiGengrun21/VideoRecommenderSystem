package com.lgr.backend.model.collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Li Gengrun
 * @date 2023/4/3 16:39
 */
@Data
@Builder
@Document(collection = "Admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Admin {

    @JsonIgnore
    private String _id;

    private int adminId;

    private String adminName;

    private String password;

    private String email;

    private String avatar; //管理员头像

    private int deleted; //逻辑删除字段，0为正常管理员，1为删除管理员

    private int permission; //权限，0为普通管理员，1为超级管理员
}
