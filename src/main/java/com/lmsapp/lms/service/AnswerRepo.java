package com.lmsapp.lms.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lmsapp.lms.model.Answer;

public interface AnswerRepo extends JpaRepository<Answer, Integer> {


    List<Answer> findAllByOrderByPosteddateDesc();


}
