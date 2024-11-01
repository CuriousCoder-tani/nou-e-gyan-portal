package com.lmsapp.lms.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lmsapp.lms.model.Answer;

public interface AnswerRepo extends JpaRepository<Answer, Integer> {

	@Query("SELECT a FROM Answer a WHERE a.qid = :qid")
    List<Answer> findAnswerByQid(@Param("qid") int qid);

}
