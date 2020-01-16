package com.springboot.attendance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;


@Component
@Entity
@Table(name = "employee")
public class User {
	
	@Id
	@Column(name = "id")
	private long id;
	@Column(name = "userName")
	private String userName;
	@Column(name = "password")
	private String password;
	@Column(name = "role")
	private String role;
	
	
	public User() {
		
	}
	
	public User(long id, String userName, String password, String role)
	{
		this.id= id;
		this.userName= userName;
		this.password= password;
		this.role= role;
	}
	
	
	public void setId(long id)
	{
         this.id= id;
	}
	
	
	public long getId()
	{
		return this.id;
	}
	
	public void setUserName(String name)
	{
		this.userName= name;
	}

	public void setPassword(String pass)
	{
		this.password= pass;
	}
	
	public String getUserName()
	{
		return this.userName;
	}
	
	public String getPassword()
	{
		return this.password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
