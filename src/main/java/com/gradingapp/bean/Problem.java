package com.gradingapp.bean;

import java.util.List;

public class Problem {
	
	String problemName;
	String problemDescription;
	String homeworkName;
	List<String> inputFiles;
	List<String> outputFiles;
	
	
	public Problem(String problemName, String problemDescription, String homeworkName, List<String> inputFiles, List<String> outputFiles) {
		this.problemName = problemName;
		this.problemDescription = problemDescription;
		this.homeworkName = homeworkName;
		this.inputFiles = inputFiles;
		this.outputFiles= outputFiles;
	}
	public String getHomeworkName() {
		return homeworkName;
	}
	public void setHomeworkName(String homeworkName) {
		this.homeworkName = homeworkName;
	}
	public String getProblemName() {
		return problemName;
	}
	public void setProblemName(String problemName) {
		this.problemName = problemName;
	}
	public String getProblemDescription() {
		return problemDescription;
	}
	public void setProblemDescription(String problemDescription) {
		this.problemDescription = problemDescription;
	}
	
	
	public List<String> getInputFiles() {
		return inputFiles;
	}
	public void setInputFiles(List<String> inputFiles) {
		this.inputFiles = inputFiles;
	}
	public List<String> getOutputFiles() {
		return outputFiles;
	}
	public void setOutputFiles(List<String> outputFiles) {
		this.outputFiles = outputFiles;
	}

}
