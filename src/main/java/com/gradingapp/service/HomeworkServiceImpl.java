package com.gradingapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gradingapp.bean.Homework;
import com.gradingapp.bean.Problem;
import com.gradingapp.dao.HomeworkDao;

@Service("homeworkService")
public class HomeworkServiceImpl implements HomeworkService{
	
	@Autowired
	HomeworkDao homeworkDao;

	@Override
	public boolean create(Homework h) {
		return homeworkDao.create(h);
	}

	@Override
	public void update(Homework h) {
		homeworkDao.update(h);
	}

	@Override
	public void delete(Homework h) {
		homeworkDao.delete(h);
	}

	@Override
	public void deleteAll() {
		homeworkDao.deleteAll();
	}

	@Override
	public Homework find(Homework h) {
		return homeworkDao.find(h);
	}

	@Override
	public List<Homework> availableHomework() {
		return homeworkDao.availableHomework();
	}

	@Override
	public void updateProblem(Problem p) {
		homeworkDao.updateProblem(p);
		
	}

	@Override
	public List<Problem> findProblem(String homeworkName) {
		return homeworkDao.findProblem(homeworkName);
	}

	@Override
	public List<Homework> findAll() {
		return homeworkDao.findAll();
	}

}
