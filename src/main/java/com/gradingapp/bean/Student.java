package com.gradingapp.bean;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "student")
public class Student {
	@Id
	String _id;
	String userName;
	String homeworkName;
	String questionName;
	List<Result> result;
	String lastModifiedDate;
	double marks = -1.0;
	String feedback = "";
	String codeFileURL = "";

	public Student() {
	}
	
	public Student(String userName, String homeworkName, String questionName, List<Result> result, String codeFileURL, String writeupURL) {
		this.userName = userName;
		this.homeworkName = homeworkName;
		this.questionName = questionName;
		DateFormat dateFormat =  new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		this.lastModifiedDate = dateFormat.format(new Date());
		this.result = result;
		this.marks = -1.0;
		this.feedback = "";
		this.codeFileURL = codeFileURL;
	}
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public List<Result> getResult() {
		return result;
	}
	public void setResult(List<Result> result) {
		this.result = result;
	}
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(String lastModifiedDate) {
		DateFormat dateFormat =  new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		this.lastModifiedDate = dateFormat.format(new Date());
	}
	
	public String getId() {
		return _id;
	}
	public void setId(String id) {
		this._id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getHomeworkName() {
		return homeworkName;
	}
	public void setHomeworkName(String homeworkName) {
		this.homeworkName = homeworkName;
	}
	public String getQuestionName() {
		return questionName;
	}
	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}
	public double getMarks() {
		return marks;
	}
	public void setMarks(double marks) {
		this.marks = marks;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	public String getCodeFileURL() {
		return codeFileURL;
	}
	public void setCodeFileURL(String codeFileURL) {
		this.codeFileURL = codeFileURL;
	}

}
