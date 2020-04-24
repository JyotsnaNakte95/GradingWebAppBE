package com.gradingapp.bean;

import java.util.List;

public class GraderData {
	
	private String homeworkFileName;
	private String writeupFileName;
	private List<Result> result;
	private double marks;
	private String feedback;
	
	public String getHomeworkFileName() {
		return homeworkFileName;
	}
	public void setHomeworkFileName(String homeworkFileName) {
		this.homeworkFileName = homeworkFileName;
	}
	
	public String getWriteupFileName() {
		return writeupFileName;
	}
	public void setWriteupFileName(String writeupFileName) {
		this.writeupFileName = writeupFileName;
	}
	
	public List<Result> getResult() {
		return result;
	}
	public void setResult(List<Result> result) {
		this.result = result;
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
}
