package com.lgr.backend.model.Display;

/**
 * @author Li Gengrun
 * @date 2023/5/19 15:39
 */
public class MovieScore {
    /**
     * 例：0~1 1~2
     */
    private String scoreInterval;

    private int number;

    public MovieScore() {
    }

    public MovieScore(String scoreInterval, int number) {
        this.scoreInterval = scoreInterval;
        this.number = number;
    }

    public String getScoreInterval() {
        return scoreInterval;
    }

    public void setScoreInterval(String scoreInterval) {
        this.scoreInterval = scoreInterval;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
