package com.senchenko.aliens.entity;

import java.sql.Date;

public class Comment extends Entity{
    private int commentId;
    private Date date;
    private Monster monster;
    private int mark;
    private String comment;
    private User user;

    public Comment(int commentId, Date date, Monster monster, int mark, String comment, User user) {
        this.commentId = commentId;
        this.date = date;
        this.monster = monster;
        this.mark = mark;
        this.comment = comment;
        this.user = user;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment1 = (Comment) o;

        if (commentId != comment1.commentId) return false;
        if (mark != comment1.mark) return false;
        if (date != null ? !date.equals(comment1.date) : comment1.date != null) return false;
        if (monster != null ? !monster.equals(comment1.monster) : comment1.monster != null) return false;
        if (comment != null ? !comment.equals(comment1.comment) : comment1.comment != null) return false;
        return user != null ? user.equals(comment1.user) : comment1.user == null;
    }

    @Override
    public int hashCode() {
        int result = commentId;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (monster != null ? monster.hashCode() : 0);
        result = 31 * result + mark;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Commentable{" +
                "commentId=" + commentId +
                ", date=" + date +
                ", monster=" + monster +
                ", mark=" + mark +
                ", comment='" + comment + '\'' +
                ", user=" + user +
                '}';
    }
}
