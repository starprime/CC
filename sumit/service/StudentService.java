package com.sumit.service;

import java.util.Collection;
import com.sumit.Entity.student;
import com.sumit.dao.studentdao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by star on 2/27/17.
 */
@Service
public class StudentService {

    @Autowired
    private studentdao studao;

    public Collection<student> getAllStudents(){
        return this.studao.getAllStudents();
    }
    public  student getStudentbyID(int id){
        return  this.studao.getStudentbyID(id);
    }

    public void removeStudentbyID(int id) {
        this.studao.removeStudentbyID(id);
    }

    public void updateStudent(student std){
        this.studao.updateStudentbyID(std);
    }

    public String getPiVal(String str){
        return studao.getPiVal(str);
    }
}
