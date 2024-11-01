package com.lmsapp.lms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="answer")
public class Answer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int aid;
	@Column(length = 50, nullable = false)
	private int qid;
	@Column(length = 1000, nullable = false)
	private String answer;
	@Column(length = 50, nullable = false)
	private String posteddate;
	@Column(length = 50, nullable = false)
	private String answeredby;
	public int getAid() {
		return aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
	}
	
	public int getQid() {
		return qid;
	}
	public void setQid(int qid) {
		this.qid = qid;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getPosteddate() {
		return posteddate;
	}
	public void setPosteddate(String posteddate) {
		this.posteddate = posteddate;
	}
	public String getAnsweredby() {
		return answeredby;
	}
	public void setAnsweredby(String answeredby) {
		this.answeredby = answeredby;
	}
	
}
