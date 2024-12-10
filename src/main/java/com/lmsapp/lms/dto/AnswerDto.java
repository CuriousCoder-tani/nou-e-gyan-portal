package com.lmsapp.lms.dto;

import com.lmsapp.lms.model.Question;

public class AnswerDto {
	private Question qid;
	private String answer;
	private String posteddate;
	private String answeredby;
	public Question getQid() {
		return qid;
	}
	public void setQid(Question qid) {
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