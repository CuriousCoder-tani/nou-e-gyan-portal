package com.lmsapp.lms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "response")
public class Response {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(length = 50, nullable = false)
	private int rollno;

	@Column(length = 50, nullable = false)
	private String name;

	@Column(length = 50, nullable = false)
	private String program;

	@Column(length = 50, nullable = false)
	private String branch;

	@Column(length = 50, nullable = false)
	private String year;

	@Column(length = 10, nullable = false)
	private String contactno;

	@Column(length = 50, nullable = false)
	private String emailaddress;

	@Column(length = 50, nullable = false)
	private String responsetype;

	@Column(length = 500, nullable = false)
	private String responsesubject;

	@Column(length = 1000, nullable = false)
	private String responsetext;

	@Column(length = 30, nullable = false)
	private String responsedate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRollno() {
		return rollno;
	}

	public void setRollno(int rollno) {
		this.rollno = rollno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getContactno() {
		return contactno;
	}

	public void setContactno(String contactno) {
		this.contactno = contactno;
	}

	public String getEmailaddress() {
		return emailaddress;
	}

	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}

	public String getResponsetype() {
		return responsetype;
	}

	public void setResponsetype(String responsetype) {
		this.responsetype = responsetype;
	}

	public String getResponsesubject() {
		return responsesubject;
	}

	public void setResponsesubject(String responsesubject) {
		this.responsesubject = responsesubject;
	}

	public String getResponsetext() {
		return responsetext;
	}

	public void setResponsetext(String responsetext) {
		this.responsetext = responsetext;
	}

	public String getResponsedate() {
		return responsedate;
	}

	public void setResponsedate(String responsedate) {
		this.responsedate = responsedate;
	}
	
}
