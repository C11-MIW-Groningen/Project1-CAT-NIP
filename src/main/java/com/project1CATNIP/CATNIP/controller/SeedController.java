package com.project1CATNIP.CATNIP.controller;

/*
 *@Author: Marcel Tubben <mhg.tubben@st.hanze.nl>
 *
 *The Purpose
 */

import com.project1CATNIP.CATNIP.model.Cohort;
import com.project1CATNIP.CATNIP.model.Course;
import com.project1CATNIP.CATNIP.model.Teacher;
import com.project1CATNIP.CATNIP.model.compositeKey.CohortId;
import com.project1CATNIP.CATNIP.repository.CohortRepository;
import com.project1CATNIP.CATNIP.repository.CourseRepository;
import com.project1CATNIP.CATNIP.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class SeedController {

    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final CohortRepository cohortRepository;

    @GetMapping("/seed")
    private String seedDatabase() {

        Teacher wally = new Teacher();
        wally.setFirstName("Walther");
        wally.setInfixName("De");
        wally.setLastName("Wit");
        wally.setEmailAddress("w.d.wit@cat-nip.nl");
        teacherRepository.save(wally);

        Teacher piet = new Teacher();
        piet.setFirstName("Pieter");
        piet.setInfixName("Van de");
        piet.setLastName("Breedband");
        piet.setEmailAddress("p.vd.breedband@cat-nip.nl");
        teacherRepository.save(piet);

        Course softwareEngineering = new Course();
        softwareEngineering.setNameCourse("Software Engineering");
        courseRepository.save(softwareEngineering);

        Cohort se11 = new Cohort();
        CohortId se11Id = new CohortId();
        se11Id.setNumber(11);
        se11Id.setCourse(softwareEngineering);
        se11.setCohortId(se11Id);
        cohortRepository.save(se11);

        return "redirect:/cohort";
    }
}
