package com.gradingapp.bean;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "homeworks")
public class Homework {
	
	 	@Id
	    private String id;
	    private String homeworkName;
	    private String dueDate;
	    private List<Problem> problem;
	    
	    public Homework(String homeworkName, String dueDate,List<Problem> problem ) {
	    	this.homeworkName = homeworkName;
	    	this.dueDate = dueDate;
	    	this.problem = problem;
	    }
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getHomeworkName() {
			return homeworkName;
		}
		public void setHomeworkName(String homeworkName) {
			this.homeworkName = homeworkName;
		}
		public String getDueDate() {
			return dueDate;
		}
		public void setDueDate(String dueDate) {
			this.dueDate = dueDate;
		}
		public List<Problem> getProblem() {
			return problem;
		}
		public void setProblem(List<Problem> problem) {
			this.problem = problem;
		}
	    
	    
	 

}
