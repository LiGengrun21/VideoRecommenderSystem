package com.lgr.backend.model.collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
//import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Li Gengrun
 * @date 2023/4/3 16:40
 */

/**
 * Movie collection 样例
 * {
 *   "_id": "641d7ba284d42715f08beb69",
 *   "movieId": 131168,
 *   "name": "Phoenix (2014)",
 *   "description": "A disfigured concentration-camp survivor, unrecognizable after facial reconstruction surgery, searches ravaged postwar Berlin for the husband who might have betrayed her to the Nazis.",
 *   "duration": "98 minutes",
 *   "releaseDate": "",
 *   "shootDate": "2014",
 *   "language": "Deutsch|English",
 *   "genre": "Drama",
 *   "actor": "Nina Hoss|Ronald Zehrfeld|Nina Kunzendorf|Trystan Pütter|Michael Maertens|Imogen Kogge|Felix Römer|Uwe Preuss|Valerie Koch|Eva Bay|Jeff Burrell|Nikola Kastner|Max Hopp|Megan Gay|Kirsten Block|Frank Seppeler|Daniela Holtz|Kathrin Wehlisch|Michael Wenninger|Claudia Geisler|Sofia Exss|Nina Hoss|Ronald Zehrfeld|Nina Kunzendorf|Trystan Pütter|Michael Maertens",
 *   "director": "Christian Petzold",
 *   "video": "",
 *   "picture": ""
 * }
 */
@Data
@Builder
//@Document(collection = "Movie")
@JsonInclude(JsonInclude.Include.NON_NULL) //不要传null去前端，但可以传0或空串。实体类转化为json时，值为null的属性不会被转换
public class Movie {

    /**
     * 主键没有实际意义，返回json数据的时候可以忽略
     */
    @JsonIgnore
    private String _id;

    private int movieId;

    private String name;

    private String description;

    private String duration;

    private String releaseDate;

    private String shootDate;

    private String language;

    private String genre;

    private String actor;

    private String director;

    private String video;

    private String picture;

//    /**
//     * collection里没有这个field，每次请求影片数据时，会根据Rating collection 中的数据临时计算
//     * 或者根据movieId从TopRated collection 里查询
//     */
//    private Double score;
}
