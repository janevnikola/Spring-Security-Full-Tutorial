package com.nikola.student;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("management/api/v1/students") //admin API
public class StudentManagementController {
	
	
	private static final List<Student> STUDENTS=Arrays.asList( //kreirame lista na studenti
			new Student(1,"Nikola"),
			new Student(2, "linda"),
			new Student(3, "Boro")//istite students od api/v1/students
			);

	//kreirame 4 metodi koi Linda i Tom t.e korisnicite koi imaat admin role moze da pristapat
	//no tie sto mozat da menuvaat raboti otkolku tie koi mozat samo da citat ke imaat povekje privilegii
	
	
	//za da mu kazeme na Spring deka ova e getRequest
	
	//read method
	@GetMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
	public List<Student> getAllStudents(){
		
		return STUDENTS; //gi vrakjame students preku get methodot
	}
	
	
	//write
	
	@PostMapping
	@PreAuthorize("hasAuthority('student:write')")
	public void registerNewStudent(@RequestBody Student student) {//za da go zemime student od Request Body koristime @RequestBody anotacijata
		System.out.println(student);
	}
	
	//write
	@DeleteMapping(path= "{studentId}")//vo patekata specificirame studentId za koi sakame da go izbriseme 
	@PreAuthorize("hasAuthority('student:write')")
	public void deleteStudent(@PathVariable("studentId") Integer studentId) { //i za da go mapirame vo ova studentId koristime @PathVariable("studentId")
	System.out.println(studentId);	
	}
	
	
	//write
	@PutMapping(path ="{studentId}")
	@PreAuthorize("hasAuthority('student:write')")
	public void updateStudent(@PathVariable("studentId") Integer studentId,@RequestBody Student student) {
		System.out.println(String.format("%s %s ", studentId, student));
	}
	
	
}
