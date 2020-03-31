package com.gradingapp.dao;

import java.util.List;

import com.gradingapp.bean.GraderData;
import com.gradingapp.bean.Student;
import com.gradingapp.bean.StudentData;

public interface GraderDao {

	public List<StudentData> getHomeworkSubmissions(String homeworkName);
	
	public GraderData getSubmissionFiles(String homeworkName, String questionName, String userName);
	
	public void submitGrades(Student student);
}
