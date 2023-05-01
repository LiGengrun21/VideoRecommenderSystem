package com.lgr.backend.model.Display;

/**
 * 首页和搜索页展示电影，显示图片、标题
 *
 * @author Li Gengrun
 * @date 2023/5/1 10:51
 */
public class MovieDisplay {

    private String movieName;

    private String pictureUrl;

    public MovieDisplay() {
    }

    public MovieDisplay(String movieName, String pictureUrl) {
        this.movieName = movieName;
        this.pictureUrl = pictureUrl;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
