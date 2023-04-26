package com.socailmedia.socialmedia1.service;

import com.socailmedia.socialmedia1.dao.UserRepository;
import com.socailmedia.socialmedia1.entity.Role;
import com.socailmedia.socialmedia1.entity.SocialMediaUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void saveUser(SocialMediaUser socialMediaUser) {
        userRepository.save(socialMediaUser);
    }

    @Override
    @Transactional
    public SocialMediaUser searchUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    @Override
    @Transactional
    public boolean checkUser(SocialMediaUser user) {
        return !(userRepository.existsByEmail(user.getEmail()) || userRepository.existsByPhoneNumber(user.getPhoneNumber()));
    }

    @Override
    public SocialMediaUser findById(Long id) {
        Optional<SocialMediaUser> optional = userRepository.findById(id);
        return optional.orElseGet(null);
    }

    @Override
    public SocialMediaUser findByEmailAddress(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SocialMediaUser user = userRepository.getUserByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }


    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}