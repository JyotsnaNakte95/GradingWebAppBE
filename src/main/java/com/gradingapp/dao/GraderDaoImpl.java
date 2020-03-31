package com.gradingapp.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.gradingapp.bean.GraderData;
import com.gradingapp.bean.Student;
import com.gradingapp.bean.StudentData;
import com.gradingapp.bean.Writeup;

@Repository
@Qualifier("graderDao")
public class GraderDaoImpl implements GraderDao {
	
	@Autowired
    MongoTemplate mongoTemplate;

	@Override
	public List<StudentData> getHomeworkSubmissions(String homeworkName) {
		
		List<StudentData> studentDataList = new ArrayList<StudentData>(); 
		
		Query query = new Query(Criteria.where("homeworkName").is(homeworkName));
		List<Student> students = mongoTemplate.find(query, Student.class);
		
		System.out.println("Result size: " + students.size());
		
		for(Student student: students){
			String userName = student.getUserName();
			
			boolean isUserExist = false;
			
			if(studentDataList != null && studentDataList.size() > 0) {
				for(StudentData studentData: studentDataList) {
					if(userName.equals(studentData.getUserName())) {
						studentData.getProblems().add(student.getQuestionName());
						studentData.getSubmissionDates().add(student.getLastModifiedDate());
						studentData.getMarksList().add(student.getMarks());
						isUserExist = true;
					}
				}
				if(!isUserExist) {
					StudentData studentData = setStudentData(student);
					studentDataList.add(studentData);
				}
			}else {
				StudentData studentData = setStudentData(student);
				studentDataList.add(studentData);
			}
		}
		
		for(StudentData data: studentDataList) {
			System.out.println("User: " + data.getUserName());
			System.out.println("User prob: " + data.getProblems());
			System.out.println("User dates: " + data.getSubmissionDates());
			System.out.println("User marks: " + data.getMarksList());
		}
		
		return studentDataList;
	}
	
	private StudentData setStudentData(Student student) {
		StudentData studentData = new StudentData();
		studentData.setUserName(student.getUserName());
		studentData.getProblems().add(student.getQuestionName());
		studentData.getSubmissionDates().add(student.getLastModifiedDate());
		studentData.getMarksList().add(student.getMarks());
		return studentData;
	}

	@Override
	public GraderData getSubmissionFiles(String homeworkName, String questionName, String userName) {
		Query query1 = new Query(Criteria.
				where("homeworkName").is(homeworkName)
				.and("questionName").is(questionName)
				.and("userName").is(userName));
		
		Student result1 = mongoTemplate.findOne(query1, Student.class);
		
		Query query2 = new Query(Criteria.
				where("homeworkName").is(homeworkName)
				.and("userName").is(userName));
		
		Writeup result2 = mongoTemplate.findOne(query2, Writeup.class);
		
		GraderData data = setGraderData(result1, result2);
		return data;
	}
	
	private GraderData setGraderData(Student student, Writeup writeup) {
		GraderData data = new GraderData();
		data.setHomeworkFileName(student.getCodeFileURL());
		data.setResult(student.getResult());
		if(writeup == null) {
			data.setWriteupFileName("");
		}else {
			data.setWriteupFileName(writeup.getWriteupURL());
		}
		return data;
	}

	@Override
	public void submitGrades(Student student) {
		Query query = new Query(Criteria 
	            .where("userName").is(student.getUserName())
	            .and("homeworkName").is(student.getHomeworkName())
	            .and("questionName").is(student.getQuestionName()));
		
		Student result =  mongoTemplate.findOne(query, Student.class);
		result.setMarks(student.getMarks());
		result.setFeedback(student.getFeedback());
		Document doc = new Document(); // org.bson.Document
		mongoTemplate.getConverter().write(result, doc);
		Update update = Update.fromDocument(doc);
		mongoTemplate.upsert(query, update, "student");
	}
}
