package com.student.AutomationPortal.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.student.AutomationPortal.config.AttributeEncrypter;

import lombok.Data;

@Data
@Entity
@Table(name="user")
public class User {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="email", nullable= false, unique= true)
	private String email;
	
	@Column(name="firstName", nullable= false)
	private String firstName;
	
	@Column(name="lastName", nullable= false)
	private String lastName;
	
	@Convert(converter = AttributeEncrypter.class)
	@Column(name="password", nullable= false)
	private String password;
	
	@Column(name= "isActive", nullable= false, columnDefinition= "boolean Default false" )
	private boolean isActive;
	
	@Column(name= "attempts", nullable= false, columnDefinition= "integer Default 0")
	private int attempts;
	
	@Convert(converter = AttributeEncrypter.class)
	@Column(name= "confirmationCode")
	private String confirmationCode;

	@ManyToMany(cascade= CascadeType.ALL)
	@JoinTable(name="user_role", 
			joinColumns = @JoinColumn(name="userId",referencedColumnName= "id"),//
			inverseJoinColumns = @JoinColumn(name="roleId", referencedColumnName = "id")) //
	private Set<Role> roles;
	
}
