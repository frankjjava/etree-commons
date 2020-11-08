/**
 * Copyright � 2020 elasticTree Technologies Pvt. Ltd.
 *
 * @author  Franklin Abel
 * @version 1.0
 * @since   2020-11-04 
 */
package com.etree.commons.core.dto;

import java.sql.Timestamp;
import java.util.Collection;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class IdentityDto implements UserDetails {
	
	private static final long serialVersionUID = 8619783336283617776L;
	
	private String userId;
	private String username; // this field is the same as userId, but spring-security needs it for an 
							 //  implementation of UserDetails interface as this class
	private String title;
	private String userFullName;
	private String email;
	private String phoneNumbers;
	private String linkedInId;
	private String facebookId;
	private String twitterId;
	private String googleId;
	private Collection<SimpleGrantedAuthority> authorities = null;
	private boolean accountLocked;
	private boolean enabled;
	private Boolean changePassword;
	private String status;
	private Timestamp createdDate;
	private Timestamp updatedDate;
	
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}
}