package com.greatlearning.smsapp_demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greatlearning.smsapp_demo.entity.Student;
import com.greatlearning.smsapp_demo.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@RequestMapping("/list")
	public String listStudent(Model theModel) {

		List<Student> students = studentService.findAll();
		theModel.addAttribute("students", students);
		return "list-students";

	}

	@RequestMapping("/showFormForAdd")
	public String showFormforAdd(Model theModel) {
		Student theStudent = new Student();

		theModel.addAttribute("Student", theStudent);

		return "Student-form";

	}

	@RequestMapping("/showFormForUpdate")
	public String showFormforUpdate(@RequestParam("studentId") int id, Model theModel) {
		Student theStudent = studentService.findById(id);

		theModel.addAttribute("Student", theStudent);

		return "Student-form";

	}

	@PostMapping("/save")
	public String saveBook(@RequestParam("id") int id, @RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName, @RequestParam("course") String course,
			@RequestParam("country") String country) {

		System.out.println(id);

		Student theStudent;

		if (id != 0) {
			theStudent = studentService.findById(id);
			theStudent.setFirstName(firstName);
			theStudent.setLastName(lastName);
			theStudent.setCourse(course);
			theStudent.setCountry(country);
		} else
			theStudent = new Student(id, firstName, lastName, course, country);
		studentService.save(theStudent);

		return "redirect:/students/list";

	}

	@RequestMapping("/delete")
	public String delete(@RequestParam("studentId") int theId) {

		studentService.deleteById(theId);

		return "redirect:/students/list";

	}
}
