package com.demo.studentinfo.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "studentinfo")
public class Student {

		private Integer studentId;
		private String studentName;
		private int age;
		private int rollNo;
		private String api;
		
		public int getStudentId() {
			return studentId;
		}
		public void setStudentId(int studentId) {
			this.studentId = studentId;
		}
		public String getStudentName() {
			return studentName;
		}
		public void setStudentName(String studentName) {
			this.studentName = studentName;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		public int getRollNo() {
			return rollNo;
		}
		public void setRollNo(int rollNo) {
			this.rollNo = rollNo;
		}
		
		public String getApi() {
			return api;
		}
		public void setApi(String api) {
			this.api = api;
		}
		public Student(int studentId, String studentName, int age,int rollNo) {
			super();
			this.studentId = studentId;
			this.studentName = studentName;
			this.age = age;
			this.rollNo = rollNo;
			
		}
		public Student() {
			super();
			// TODO Auto-generated constructor stub
		}
		@Override
		public String toString() {
			return "Student [studentId=" + studentId + ", studentName=" + studentName + ", age=" + age +", rollNo=" + rollNo + "]";
		}
}
