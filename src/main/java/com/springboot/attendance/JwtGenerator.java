package com.springboot.attendance;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
@Scope("prototype")
public class JwtGenerator {
	Date date= new Date();
	 Date expTime = new Date(date.getTime() + 500000l); 
     
	public String generate(JwtUser jwtUser) {
		// TODO Auto-generated method stub
	
		System.out.println("Time is:");
		Date date= new Date();
		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(date));
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("IST"));
	    System.out.println(new SimpleDateFormat("HH:mm:ss").format(calendar.getTime()));
		Claims claims = Jwts.claims().setSubject(jwtUser.getUserName());
		claims.put("userId",String.valueOf(jwtUser.getId()));
		claims.put("role",jwtUser.getRole());
		
		
		return Jwts.builder()
			   .setClaims(claims)
			   .setExpiration(new Date(date.getTime() + 500000l))
		       .signWith(SignatureAlgorithm.HS512,"hitendra")
		       .compact();
		
		
	}

}
