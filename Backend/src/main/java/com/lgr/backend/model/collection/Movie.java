package com.lgr.backend.model.collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

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
//@Data
//@Builder
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


    public Movie() {
    }

    public Movie(String _id, int movieId, String name, String description, String duration, String releaseDate, String shootDate, String language, String genre, String actor, String director, String video, String picture) {
        this._id = _id;
        this.movieId = movieId;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.shootDate = shootDate;
        this.language = language;
        this.genre = genre;
        this.actor = actor;
        this.director = director;
        this.video = video;
        this.picture = picture;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getShootDate() {
        return shootDate;
    }

    public void setShootDate(String shootDate) {
        this.shootDate = shootDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
