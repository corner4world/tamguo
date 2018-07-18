package com.tamguo.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.tamguo.config.dao.SuperEntity;

import java.io.Serializable;

/**
 * The persistent class for the tiku_book database table.
 */
@TableName(value = "tiku_book")
public class BookEntity extends SuperEntity<BookEntity> implements Serializable {
    private static final long serialVersionUID = 1L;


    private String subjectId;

    private String courseId;

    private String name;

    private String publishingHouse;

    private Integer questionNum;

    private Integer pointNum;

    private Integer orders;

    private String reserveField1;

    public BookEntity() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    public Integer getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(Integer questionNum) {
        this.questionNum = questionNum;
    }

    public Integer getPointNum() {
        return pointNum;
    }

    public void setPointNum(Integer pointNum) {
        this.pointNum = pointNum;
    }

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    public String getReserveField1() {
        return reserveField1;
    }

    public void setReserveField1(String reserveField1) {
        this.reserveField1 = reserveField1;
    }
}