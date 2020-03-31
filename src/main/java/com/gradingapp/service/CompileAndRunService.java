package com.gradingapp.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
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
        //String checkinp= expectedOutput(sourceCodePath+"Main.java");
        String pathprior= "C:/Users/jyots/Desktop/Capstone/grading-backend-master/grading-backend-master/";
        String prior = "javac -cp src "+ sourceCodePath+"/"+"Main.java";
        System.out.println("Prior"+prior);

        try {
            // Boolean testCasePassed = false;
            Process pro = Runtime.getRuntime().exec(prior);
            pro.waitFor();
            System.out.println(prior + " exitValue() " + pro.exitValue());
            //System.out.println("********************");
            result = runProcess("java -cp "+ pathprior+ sourceCodePath+ ";"+ " Main "+ pathprior + inputTestFilePath);
            //result = runProcess("java -cp src "+sourceCodePath+"Main.java "+ inputTestFilePath );
            //System.out.println("java -cp "+ pathprior+ sourceCodePath+ ";"+ "Main "+ inputTestFilePath );
            //result = runProcess("java -cp src "+ prior +sourceCodePath+"Main.java "+ prior + inputTestFilePath );
            System.out.println("----------------------------------------------------------------------------------");
            System.out.println(expectedOutput.length());
            char[] tofind = expectedOutput.toCharArray();
            System.out.println(Arrays.toString(tofind));
            System.out.println("----------------------------------------------------------------------------------");
            String two=result.get("studentOutput").toString();
            System.out.println(two.length());
            for(int j=0;j<two.length();j++) {
            	System.out.println(two.charAt(j));
            }
            
            
            Boolean testCasePassed = compareResults(result.get("studentOutput").toString(), expectedOutput);
            r.setStatus((int)result.get("status"));
            r.setStudentOutput(result.get("studentOutput").toString());
            r.setExpectedOutput(expectedOutput);
            r.setErrorOutput(result.get("errorOutput").toString());
            r.setTestCasePassed(testCasePassed);
        } catch (Exception e) {
            System.out.println("failing");
        }
        System.out.println(r);
        return r;

    }

    private  HashMap<String, Object> runProcess(String command) throws Exception {
        HashMap<String, Object> result = new HashMap();
        System.out.println("COMMAND PASSED");
        System.out.println(command);
        
        Process pro = Runtime.getRuntime().exec(command);
        System.out.println("PRO");
        //System.out.println(pro);
       
        String studentOutput = getResult(command + " stdout:", pro.getInputStream());
        //System.out.println(studentOutput);
        String errorOutput = getResult(command + " stderr:", pro.getErrorStream());
        System.out.println("STUDENTOUTPUT: ");
        System.out.println(studentOutput);
        pro.waitFor();
        result.put("status", pro.exitValue() );
        result.put("studentOutput", studentOutput);
        result.put("errorOutput", errorOutput);
        System.out.println("HASHMAP VALUES FOUND:");
        System.out.println(result);
        //System.out.println(command + " exitValue() " + pro.exitValue());
        return result;
    }

    private String getResult(String cmd, InputStream ins) throws Exception {

        BufferedReader in = new BufferedReader(
                new InputStreamReader(ins));
        String output = "";
        String line = in.readLine();
        while (line != null) {
            output += line;
            line = in.readLine();
            if(line!=null)
                output +="\n";
            // System.out.println(cmd + " " + line);
        }
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
    	 System.out.println("PLEASSSSSSSSEEEEEEEEEE        SEEEEEEEEEE        HEREREREEEEEEEEE");
    	//String filter1=studentOuput.replaceAll("\s+", "");
    	System.out.println(output);
    	//String filter2=expectedOutput.replaceAll("\s+", "");
    	System.out.println(exoutput);
        return output.equals(exoutput);
    }
}
