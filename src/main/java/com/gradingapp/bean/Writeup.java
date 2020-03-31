package com.gradingapp.bean;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "writeup")
public class Writeup {
	
	private String userName;
	private String homeworkName;
	private String writeupURL;
	
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
	public String getWriteupURL() {
		return writeupURL;
	}
	public void setWriteupURL(String writeupURL) {
		this.writeupURL = writeupURL;
	}
}
