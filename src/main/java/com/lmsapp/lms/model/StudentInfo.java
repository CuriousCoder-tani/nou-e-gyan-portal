package com.lmsapp.lms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Student")
public class StudentInfo {
	
	@Id
	@Column(length=50, nullable=false)
	private int rollno;
	
	@Column(length=50, nullable=false)
	private String name;
	
	@Column(length=6, nullable=false)
	private String gender;
	
	@Column(length=255, nullable=false)
	private String address;
	
	@Column(length=50, nullable=false)
	private String program;
	
	@Column(length=50, nullable=false)
	private String branch;
	
	@Column(length=30, nullable=false)
	private String year;
	
	@Column(length=10, nullable=false)
	private String contactno;
	
	@Column(length=50, nullable=false)
	private String emailaddress;
	
	@Column(length=50, nullable=false)
	private String password;
	
	@Column(length=30, nullable=false)
	private String regdate;

	@Column(length = 500, nullable = true)
	private String profilepic;
	
	public String getProfilepic() {
		return profilepic;
	}
	public void setProfilepic(String profilepic) {
		this.profilepic = profilepic;
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	
	
}
