package com.etree.commons.core.web.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.etree.commons.core.exception.EtreeCommonsException;

public class UserDetailsDto extends UserAdditionalInfoDto implements UserDetails, Cloneable {

	private static final long serialVersionUID = -6029489920983711889L;
	
	private String username;
	private String userFullName;
	private Collection<EtreeGrantedAuthority> authorities = null;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;
	
	public UserDetailsDto() {
	}
	
	public UserDetailsDto(String username, String password, List<String> roles) {
		this.username = username;
		setPassword(password);
		if (roles == null || roles.isEmpty()) {
			return;
		}
		authorities = new ArrayList<>();
		for (String role : roles) {
			EtreeGrantedAuthority grantedAuthority = new EtreeGrantedAuthority();
			grantedAuthority.setAuthority(role);
			authorities.add(grantedAuthority);
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public Collection<EtreeGrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<EtreeGrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public UserDetailsDto clone() {
		UserDetailsDto userDetailsDto = null;
		try {
			userDetailsDto = (UserDetailsDto) super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new EtreeCommonsException(ex);

		}
		return userDetailsDto;
	}
}
