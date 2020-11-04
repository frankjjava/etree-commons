package com.etree.commons.core.web.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

// EtreeGrantedAuthority was introduced as jackson serialization was failing due to absence of setter in SimpleGrantedAuthority.
public class EtreeGrantedAuthority implements GrantedAuthority {

	private static final long serialVersionUID = -59557620489200528L;
	private SimpleGrantedAuthority simpleGrantedAuthority;
	
	public void setAuthority(String role) {
		simpleGrantedAuthority = new SimpleGrantedAuthority(role);
	}	
	
	@Override
	public String getAuthority() {
		return simpleGrantedAuthority.getAuthority();
	}

	public boolean equals(Object obj) {
		return simpleGrantedAuthority.equals(obj);
	}

	public int hashCode() {
		return simpleGrantedAuthority.hashCode();
	}

	public String toString() {
		return simpleGrantedAuthority.toString();
	}	
}
