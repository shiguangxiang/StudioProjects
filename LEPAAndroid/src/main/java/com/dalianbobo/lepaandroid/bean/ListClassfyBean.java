package com.dalianbobo.lepaandroid.bean;

import java.io.Serializable;

/**
 * 分类数据实体类
 * Created by Administrator on 2015/12/31 on 14:37.
 * Author Clown Fish
 */
public class ListClassfyBean implements Serializable{
    private String className;
    private String classImg;
    private String classNameOne;
    private String classNameTwo;

    public ListClassfyBean(String className, String classImg, String classNameOne, String classNameTwo) {
        this.className = className;
        this.classImg = classImg;
        this.classNameOne = classNameOne;
        this.classNameTwo = classNameTwo;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassImg() {
        return classImg;
    }

    public void setClassImg(String classImg) {
        this.classImg = classImg;
    }

    public String getClassNameOne() {
        return classNameOne;
    }

    public void setClassNameOne(String classNameOne) {
        this.classNameOne = classNameOne;
    }

    public String getClassNameTwo() {
        return classNameTwo;
    }

    public void setClassNameTwo(String classNameTwo) {
        this.classNameTwo = classNameTwo;
    }
}
