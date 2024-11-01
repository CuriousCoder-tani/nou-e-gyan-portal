package com.lmsapp.lms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "colleges")
public class Colleges {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int collegeId;
	@Column( length = 255 , nullable = false)
	private String studycenter;
	@Column( length = 20 , nullable = false)
	private String centercode;
	@Column( length = 50 , nullable = false)
	private String city;
	@Column( length = 50 , nullable = false)
	private String state;
	public int getCollegeId() {
		return collegeId;
	}
	public void setCollegeId(int collegeId) {
		this.collegeId = collegeId;
	}
	public String getStudycenter() {
		return studycenter;
	}
	public void setStudycenter(String studycenter) {
		this.studycenter = studycenter;
	}
	public String getCentercode() {
		return centercode;
	}
	public void setCentercode(String centercode) {
		this.centercode = centercode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
}
