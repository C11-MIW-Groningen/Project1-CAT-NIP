package com.project1CATNIP.CATNIP.controller;

/*
 *@Author: Marcel Tubben <mhg.tubben@st.hanze.nl>
 *
 *The Purpose
 */

import com.project1CATNIP.CATNIP.model.Course;
import com.project1CATNIP.CATNIP.model.Student;
import com.project1CATNIP.CATNIP.model.Test;
import com.project1CATNIP.CATNIP.repository.CourseRepository;
import com.project1CATNIP.CATNIP.repository.StudentRepository;
import com.project1CATNIP.CATNIP.repository.TestAttemptRepository;
import com.project1CATNIP.CATNIP.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class TestAttemptController {

    private final TestAttemptRepository testAttemptRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final TestRepository testRepository;

    @GetMapping("/testattempts/{studentId}/{courseId}")
    private String showTestAttemptsForStudent(
            @PathVariable("studentId") Long studentId, @PathVariable("courseId") Long courseId, Model model) {

        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if (optionalStudent.isEmpty()) {
            return "redirect:/student/all";
        }
        Student student = optionalStudent.get();

        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isEmpty()) {
            return "redirect:/student/all";
        }
        Course course = optionalCourse.get();

        List<Test> testList = testRepository.findByCourse(course);
        if (testList.isEmpty()) {
            return "redirect:/student/all";
        }

        model.addAttribute("allTestAttempts",
                testAttemptRepository.findTestAttemptsByStudentAndTestIn(student, testList));
        model.addAttribute("thisStudent", student);

        return "testAttemptsOverviewStudent";
    }

}
