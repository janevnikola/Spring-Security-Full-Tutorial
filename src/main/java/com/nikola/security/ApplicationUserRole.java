package com.nikola.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.collect.Sets;

import static com.nikola.security.ApplicationUserPermission.*;
public enum ApplicationUserRole {//ENUM ROLES
	STUDENT(Sets.newHashSet()), //STUDENT role dodavame 0 permissii
	ADMIN(Sets.newHashSet(COURSE_READ,COURSE_WRITE,STUDENT_READ,STUDENT_WRITE)),//ADMIN role (ovie permisii se input od
	//import static com.nikola.security.ApplicationUserPermission.* klasata a ne se definirani pak vo ovaa klasa)
	ADMINTRAINEE(Sets.newHashSet(COURSE_READ,STUDENT_READ));
	//imame nov Role AdminTrainee
	
	
	
	//definiraj gi permisiite
	private final Set<ApplicationUserPermission> permissions;

	private ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
		this.permissions = permissions;
	}

	public Set<ApplicationUserPermission> getPermissions() {
		return permissions;
	}
	
	
	public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
		
	Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
		.map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
		.collect(Collectors.toSet());
	
	permissions.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
	
	return permissions;	
		
	}
	
}
