package com.gradingapp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gradingapp.bean.GraderData;
import com.gradingapp.bean.Student;
import com.gradingapp.bean.StudentData;
import com.gradingapp.service.GraderService;

@CrossOrigin("*")
@RestController
public class GraderController {
	
	@Autowired
	private GraderService graderService;
	
	@CrossOrigin
	@GetMapping(value = "/getHomeworkSubmissions")
	public ResponseEntity<?> getHomeworkSubmissions(@RequestParam("homeworkName") String homeworkName){
		List<StudentData> studentData = graderService.getHomeworkSubmissions(homeworkName);
		return new ResponseEntity<>(studentData, HttpStatus.OK);
	}
	
	@CrossOrigin
	@GetMapping(value = "/getSubmissionFiles")
	public ResponseEntity<?> getSubmissionFiles(@RequestParam("homeworkName") String homeworkName,
			@RequestParam("questionName") String questionName, @RequestParam("userName") String userName){
		
		System.out.println("Student Name: "+ homeworkName);
		System.out.println("Prob name: "+ questionName);
		System.out.println("User name: "+ userName);
		
		GraderData graderData = graderService.getSubmissionFiles(homeworkName, questionName, userName);
		
		return new ResponseEntity<>(graderData, HttpStatus.OK);
	}
	
	@CrossOrigin
	@PostMapping(value = "/submitGrades")
	public ResponseEntity<?> submitGrades(Student student) {
		System.out.println("Inside grader");
		graderService.submitGrades(student);
		return new ResponseEntity<>("Successfully uploaded!", HttpStatus.OK);
	}
	
	@RequestMapping(value="/download", method=RequestMethod.GET) 
	public ResponseEntity<Object> downloadFile(@RequestParam("fileName") String filename) throws IOException  {
		FileWriter filewriter =  null;
		try {
			File file = new File(filename);
			
			System.out.println("Filename: " + filename);
			
			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
			HttpHeaders headers = new HttpHeaders();
			headers.add("content-disposition", "inline;filename=" + filename);
		    headers.setContentDispositionFormData(filename, filename);
		    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.parseMediaType("application/txt")).body(resource);
			return responseEntity;
		} catch (Exception e ) {
			return new ResponseEntity<>("error occurred", HttpStatus.INTERNAL_SERVER_ERROR);	
		} finally {
			if(filewriter!=null)
				filewriter.close();
		}
	}
}
