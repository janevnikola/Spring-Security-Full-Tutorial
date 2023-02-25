package com.nikola.student;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {
	
	private static final List<Student> STUDENTS=Arrays.asList( //kreirame lista na studenti
			new Student(1,"Nikola"),
			new Student(2, "linda"),
			new Student(3, "Boro")
			);
	
	@GetMapping(path ="{studentId}") //studentId go zimame od patekata koja ke ja dade klientot t.e student id
	public Student getStudent(@PathVariable("studentId") Integer studentId) {
		return STUDENTS.stream()//stream students
				.filter(student -> studentId.equals(student.getStudentId()))//ako student id = student.getStudentId koj go imame
				.findFirst()//vrati go
				.orElseThrow(()->new IllegalStateException("Student" + studentId+"does not exists"));// ako ne frli exception
	}

}
