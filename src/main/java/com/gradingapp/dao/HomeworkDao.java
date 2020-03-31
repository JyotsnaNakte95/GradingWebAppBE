package com.gradingapp.dao;

import java.util.List;

import com.gradingapp.bean.Homework;
import com.gradingapp.bean.Problem;

public interface HomeworkDao {
	
	public boolean create(Homework h);
	 
    public void update(Homework h);
    
    public void updateProblem(Problem p);
 
    public void delete(Homework h);
 
    public void deleteAll();
 
    public Homework find(Homework h);
 
    public List <Homework> availableHomework();

    public List<Problem> findProblem(String homeworkName);
    
    public List <Homework> findAll();
}
