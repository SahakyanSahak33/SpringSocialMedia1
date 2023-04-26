package com.socailmedia.socialmedia1.service;


import com.socailmedia.socialmedia1.entity.SocialMediaUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void saveUser(SocialMediaUser socialMediaUser);
    SocialMediaUser searchUserByUsername(String username);

    boolean checkUser (SocialMediaUser user);
    SocialMediaUser findById(Long id);

    SocialMediaUser findByEmailAddress(String email);
}