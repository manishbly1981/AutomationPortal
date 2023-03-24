package com.student.AutomationPortal.model;


import javax.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name="role"  , uniqueConstraints = @UniqueConstraint(name= "roleConst", columnNames = "role"))
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name= "role")
	private String role;
	
	/*@ManyToMany(cascade= CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinTable(name="user_role", joinColumns = { 
		@JoinColumn(name="roleId",referencedColumnName= "id")}, inverseJoinColumns = {
		@JoinColumn(name="userId", referencedColumnName = "id")},
		uniqueConstraints= @UniqueConstraint(columnNames = {"roleId", "userId"}))
	private Set<User> Users;
	*/
}

