package com.sumit.dao;

import java.util.Collection;

import com.sumit.Entity.EConnect;
import com.sumit.Entity.RequestPiValue;
import com.sumit.Entity.student;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by star on 2/27/17.
 */
@Repository
public class studentdao {

    private static Map<Integer, student> students;

    static  {
        students = new HashMap<Integer,student>()
        {
            {   put(1,new student(1,"sam","comp sce"));
                put(2,new student(2,"star","wove"));
                put(3,new student(3,"prime","bio"));
            }
        };
    }
    public Collection<student> getAllStudents(){
        return this.students.values();
    }
    public  student getStudentbyID(int id){
        return  this.students.get(id);
    }

    public void removeStudentbyID(int id) {
        this.students.remove(id);
    }
    public void updateStudentbyID(student std){
        student s = students.get(std.getId());
        s.setCourse(std.getCourse());
        s.setName(std.getName());
        students.put(std.getId(),std);

    }

    public String getPiVal(String str){
        int foo = Integer.parseInt(str);
        RequestPiValue rpv = new RequestPiValue(str);
        return rpv.retPiVal();
    }
}