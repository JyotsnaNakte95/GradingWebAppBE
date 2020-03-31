package com.gradingapp.dao;

import java.util.List;

import com.gradingapp.bean.Student;
import com.gradingapp.bean.Writeup;

public interface StudentDao {
	
	public void create(Student s);
	 
    public void update(Student s);
 
    public void delete(Student s);
 
    public void deleteAll();
 
    public Student find(Student s);
 
    public List < Student > findAll();
    
    public void updateWriteup(Writeup writeup);


}
