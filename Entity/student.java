package com.sumit.Entity;

/**
 * Created by star on 2/27/17.
 */
public class student{
    private int id;
    private String name;
    private String course;

    public student(int id, String name, String course) {
        this.id = id;
        this.name = name;
        this.course = course;
    }
    public student(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
