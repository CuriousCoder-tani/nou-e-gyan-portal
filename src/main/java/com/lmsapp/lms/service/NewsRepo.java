package com.lmsapp.lms.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lmsapp.lms.model.News;

public interface NewsRepo extends JpaRepository<News, Integer> {

}
