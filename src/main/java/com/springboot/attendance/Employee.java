package com.springboot.attendance;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Id;

import org.springframework.lang.NonNull;


	
	public class Employee 
	{
		
	/**
	 *   1)  It is a POJO class containing attributes of an employee
	 *       having "id" as a unique Attribute. 
	 *     
	 *   2)  It is used to represent an employee.
	 * 	
	 *   3)  Containing Getter and Setter methods for employee object.
	 */
		
	private int id;                         // "id" is PRIMARY KEY for Employee Table.
	private String userName;
	private Date date;
	private Time entryTime;
	private Time exitTime;
	private String status;
		
		

	public Employee(int id, String userName, Date date, Time entryTime, Time exitTime, String status)
	{
		
	/**
	 *  Parameterized Constructor. 	
	 */
		this.setId(id);
		this.setUserName(userName);
		this.setDate(date);
		this.setEntryTime(entryTime);
		this.setExitTime(exitTime);
		this.setStatus(status);
	}   


	public Employee() 
	{
		//defaultConstructor
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date2) {
		this.date = date2;
	}


	public Time getEntryTime() {
		return entryTime;
	}


	public void setEntryTime(Time entryTime2) {
		this.entryTime = entryTime2;
	}


	public Time getExitTime() {
		return exitTime;
	}


	public void setExitTime(Time exitTime2) {
		this.exitTime = exitTime2;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}    

}
