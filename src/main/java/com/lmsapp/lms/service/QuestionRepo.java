package com.lmsapp.lms.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lmsapp.lms.model.Question;

public interface QuestionRepo extends JpaRepository<Question, Integer> {

    List<Question> findAllByOrderByPosteddateDesc();

}
