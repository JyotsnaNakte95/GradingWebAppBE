package com.gradingapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gradingapp.bean.Student;
import com.gradingapp.bean.Writeup;
import com.gradingapp.dao.StudentDao;

@Service("studentService")
public class StudentServiceImpl implements StudentService{
	
	@Autowired
	StudentDao studentDao;

	@Override
	public void create(Student s) {
		studentDao.create(s);
	}

	@Override
	public void update(Student s) {
		studentDao.update(s);
	}

	@Override
	public void delete(Student s) {
		studentDao.delete(s);
		
	}

	@Override
	public void deleteAll() {
		studentDao.deleteAll();
	}

	@Override
	public Student find(Student s) {
		return studentDao.find(s);
	}

	@Override
	public List<Student> findAll() {
		return studentDao.findAll();
	}

	@Override
	public void updateWriteup(Writeup writeup) {
		studentDao.updateWriteup(writeup);
		
	}

}
