package com.springboot.attendance;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtUserDetails implements UserDetails {

	private String userName;
	private String token;
	private Long id;
	private Collection<? extends GrantedAuthority> authorities;
	
	
	public JwtUserDetails(String userName, long id, String token, List<GrantedAuthority> grantedAuthorities)
	{
		this.userName= userName;
		this.id = id;
		this.token = token;
		this.authorities = grantedAuthorities;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}


	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
	public String getPassword() {

		return null;
	}
	
	public void setId(Long id)
	{
		this.id=id;
	}
	
	public Long getId()
	{
		return id;
	}
	
	public void setToken(String token)
	{
		this.token = token;
	}
	
	public String getToken()
	{
		return token;
	}
	
	public void setUserName(String name)
	{
		this.userName = name;
	}
	
	public String getUserName()
	{
		return userName;
	}
	


}
