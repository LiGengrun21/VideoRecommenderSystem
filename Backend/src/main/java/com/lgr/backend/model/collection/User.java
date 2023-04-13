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
@Document(collection = "User")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    @JsonIgnore
    private String _id;

    private int userId;

    private String username;

    private String password;

    private String email;

    private String avatar; //用户头像

    private int deleted; //逻辑删除字段，0为正常用户，1为删除用户
}
