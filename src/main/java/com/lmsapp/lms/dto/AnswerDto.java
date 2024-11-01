package com.lmsapp.lms.dto;

public class AnswerDto {
	private int qid;
	private String answer;
	private String posteddate;
	private String answeredby;
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
