package com.gradingapp.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;

import com.gradingapp.bean.Homework;
import com.gradingapp.bean.Problem;
import com.gradingapp.service.FileService;
import com.gradingapp.service.HomeworkService;


@CrossOrigin("*")
@RestController
public class HomeworkController {
	
	@Autowired
	FileService fileService;
	
	@Autowired
	HomeworkService homeworkService;
	
    @CrossOrigin
	@PostMapping(value = "/create" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> create(@ModelAttribute Homework homework) {       
		boolean isHWExist = homeworkService.create(homework);
		if(isHWExist) {
			return new ResponseEntity<>("Homework already exists!", HttpStatus.CONFLICT);
		}else {
			return new ResponseEntity<>("Successfully uploaded!", HttpStatus.OK);
		}
	}
    @CrossOrigin
	@PostMapping(value = "/upload")
	public ResponseEntity<?> upload(MultipartFile inputFile[], MultipartFile outputFile[], Problem problem) {
    	//System.out.println("filessssssssssssss");
		//System.out.println(Arrays.toString(inputFile));
    	//HashMap<String, > result = new HashMap();
		List<String> inputFileNames = new ArrayList<String>();
    	for(MultipartFile file : inputFile) {
			//ResponseEntity<?> uploaded = 
			
    		inputFileNames.add(StringUtils.cleanPath(file.getOriginalFilename()));
		}  
    	List<String> outputFileNames = new ArrayList<String>();
    	for(MultipartFile file : outputFile) {
			//ResponseEntity<?> uploaded = 
			
    		outputFileNames.add(StringUtils.cleanPath(file.getOriginalFilename()));
		}  
    	
		//System.out.println(problem.getHomeworkName()+   "    " + problem.getProblemName() + "    "+  problem.getProblemDescription());
		//System.out.println("IIIINNNnputFileNames");
		//System.out.println(inputFileNames);
		//System.out.println("OOOOOOUUUTTTputFileNames");
		//System.out.println(outputFileNames);
		//System.out.println(problem.getHomeworkName()+   "    " + problem.getProblemName() + "    "+  problem.getProblemDescription());
		
		fileService.handleFileUploads(inputFile, "Professor-Input", problem.getHomeworkName(), problem.getProblemName(), "");
		fileService.handleFileUploads(outputFile, "Professor-Output", problem.getHomeworkName(), problem.getProblemName(), "");
		
		homeworkService.updateProblem(new Problem(problem.getProblemName(), problem.getProblemDescription(),problem.getHomeworkName(),inputFileNames, outputFileNames));
		return new ResponseEntity<>("Successfully uploaded!", HttpStatus.OK);
	}
  
     /*
      
    @CrossOrigin
	@PostMapping(value = "/upload")
	public ResponseEntity<?> upload(MultipartFile inputFiles, MultipartFile outputFiles[], Problem problem) {
    	//System.out.println("input filesss   :"+ Arrays.toString(inputFiles));
    	//System.out.println(Arrays.toString(outputFiles));
    	/*
    	List<String> inputFileNames = new ArrayList<String>();
    	for(MultipartFile file : inputFiles) {
			//ResponseEntity<?> uploaded = 
			
    		inputFileNames.add(StringUtils.cleanPath(file.getOriginalFilename()));
		}  
    	List<String> outputFileNames = new ArrayList<String>();
    	for(MultipartFile file : outputFiles) {
			//ResponseEntity<?> uploaded = 
			
    		outputFileNames.add(StringUtils.cleanPath(file.getOriginalFilename()));
		}  
    	
		System.out.println(problem.getHomeworkName()+   "    " + problem.getProblemName() + "    "+  problem.getProblemDescription());
		System.out.println("IIIINNNnputFileNames");
		System.out.println(inputFileNames);
		System.out.println("OOOOOOUUUTTTputFileNames");
		System.out.println(outputFileNames);
		*/
    /*
		fileService.handleFileUpload(inputFiles, "Professor-Input", problem.getHomeworkName(), problem.getProblemName(), "");
		fileService.handleFileUploads(outputFiles, "Professor-Output", problem.getHomeworkName(), problem.getProblemName(), "");
		
		homeworkService.updateProblem(new Problem(problem.getProblemName(), problem.getProblemDescription(),problem.getHomeworkName()));
		return new ResponseEntity<>("Successfully uploaded!", HttpStatus.OK);
	}
   
    */
    @CrossOrigin
    @RequestMapping(value = "/availableHomework", method = RequestMethod.GET)
    public ResponseEntity<?> availableHomework(){
    	List<String> homeworkNames = new ArrayList<String>();
    	List<Homework> homeworks = homeworkService.availableHomework();
    	for(Homework homework: homeworks) {
    		homeworkNames.add(homework.getHomeworkName());
    	}
    	
    	Collections.sort(homeworkNames);
    	System.out.println("Homeworknames list: " + homeworkNames);
    	
    	return new ResponseEntity<>(homeworkNames, HttpStatus.OK);
    }
    
    @CrossOrigin
	@GetMapping(value = "/findProblem")
    public ResponseEntity<?> find(@RequestParam("homeworkName") String homeworkName){
    	System.out.println("Homework name: " + homeworkName);
    	List<String> problemNames = new ArrayList<String>();
    	List<Problem> problems = homeworkService.findProblem(homeworkName);
    	for(Problem problem: problems) {
    		problemNames.add(problem.getProblemName());
    	}
    	
    	Collections.sort(problemNames);
    	return new ResponseEntity<>(problemNames, HttpStatus.OK);
    }
    
    @CrossOrigin
	@GetMapping(value = "/findAllHomework")
    public ResponseEntity<?> findAll(){
    	List<String> homeworkNames = new ArrayList<String>();
    	List<Homework> homeworks = homeworkService.findAll();
    	for(Homework homework: homeworks) {
    		homeworkNames.add(homework.getHomeworkName());
    	}
    	
    	Collections.sort(homeworkNames);
    	System.out.println("Homeworknames list: " + homeworkNames);
    	return new ResponseEntity<>(homeworkNames, HttpStatus.OK);
    }
}