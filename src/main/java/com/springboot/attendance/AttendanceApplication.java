package com.springboot.attendance;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




@SpringBootApplication
public class AttendanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AttendanceApplication.class, args);
		Timer timer = new Timer();
		TimerTask tt = new TimerTask(){
			public void run(){
				Calendar cal = Calendar.getInstance(); //this is the method you should use, not the Date(), because it is desperated.
 
				int hour = cal.get(Calendar.HOUR_OF_DAY);//get the hour number of the day, from 0 to 23
 
				if(hour==20)
				{
                    System.out.println("inside job schedule");
					new UserDAO().markDefaultAttendance();
				}
				
			}
		};
		timer.schedule(tt, 1000, 1000*21600);//	delay the task 1 second, and then run task every five seconds
	}

}
