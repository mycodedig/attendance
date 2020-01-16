package com.springboot.attendance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;



@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration


public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private JwtAuthenticationProvider authenticationProvider;
	@Autowired
	private JwtAuthenticationEntryPoint entryPoint;
	
	
	@Bean
	public BCryptPasswordEncoder encodePWD()
	{
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception 
	 {
      http.csrf().disable()
      .authorizeRequests().antMatchers("**/entry").authenticated()
      .and()
      .authorizeRequests().antMatchers("**/exit").authenticated()
      .and()
      .exceptionHandling().authenticationEntryPoint(entryPoint)
      .and()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
      http.addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);  
      http.headers().cacheControl();
      http.cors();
      } 

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
	    final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH"));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
	}
	
	
	@Bean
	public AuthenticationManager authenticationManager()
	{
		return new ProviderManager(Collections.singletonList(authenticationProvider));
	}
	
	
   @Bean
   public JwtAuthenticationTokenFilter authenticationTokenFilter() {
	   
	   
	   JwtAuthenticationTokenFilter filter= new JwtAuthenticationTokenFilter();
	   filter.setAuthenticationManager(authenticationManager());
	   filter.setAuthenticationSuccessHandler(new JwtSuccessHandler());
	   return filter;
   }
   

	
	
}