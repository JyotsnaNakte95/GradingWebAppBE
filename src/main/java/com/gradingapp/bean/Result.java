package com.gradingapp.bean;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Result {

	private int status;
    private String studentOutput;
    private String errorOutput;
    private String expectedOutput;
    private Boolean testCasePassed;
    private long duration;

    public Result() {
        this.status = 0;
        this.studentOutput = "";
        this.expectedOutput = "";
        this.errorOutput = "";
        this.testCasePassed = true;
        this.duration=0;
    }
    public Result(int status, String studentOutput, String expectedOutput,String errorOutput, Boolean testCasePassed, long duration) {
        this.status = status;
        this.studentOutput = studentOutput;
        this.expectedOutput = expectedOutput;
        this.errorOutput = errorOutput;
        this.testCasePassed = testCasePassed;
        this.duration =duration;
    }
    public int getStatus() {
        return status;
    }
 
    public void setStatus(int status) {
        this.status = status;
    }
    public long getDuration() {
        return duration;
    }
    public void setDuration(long duration) {
        this.duration = duration;
    }
    public String getStudentOutput() {
        return studentOutput;
    }
    public void setStudentOutput(String studentOutput) {
        this.studentOutput = studentOutput;
    }
    public String getExpectedOutput() {
        return expectedOutput;
    }
    public void setErrorOutput(String errorOutput) {
        this.errorOutput = errorOutput;
    }
    public String getErrorOutput() {
        return errorOutput;
    }
    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }
    public Boolean getTestCasePassed() {
        return testCasePassed;
    }
    public void setTestCasePassed(Boolean testCasePassed) {
        this.testCasePassed = testCasePassed;
    }

	
}
