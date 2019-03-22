package model;

import java.util.Date;

public class Score {

    private int score;
    private String player;
    private int level;
    private Date date;

    public Score(int score, String player, int level, Date date) {
        this.score = score;
        this.player = player;
        this.level = level;
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
