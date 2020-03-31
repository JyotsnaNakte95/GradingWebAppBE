package com.gradingapp.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.gradingapp.bean.Homework;
import com.gradingapp.bean.Problem;

@Repository
@Qualifier("homeworkDao")
public class HomeworkDaoImpl implements HomeworkDao{
	
	@Autowired
    MongoTemplate mongoTemplate;

	@Override
	public boolean create(Homework h) {
		boolean isHWExist = false;
		Query query = new Query(Criteria.where("homeworkName").is(h.getHomeworkName()));
		Homework result = null;
		result = mongoTemplate.findOne(query, Homework.class);
		if(result == null) {
			List<Problem> problems = new ArrayList<>();
			h.setProblem(problems);
			mongoTemplate.insert(h);
		}else {
			isHWExist = true;
		}
		return isHWExist;
	}

	@Override
	public void update(Homework h) {
		mongoTemplate.save(h);
	}

	@Override
	public void delete(Homework h) {
		mongoTemplate.remove(h);
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Homework find(Homework h) {
		// TODO Auto-generated method stub
		return null;
		
	}

	@Override
	public List<Homework> availableHomework() {
		DateFormat dateFormat =  new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(new Date());
		System.out.println("Today's date: " + date);
		
		Query query = new Query(Criteria.where("dueDate").gte(date));
		List<Homework> homeworks = mongoTemplate.find(query, Homework.class);
		Set set = new HashSet(homeworks);
		List<Homework> test = new ArrayList(set);
		
		System.out.println("Homeworks count: " + test.size());
		return test;
	}

	@Override
	public void updateProblem(Problem p) {
		Query query = new Query(Criteria.where("homeworkName").is(p.getHomeworkName()));
		Update updateCmd = new Update();
		updateCmd.addToSet("problem", p);
		mongoTemplate.updateFirst(query, updateCmd, Homework.class);
	}

	@Override
	public List<Problem> findProblem(String homeworkName) {
		List<Problem> problems = null;
		Query query = new Query(Criteria.where("homeworkName").is(homeworkName));
		Homework result = mongoTemplate.findOne(query, Homework.class);
		System.out.println("Homework: " + result);
		if(null != result) {
			problems = result.getProblem();
			
			System.out.println("Problems count: " + problems.size());
		}
		return problems;
	}

	@Override
	public List<Homework> findAll() {
		Query query = new Query();
		List<Homework> homeworks = mongoTemplate.find(query, Homework.class);
		System.out.println("Homeworks count: " + homeworks.size());
		return homeworks;
	}

}
