package com.springboot.attendance;

public class JwtUser {
	private String userName;
	private long id;
	private String role;
    private String password;
    
    
	public void setUserName(String subject) {
	this.userName= subject;
		
	}

	public void setId(long id) {
		this.id= id;
		
	}

	public void setRole(String role) {
		this.role= role;
	}
	
	public String getUserName()
	{
		return userName;
	}

	public long getId()
	{
		return id;
	}
	
	
	public String getRole()
	{
		return role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
