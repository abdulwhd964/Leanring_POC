package com.demo.studentservices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.demo.studentservices.StudentProxy;
import com.demo.studentservices.model.Student;

@RestController
public class StudentServiceController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private StudentProxy proxy;

	private static String urlGETList = "http://student-info:8081/";

	@GetMapping("/student-info-test")
	public String returnSomeString() {
		return "Application is up and running";
	}

	@GetMapping("/student-info")
	public Student[] getAllStudentDetailsFromStudentInfo() {

		ResponseEntity<Student[]> responseEntity = restTemplate.getForEntity(urlGETList + "/students", Student[].class);
		Student[] objects = responseEntity.getBody();

		for (Student obj : objects) {
			obj.setApi("Rest template");
		}
		return objects;
	}

	@GetMapping("/student-info-feign")
	public Student[] getAllStudentDetailsFromStudentInfoUsingFeign() {

		Student[] objects = proxy.getAllStudentRecords();

		for (Student obj : objects) {
			obj.setApi("Feign");
		}
		return objects;
	}

	@GetMapping("/student-info/{studentId}")
	public Student getStudentDetailsFromStudentInfo(@PathVariable("studentId") int studentId) {

		ResponseEntity<Student> responseEntity = restTemplate.getForEntity(urlGETList + "/student/" + studentId,
				Student.class);
		Student object = responseEntity.getBody();
		object.setApi("Rest template");

		return object;
	}

	@GetMapping("/student-info-feign/{studentId}")
	public Student getStudentDetailsFromStudentInfoUsingFeign(@PathVariable("studentId") int studentId) {

		Student object = proxy.getStudentRecord(studentId);

		object.setApi("Feign");
		return object;
	}

	@DeleteMapping("/student-info/{studentId}")
	public String deleteStudentFromInfo(@PathVariable("studentId") int studentId) {

		restTemplate.delete(urlGETList + "/student/" + studentId);
		return "Student has been deleted :" + studentId;
	}

	@DeleteMapping("/student-info-feign/{studentId}")
	public String deleteStudentFromInfoUsingFeign(@PathVariable("studentId") int studentId) {

		proxy.deleteStudentRecord(studentId);
		return "Student has been deleted :" + studentId;
	}

	@PutMapping("/student-info")
	public Student updateStudentFromInfo(@RequestBody Student student) {

		restTemplate.put(urlGETList + "/student", student);
		return student;
	}

	@PutMapping("/student-info-feign")
	public Student updateStudentFromInfoUsingFeign(@RequestBody Student student) {

		proxy.updateStudentRecord(student);
		return student;
	}

	@PostMapping("/student-info")
	public Student insertStudentFromInfo(@RequestBody Student student) {

		restTemplate.postForEntity(urlGETList + "/student", student, Student.class);
		return student;
	}

	@PostMapping("/student-info-feign")
	public Student insertStudentFromInfoUsingFeign(@RequestBody Student student) {

		proxy.insertStudentRecord(student);
		return student;
	}
}
