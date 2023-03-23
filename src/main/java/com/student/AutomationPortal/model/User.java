package com.student.AutomationPortal.model;

import java.util.Set;

import javax.persistence.*;

import com.student.AutomationPortal.config.AttributeEncrypter;

import lombok.Data;


@Data
@Entity
@Table(name="user")
public class User {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
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

	@ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)//cascade= CascadeType.ALL, fetch=FetchType.LAZY
	@JoinTable(name="user_role", joinColumns = { 
		@JoinColumn(name="userId",referencedColumnName= "id")}, inverseJoinColumns = {
		@JoinColumn(name="roleId", referencedColumnName = "id")}
		,uniqueConstraints= @UniqueConstraint(columnNames = {"userId","roleId"})
		)
	private Set<Role> roles;

	@ManyToMany(cascade= CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name="user_project", joinColumns = { 
		@JoinColumn(name="userId",referencedColumnName= "id")}, inverseJoinColumns = {
		@JoinColumn(name="projectId", referencedColumnName = "id")},
		uniqueConstraints= @UniqueConstraint(columnNames = {"userId","projectId"}))
	private Set<Project> projects;
}
