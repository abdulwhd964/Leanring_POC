package com.demo.studentinfo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.demo.studentinfo.model.Student;

public interface StudentRepo extends MongoRepository<Student, Integer>{
	public void deleteByStudentId(int id);

	public Student findByStudentId(Object object);
}
