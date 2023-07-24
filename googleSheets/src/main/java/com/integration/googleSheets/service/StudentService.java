package com.integration.googleSheets.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.integration.googleSheets.model.Student;
import com.integration.googleSheets.repository.StudentRepository;

@Configuration
@Service
public class StudentService {

	@Autowired
	private StudentRepository repository;

	public ArrayList<Student> getAllStudent() {
		ArrayList<Student> allStudent = new ArrayList<Student>();
		try {
			List<List<Object>> values = repository.getAllStudent();
			// A1, B1, C1 is titles for a student in sheets (firstName, middleName,
			// lastName), so we delete them.
			values.remove(0);
			values.forEach(object -> {
				Student student = new Student();
				student.setId(values.indexOf(object) + 2);
				student.setFirstName(object.get(0).toString());
				student.setMiddleName(object.get(1).toString());
				student.setLastName(object.get(2).toString());
				allStudent.add(student);

			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		return allStudent;
	}

	public Student getStudentById(int id) {
		Student student = new Student();
		List<List<Object>> values;
		try {
			values = repository.getStudentById(id);
			student.setId(id);
			student.setFirstName(values.get(0).get(0).toString());
			student.setMiddleName(values.get(0).get(1).toString());
			student.setLastName(values.get(0).get(2).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return student;
	}

	public Student addStudent(Student student) {
		try {

			return repository.addStudent(student);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public Student updateStudentById(int id, Student student) {
		try {

			return repository.updateStudentById(id, student);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public String deleteStudentById(int id) {
		try {
			
			return repository.deleteStudentById(id);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
