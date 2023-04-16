package com.student.APIAutomationUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.student.AutomationPortal.model.Project;
import com.student.AutomationPortal.model.Role;
import com.student.AutomationPortal.model.User;
import io.restassured.response.Response;
import java.util.HashSet;
import java.util.Set;

public class SampleRestToCheck {
    public static void main(String args[]) throws JsonProcessingException {
        RestUtil restUtil= new RestUtil();
        restUtil.setBaseURI("http://localhost:8080/api");
//        restUtil.setProxy("hybrid-web.global.blackspider.com",8081);
        ReqSpecificationBuilder reqSpecificationBuilder= new ReqSpecificationBuilder();
        reqSpecificationBuilder.setContentType("application/json");
        reqSpecificationBuilder.setAccept("application/json");
        User user= new User();
        user.setEmail("manish2@manish.com");
        user.setFirstName("manish");
        user.setLastName("gupta");
        user.setPassword("Niit@123");
        Set<Role> roles= new HashSet<>();
        Role role1=new Role();
        role1.setRole("ADMIN");
        roles.add(role1);
        Role role2=new Role();
        role1.setRole("USER");
        roles.add(role2);


        user.setRoles(roles);
        Set<Project> projects= new HashSet<>();
        Project project1= new Project();
        project1.setProjectCode("Test");
        project1.setProjectName("Testing");
        projects.add(project1);
        user.setProjects(projects);

        reqSpecificationBuilder.setBodyAsPojo(user);
        Response response= restUtil.getPostResponse(reqSpecificationBuilder, "/register");
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody().prettyPrint());
    }
}
