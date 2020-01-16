package com.springboot.attendance;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.springboot.attendance.Employee;
import com.springboot.attendance.User;




@Repository
@Component
public class UserDAO {
	
	
/**
 * 1)  This class is for making Queries to the employee database
 *     such as SELECT, DELETE and INSERT Queries.
 * 
 * 2)  Database name ===>> Employees.
 *     Table name ===>> Employee.
 *     User  ===>> root.
 */

	
	
	
@Autowired
JdbcTemplate jdbcTemplate;

List<Integer> idList = new ArrayList();


public void createUser(User user) {
	
/**
 *  1) Insert employee object to the database table "employee".
 *  
 *  2) "employee" table has 6 columns with "id" as PRIMARY KEY and
 *     "salary" attribute as NOT NULL. 
 */
	
	jdbcTemplate.update((Connection connection) ->{
		PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO EMPLOYEE(id,userName,password,role) VALUES(?,?,?,?)");
	    preparedStatement.setLong(1, user.getId());
	    preparedStatement.setString(2, user.getUserName());
	    preparedStatement.setString(3, user.getPassword());
	    preparedStatement.setString(4, user.getRole());
	    return preparedStatement;
	});
	
}




public void createUserLogged_In(JwtUser user) {
	
/**
 *  1) Insert employee object to the database table "employee".
 *  
 *  2) "employee" table has 6 columns with "id" as PRIMARY KEY and
 *     "salary" attribute as NOT NULL. 
 */
	
	jdbcTemplate.update((Connection connection) ->{
		PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Logged_In (id, userName, password, role) SELECT * FROM (SELECT ?,?,?,?) AS tmp WHERE NOT EXISTS (SELECT id FROM Logged_In WHERE id = ?) LIMIT 1;");
	    preparedStatement.setLong(1, user.getId());
	    preparedStatement.setString(2, user.getUserName());
	    preparedStatement.setString(3, user.getPassword());
	    preparedStatement.setString(4, user.getRole());
	    preparedStatement.setLong(5, user.getId());
	    return preparedStatement;
	});
	
}





public User findByUserId(Long id) {

    String sql = "SELECT * FROM EMPLOYEE WHERE ID = ?";

    try {
    return jdbcTemplate.queryForObject(
		sql, 
		new Object[]{id}, 
		new BeanPropertyRowMapper<User>(User.class));
    }
    catch(Exception e)
    {
    	return null;
    }

}




public User findByUserIdInLogged_IN(Long id) {

    String sql = "SELECT * FROM Logged_In WHERE ID = ?";

        try {
         return jdbcTemplate.queryForObject(
		                sql, 
		                new Object[]{id}, 
		                new BeanPropertyRowMapper<User>(User.class));
        }
        catch(Exception e)
        {
        	return null;
        }
    
}




public void deleteUserFromLogged_In(int pid) {
	
/**
 *  This method is used to delete row from employee 
 *  table having "id" as PRIMARY KEY.
 *  	
 */
	
	jdbcTemplate.update((Connection connection) ->{
		PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Logged_In WHERE id=?");
	    preparedStatement.setInt(1, pid);
	    return preparedStatement;
	});
	
}






public void markAttendance(User user) {
	Calendar calendar = Calendar.getInstance();
	calendar.setTimeZone(TimeZone.getTimeZone("IST"));
	Date date1= Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));


	jdbcTemplate.update((Connection connection) ->{
		PreparedStatement preparedStatement = connection.prepareStatement("UPDATE EmployeeAttendance SET status=?, marked=?, entryTime=? WHERE DATE(date)=? and id =?");
	    preparedStatement.setString(1, "P");
	    preparedStatement.setString(2, "Y");
        preparedStatement.setTime(3, Time.valueOf(new SimpleDateFormat("HH:mm:ss").format(calendar.getTime())));
	    preparedStatement.setDate(4, date1);
	    preparedStatement.setLong(5,user.getId());
	    
	    return preparedStatement;
	});
}




public void mark_Exit_Attendance(User user) {
	Calendar calendar = Calendar.getInstance();
	calendar.setTimeZone(TimeZone.getTimeZone("IST"));
	Date date1= Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));

	jdbcTemplate.update((Connection connection) ->{
		PreparedStatement preparedStatement = connection.prepareStatement("UPDATE EmployeeAttendance SET exitTime =? WHERE Date(date) = ? and id = ? and marked=?");
	    preparedStatement.setTime(1, Time.valueOf(new SimpleDateFormat("HH:mm:ss").format(calendar.getTime())));
	    preparedStatement.setDate(2, date1);
	    preparedStatement.setLong(3, user.getId());
	    preparedStatement.setString(4, "Y");

	    return preparedStatement;
	});
	
}



public ArrayList<Employee> ShowAttendance(int id)
{

	ArrayList<Employee> employeelist = new ArrayList<>();
	String sql = "SELECT id, userName,entryTime,exitTime,date,status FROM EmployeeAttendance WHERE id="+id;
	
	Collection<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
	System.out.println("inside dao");
	rows.stream().map((row) ->{
		Employee emp = new Employee();
		emp.setId((int) row.get("id"));
		emp.setUserName((String) row.get("userName"));
		emp.setDate(new Date(((Timestamp)row.get("date")).getTime()));
		emp.setEntryTime((Time) row.get("entryTime"));
		emp.setExitTime((Time) row.get("exitTime"));
		emp.setStatus((String) row.get("status"));
		return emp;
		
	}).forEach((user1) -> {
		employeelist.add(user1);
	});
	System.out.println("hello"+employeelist.get(0));
	return employeelist;
}




public void markDefaultAttendance() {

	Calendar calendar = Calendar.getInstance();
	calendar.setTimeZone(TimeZone.getTimeZone("IST"));
	//Date date1= Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
	jdbcTemplate.update((Connection connection) ->{
		PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO EmployeeAttendance (id) SELECT id from employee;");
	    return preparedStatement;
	});
		

	
}




public ArrayList<Employee> ShowActivity() {

	ArrayList<Employee> employeelist = new ArrayList<>();
	String sql = "SELECT id,userName,date,status FROM EmployeeAttendance WHERE marked='N'";
	
	Collection<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
	System.out.println("inside dao");
	rows.stream().map((row) ->{
		Employee emp = new Employee();
		emp.setId((int) row.get("id"));
		emp.setUserName((String) row.get("userName"));
		emp.setDate(new Date(((Timestamp)row.get("date")).getTime()));
		emp.setEntryTime((Time) row.get("entryTime"));
		emp.setExitTime((Time) row.get("exitTime"));
		emp.setStatus((String) row.get("status"));
		return emp;
		
	}).forEach((user1) -> {
		employeelist.add(user1);
	});
	System.out.println("hello"+employeelist.get(0));
	return employeelist;
}




public void AdminMarkAbsent(User user) {
	// TODO Auto-generated method stub
	
	Calendar calendar = Calendar.getInstance();
	calendar.setTimeZone(TimeZone.getTimeZone("IST"));
	Date date1= Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));

	jdbcTemplate.update((Connection connection) ->{
		PreparedStatement preparedStatement = connection.prepareStatement("UPDATE EmployeeAttendance SET marked =? WHERE Date(date) = ? and id = ?");
	    preparedStatement.setString(1, "Y");
	    preparedStatement.setDate(2, date1);
	    preparedStatement.setLong(3, user.getId());


	    return preparedStatement;
	});
	
	
}





}
