package com.lgr.backend.model.collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
//import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Li Gengrun
 * @date 2023/4/3 21:59
 */
@Data
@Builder
//@Document(collection = "Comment")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Comment {

    @JsonIgnore
    private String _id;

    private int userId;

    private int movieId;

    private int timeStamp; //评论时间

    private String content; //评论内容
}
