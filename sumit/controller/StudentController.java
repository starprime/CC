package com.sumit.controller;

import com.sumit.service.StudentService;
import com.sumit.Entity.student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Created by star on 2/27/17.
 */
@RestController
//@RequestMapping("/students")
@RequestMapping(value="/",method = RequestMethod.GET)
public class StudentController {
    @Autowired
    private StudentService studentService;
    @RequestMapping(method = RequestMethod.GET)
    public Collection<student> getAllStudents(){
        return  studentService.getAllStudents();
    }
    //@RequestMapping(value = "/{id}",method = RequestMethod.GET)
    //public student getStudentbyID(@PathVariable("id") int id){
    //    return studentService.getStudentbyID(id);
    //}
    /*
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public void deleteStudentByID(@PathVariable("id") int id){
        studentService.removeStudentbyID(id);
    }
    @RequestMapping(method = RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateStudentByID(@RequestBody student std){
        studentService.updateStudent(std);
    }
    */


    @RequestMapping(value="cloudpi.php",method = RequestMethod.GET)
    public  String getPiVal(@RequestParam(value="input",defaultValue = "1") String str){
        System.out.println("inp string is : "+str);
        return studentService.getPiVal(str);
    }
}
