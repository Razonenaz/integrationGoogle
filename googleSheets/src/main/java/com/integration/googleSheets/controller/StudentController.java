package com.integration.googleSheets.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.integration.googleSheets.model.Student;
import com.integration.googleSheets.service.StudentService;

@RestController
public class StudentController {

	@Autowired
	private StudentService service;

	@GetMapping("/student/get")
	public ArrayList<Student> getAll() {

		return service.getAllStudent();
	}

	@GetMapping("/student/get/{id}")
	public Student getStudentById(@PathVariable("id") int id) {

		return service.getStudentById(id);
	}

	@PostMapping("/student/add")
	public Student addStudent(@RequestBody Student student) {

		return service.addStudent(student);
	}

	@PutMapping("/student/update/{id}")
	public Student updateStudentById(@PathVariable("id") int id, @RequestBody Student student) {

		return service.updateStudentById(id, student);
	}
	
	@DeleteMapping("/student/delete/{id}")
	public String deleteStudentById(@PathVariable("id") int id) {
		
		return service.deleteStudentById(id-1);
	}
}
