package com.xmrion.tnm.aitao.javabean;

/**
 * Created by 郑艳达 on 2017/7/6.
 * 评论类
 */

public class Comment_Xmarion {

    private String content;//评论内容

    private String reviewerName;//评论人姓名

    private String date;//评论日期

    public Comment_Xmarion(String content, String reviewerName, String date) {
        this.content = content;
        this.reviewerName = reviewerName;
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
