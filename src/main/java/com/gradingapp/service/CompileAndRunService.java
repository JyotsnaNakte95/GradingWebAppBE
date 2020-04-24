package com.gradingapp.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
//import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.gradingapp.bean.Result;

@Service
public class CompileAndRunService {
//loop through array list n store in boolean array true false.	
	public  Result compileAndRun(String sourceCodePath, String inputTestFilePath, String outputTestFilePath) {
        Result r = new Result();
        HashMap<String, Object> result = new HashMap() ;
        String expectedOutput = expectedOutput(outputTestFilePath);
        System.out.println("EXPECTEDDD OUTPUTTTT");
        System.out.println(expectedOutput);
        //String checkinp= expectedOutput(sourceCodePath+"Main.java");
        String pathprior= "C:/Users/jyots/Desktop/Capstone/grading-backend-master/grading-backend-master/";
        String prior = "javac -cp src "+ sourceCodePath+"/"+"Main.java";
        //System.out.println("Prior "+prior);
       

        try {
            // Boolean testCasePassed = false;
            Process pro = Runtime.getRuntime().exec(prior);
            pro.waitFor();
            //System.out.println(prior + " exitValue() " + pro.exitValue());
            //long startTime = System.currentTimeMillis();
            result = runProcess("java -cp "+ pathprior+ sourceCodePath+ ";"+ " Main "+ pathprior + inputTestFilePath);
            Boolean testCasePassed = compareResults(result.get("studentOutput").toString(), expectedOutput);
            r.setStatus((int)result.get("status"));
            r.setStudentOutput(result.get("studentOutput").toString());
            r.setExpectedOutput(expectedOutput);
            r.setErrorOutput(result.get("errorOutput").toString());
            r.setTestCasePassed(testCasePassed);
            r.setDuration((long)result.get("duration"));
        } catch (Exception e) {
        	System.out.println(e);
            System.out.println("failing");
        }
        //System.out.println(r);
        return r;

    }

    private  HashMap<String, Object> runProcess(String command ) throws Exception {
        HashMap<String, Object> result = new HashMap<String, Object>();
        System.out.println("hey im in runprocess");
       
        Process pro = Runtime.getRuntime().exec(command);
        String studentOutput = getResult(command + " stdout:", pro.getInputStream(),result );
        System.out.println("checking input stream:");
        System.out.println(pro.getInputStream());
        pro.waitFor();
        //long duration = findduration(command + " duration:", pro.getInputStream());
        //System.out.println(pro.getInputStream());
        pro.waitFor();
        String errorOutput = getResult(command + " stderr:", pro.getErrorStream(), result);
        pro.waitFor();       
        result.put("status", pro.exitValue() );
        result.put("studentOutput", studentOutput);
        result.put("errorOutput", errorOutput);
        //result.put("duration", duration);
        
        System.out.println("RESULT");
        System.out.println(result);
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
        
        return result;
    }
    
    /*
    private long findduration(String cmd ,InputStream ins) throws Exception {
System.out.println("I entered in duration function");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(ins));
        long durationtime = 0L;
        
        String line = in.readLine();
        System.out.println("LINE first of duration:   ");
        System.out.println(line);
        //System.out.println("here");
        //durationtime=Long.parseLong(line);
        while (line != null) {
        	//durationtime=Long.parseLong(line);
        	System.out.println("DURATIONNNNNN  ");
        	System.out.println(durationtime);
        	System.out.println(line);
        	
            line = in.readLine();
            if(line!=null) {
            	if(line.equals("duration")) {
            		System.out.println("hiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
            		
            	}
            }
            // System.out.println(cmd + " " + line);
        }
        return durationtime;
    }
*/
    private String getResult(String cmd, InputStream ins, HashMap<String, Object> result) throws Exception {
    	System.out.println("I entered in get Result function");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(ins));
        String output = "";
        //long duration=0;
        String line = in.readLine();
        while (line != null) {
        	System.out.println("what lines you have in input stream:");
        	System.out.println(line);
            output += line;
            line = in.readLine();
            //System.out.println("LINEEEEEEEEEEEEE");
            //System.out.println(line);
            if(line!=null) {
            	
            	if(line.equals("duration")) {
            		line = in.readLine();
            		long durationtime=Long.parseLong(line);
            		result.put("duration", durationtime);
            		
            		break;
            	}
            	output +="\n";
            }
            
            // System.out.println(cmd + " " + line);
        }
        //System.out.println("OUTPUT: ");
        System.out.println(output);
        return output;
    }

    private  String expectedOutput(String path) {
        String content = null;
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines( Paths.get(path), StandardCharsets.UTF_8)) 
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
       
        return contentBuilder.toString();
        /*
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            content = lines.collect(Collectors.joining(System.lineSeparator()));

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("Expected Output:");
        System.out.println(path);
        System.out.println(content.length()); 
        System.out.println("Expected Output Over");
        
        return content;
        */
    }

    private  Boolean compareResults(String studentOuput, String expectedOutput ) {
    	 String output= studentOuput.replaceAll("[^a-zA-Z0-9]", "");
    	 String exoutput= expectedOutput.replaceAll("[^a-zA-Z0-9]", "");
        return output.equals(exoutput);
    }
}
