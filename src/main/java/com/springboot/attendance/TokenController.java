package com.springboot.attendance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class TokenController {
	
    @Autowired
	UserDAO userDAO;
    
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
  
	private JwtGenerator jwtGenerator;
	public TokenController(JwtGenerator jwtGenerator)
	{
		this.jwtGenerator= jwtGenerator;
	}
	
	
	@PostMapping
	public String generate(@RequestBody final JwtUser jwtUser)
	{
        User tryloginuser= userDAO.findByUserId(jwtUser.getId());
		if(tryloginuser!= null)
		{
		  	
		  boolean isPasswordMatches= passwordEncoder.matches(jwtUser.getPassword(),tryloginuser.getPassword());
          if(isPasswordMatches)
          {
        	 
        	  String encryptPwd= passwordEncoder.encode(jwtUser.getPassword());
      		  jwtUser.setPassword(encryptPwd);
        	  userDAO.createUserLogged_In(jwtUser);
        	  
        	  return jwtGenerator.generate(jwtUser);
        	 
          }
          else
          {
        	  throw new RuntimeException("password Incorrect");
          }
		}
		else
		{
		     throw new RuntimeException("User id NOT FOUND");
		}
		
		
	}
}
 