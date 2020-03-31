package com.gradingapp.bean;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "student")
public class StudentData {
	
	private String userName;
	private List<String> problems = new ArrayList<String>();
	private List<String> submissionDates = new ArrayList<String>();
	private List<Double> marksList = new ArrayList<Double>();
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public List<String> getProblems() {
		return problems;
	}
	public void setProblems(List<String> problems) {
		this.problems = problems;
	}
	
	public List<String> getSubmissionDates() {
		return submissionDates;
	}
	public void setSubmissionDates(List<String> submissionDates) {
		this.submissionDates = submissionDates;
	}
	public List<Double> getMarksList() {
		return marksList;
	}
	public void setMarksList(List<Double> marksList) {
		this.marksList = marksList;
	}
}
