package com.lmsapp.lms.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lmsapp.lms.model.Colleges;

public interface CollegesRepo extends JpaRepository<Colleges, Integer> {

}
