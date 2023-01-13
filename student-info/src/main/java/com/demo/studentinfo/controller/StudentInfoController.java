package com.demo.studentinfo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.studentinfo.model.Student;
import com.demo.studentinfo.repo.StudentRepo;

@RestController
public class StudentInfoController {

	@Autowired
	private StudentRepo repo;

	@Autowired
	private MongoTemplate template;
	
	@Autowired
	private KafkaTemplate<String,Student> kafkaTemplate;
	
	// get with slow service to test the sync and async
	@GetMapping("/students")
	public List<Student> getAllStudentInfoFromMongoDb() throws InterruptedException {
//		Thread.sleep(10000);
		System.out.println("IN M2");
		return repo.findAll();
	}

	// get single value
	@GetMapping("/student/{studentId}")
	public Student getStudentInfoFromMongoDb(@PathVariable("studentId") int studentId) {
		
		return repo.findByStudentId(studentId);
	}

	// post
	@PostMapping("/student")
	public Student insertStudent(@RequestBody Student student) {

		repo.save(student);
		return student;
	}
	
	@PostMapping("/student-kafka")
	public ResponseEntity<String> getUser(@RequestBody Student userInfo) {

		try {
			return ResponseEntity.status(HttpStatus.CREATED).body("Message was sent with success");
		} catch (Exception exception) {
			exception.printStackTrace();
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Message was not sent");
		}

	}
	// delete
	@DeleteMapping("/student/{studentId}")
	public String insertStudent(@PathVariable("studentId") int studentId) {

		repo.deleteByStudentId(studentId);
		return "Student Record has been deleted : " + studentId;
	}

	// update
	@PutMapping("/student")
	public Student updateStudent(@RequestBody Student student) {

		Query query = new Query(new Criteria("studentId").is(student.getStudentId()));
		Update update = new Update().set("studentName", student.getStudentName());
		update.set("age", student.getAge());
		update.set("rollNo", student.getRollNo());
		template.upsert(query, update, "studentinfo");
		return student;
	}
	
	@PostMapping("/student-info-kafka")
	public ResponseEntity<String> getStudentInfoFromMongoDb(@RequestBody Student student) {
		
		kafkaTemplate.send("student-info-mongo-data",student);
		 
		return new ResponseEntity<String>("Student has been inserted", HttpStatus.OK);
	}

	
	
}
