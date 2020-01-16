package com.springboot.attendance;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.springboot.attendance.UserDAO;
import com.springboot.attendance.Employee;
import com.springboot.attendance.User;





@RestController
public class LoginController {
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@RequestMapping(value = "/entry", method = { RequestMethod.POST})
	public String auth(@RequestBody User user) throws Exception
	{
		try {
		userDAO.markAttendance(user);
		}
		catch(Exception e)
		{
			throw new Exception("Entry is marked twice");
		}
		return "entry attendance is marked";
	}
	
	
	
	@RequestMapping(value = "/exit", method = { RequestMethod.POST})
	public String exitAttendance(@RequestBody User user)
	{
		userDAO.mark_Exit_Attendance(user);
		return "entry attendance is marked";
	}
	
	
	@RequestMapping(value = "/ShowActivity", method = { RequestMethod.POST})
	public ArrayList<Employee> Activity()
	{
		
		return userDAO.ShowActivity();
	}
	
	
	@RequestMapping(value = "/AdminMarkPresent", method = { RequestMethod.POST})
	public ArrayList<Employee> AdminMarkPresent(@RequestBody User user)
	{
		userDAO.markAttendance(user);
		return userDAO.ShowActivity();
	}
	
	@RequestMapping(value = "/AdminMarkAbsent", method = { RequestMethod.POST})
	public ArrayList<Employee> AdminMarkAbsent(@RequestBody User user)
	{
		userDAO.AdminMarkAbsent(user);
		return userDAO.ShowActivity();
	}
	
	
	@PostMapping("/add")
	public String addUserByAdmin(@RequestBody User user) {
		String pwd= user.getPassword();
		String encryptPwd= passwordEncoder.encode(pwd);
		user.setPassword(encryptPwd);
		userDAO.createUser(user);
		return "user added successfully.....";
	}
	
	@PostMapping("/logged_in")
	public Boolean checkLogin(@RequestBody User user) {

		User user1= userDAO.findByUserIdInLogged_IN(user.getId());
		System.out.println("hello check login"+user.getId());
		if(user1!=null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	

	@PostMapping("/logout_user")
	public String deleteuser(@RequestBody User user)
	{
		System.out.println(user.getId());
		int id = (int) user.getId();
		userDAO.deleteUserFromLogged_In(id);
		return "User is deleted from logged_In";

	}

	
	@PostMapping("/ShowAttendance")
	public ArrayList<Employee> ShowAttendance(@RequestBody User user)
	{
		
		int id = (int) user.getId();
		return(userDAO.ShowAttendance(id));
		
		

	}

    
    
	@PostMapping("/hello")
	public String exhello()
	{
		return "hello";
	}
	
}
