package com.gradingapp.bean;

public class GraderData {
	
	private String homeworkFileName;
	private String writeupFileName;
	private Result result;
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
	
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
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
