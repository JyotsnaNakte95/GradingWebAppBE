package com.gradingapp.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gradingapp.bean.Result;
import com.gradingapp.bean.Student;
import com.gradingapp.bean.Writeup;
import com.gradingapp.bean.Problem;
import com.gradingapp.bean.Homework;
import com.gradingapp.service.CompileAndRunService;
import com.gradingapp.service.FileService;
import com.gradingapp.service.StudentService;

@CrossOrigin("*")
@RestController
public class StudentController {
		
	@Autowired
	private StudentService studentService;
	@Autowired
	FileService fileService;
	@Autowired
	CompileAndRunService c;
	@Autowired
    MongoTemplate mongoTemplate;
	
	
	@CrossOrigin
	@PostMapping(value = "/submitHomework")
	public ResponseEntity<?> submitHomework(MultipartFile sourceCode, Student studentHomework) {
		//Homework hm = grading-app.homeworks.find(studentHomework.getHomeworkName());
		//Problem pb = hm.studentHomework.getQuestionName();
		//pb.inputFiles
		Query query = new Query(Criteria.where("homeworkName").is(studentHomework.getHomeworkName()));
		Homework hm = mongoTemplate.findOne(query, Homework.class);
		//Query query = new Query(Criteria.where("homeworkName").is(studentHomework.getHomeworkName()));
		 //List<Problem> pb = hm.getProblem
		List<Problem> problems= hm.getProblem();
		List<String> inputFiles=problems.get(0).getInputFiles();
		List<String> outputFiles=problems.get(0).getOutputFiles();
		//System.out.println("homeworrrrkkkkkkkkkkkkkkkkk ddddddddaaaaaataaaaaaaaa");
		//System.out.println(hm);
		//System.out.println(inputFiles);
		//System.out.println(outputFiles);
		//Object[] row = (Object[]) problems.get(0);
		//System.out.println(Arrays.toString(row));
		
		//System.out.println(problems.get(0).);
		String studentCodePath = "";
		Result result = new Result();
		List<Result> resultOfAllTestCases = new ArrayList<Result>();
		boolean[] testcasespassedinput = new boolean[inputFiles.size()];
		int noofpassed=0;
		System.out.println(studentHomework.getUserName()+ studentHomework.getHomeworkName() + studentHomework.getQuestionName() );
		if(sourceCode != null) {
			long startTime = System.currentTimeMillis();
			for(int k=0;k<inputFiles.size();k++) {
				
			
			fileService.handleFileUpload(sourceCode, "Student", studentHomework.getHomeworkName(), studentHomework.getQuestionName(), studentHomework.getUserName());
			String inputFilePath = fileService.generatePath("Professor-Input", studentHomework.getHomeworkName(), studentHomework.getQuestionName(), "") +inputFiles.get(k);
			String outputFilePath = fileService.generatePath("Professor-Output", studentHomework.getHomeworkName(), studentHomework.getQuestionName(), "") +outputFiles.get(k);
			//System.out.println("PATHS TO BE EXAMINED: ");
			//System.out.println("inputFilepath: " + inputFilePath );
			//System.out.println("outputFilepath: " + outputFilePath );
			studentCodePath = fileService.generatePath("Student", studentHomework.getHomeworkName(), studentHomework.getQuestionName(), studentHomework.getUserName());
			//System.out.println("studentCodePath: " + studentCodePath );
			result = c.compileAndRun(studentCodePath, inputFilePath, outputFilePath);
			//System.out.println("RREEEEEEEEEEDSuuuLLLLLLLLLLTTTt");
			//System.out.println(result.getTestCasePassed());
			resultOfAllTestCases.add(result);
			if(result.getTestCasePassed()== true) {
				noofpassed++;
			}
			testcasespassedinput[k]=result.getTestCasePassed();
		}
			 long endTime = System.currentTimeMillis();
		        long duration = (endTime - startTime);
		        System.out.println("DURATION: NNNN " + duration);
		}
	
		studentService.create(new Student(studentHomework.getUserName(), studentHomework.getHomeworkName(), studentHomework.getQuestionName(), resultOfAllTestCases, studentCodePath, ""));
		System.out.println(Arrays.toString(testcasespassedinput));
		System.out.println(resultOfAllTestCases.size());
		return new ResponseEntity<>(resultOfAllTestCases, HttpStatus.OK);
	}
	
	@CrossOrigin
	@PostMapping(value = "/uploadWriteup")
	public ResponseEntity<?> uploadWriteup(MultipartFile writeupFile, Writeup writeup) {
		String writeupPath = "";
		if(null != writeupFile) {
			writeupPath = fileService.generatePath("Writeup", writeup.getHomeworkName(), "", writeup.getUserName()) + writeupFile.getOriginalFilename();;
			fileService.handleFileUpload(writeupFile, "Writeup", writeup.getHomeworkName(), "", writeup.getUserName());
			writeup.setWriteupURL(writeupPath);
			studentService.updateWriteup(writeup);
			
			System.out.println("Write up path: " + writeupPath);
		}
		return new ResponseEntity<>("Successfully uploaded!", HttpStatus.OK);
	}
}
