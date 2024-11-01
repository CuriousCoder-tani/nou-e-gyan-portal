package com.lmsapp.lms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="adminlogin")
public class AdminLogin {
	
	@Id
	@Column (length =60 , nullable = false)
	private String adminid;
	@Column (length =60 , nullable = false)
	
	private String password;
	public String getAdminid() {
		return adminid;
	}
	public void setAdminid(String adminid) {
		this.adminid = adminid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
