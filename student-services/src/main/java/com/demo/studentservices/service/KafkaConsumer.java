package com.demo.studentservices.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.demo.studentservices.StudentProxy;
import com.demo.studentservices.model.Student;

@Service
public class KafkaConsumer {

	@Autowired
	private StudentProxy proxy;
	
	Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
	
	@KafkaListener(topics = "student-info-mongo-data",groupId = "group_id",containerFactory = "kafkaListenerContainerFactory")
	public void consume(Student student) {
		
		System.out.println("Student Object from kafka" +student);
		logger.info("Student Object from kafka {}", student);
		
		proxy.insertStudentRecord(student);
		
		
	}
}
