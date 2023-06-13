package com.project1CATNIP.CATNIP.controller;

/*
 *@Author: Marcel Tubben <mhg.tubben@st.hanze.nl>
 *
 *The Purpose
 */

import com.project1CATNIP.CATNIP.model.Teacher;
import com.project1CATNIP.CATNIP.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class SeedController {

    private final TeacherRepository teacherRepository;

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

        return "redirect:/teacher";
    }
}
