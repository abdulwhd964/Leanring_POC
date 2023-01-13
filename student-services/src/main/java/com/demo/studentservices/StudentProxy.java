package com.demo.studentservices;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.demo.studentservices.model.Student;

@FeignClient(name="student-info",url="localhost:8081")
public interface StudentProxy {
	@GetMapping("/students")
	Student[] getAllStudentRecords();
	
	@GetMapping("/student/{studentId}")
	Student getStudentRecord(@PathVariable("studentId") int studentId);

	@DeleteMapping("/student/{studentId}")
	void deleteStudentRecord(@PathVariable("studentId") int studentId);

	@PutMapping("/student")
	void updateStudentRecord(@RequestBody Student student);

	@PostMapping("/student")
	void insertStudentRecord(@RequestBody Student student);

}
