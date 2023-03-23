package com.student.AutomationPortal.config;

import com.student.AutomationPortal.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private String userName;
    private String password;
    private boolean isActive;
    private boolean isLocked;
    private List<GrantedAuthority> authorities;


    public UserDetailsImpl(User user){
        this.userName=user.getEmail();
        this.password= user.getPassword();
        this.isActive= user.isActive();
        if(user.getAttempts()>=3)
            this.isLocked=true;
        else
            this.isLocked=false;

        List<String> roles=new ArrayList<>();
        user.getRoles().stream().forEach(r->roles.add("ROLE_"+ r.getRole().toUpperCase().trim()));
        this.authorities=roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
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
        return !isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
