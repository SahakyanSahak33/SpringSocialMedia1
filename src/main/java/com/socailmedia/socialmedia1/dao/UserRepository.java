package com.socailmedia.socialmedia1.dao;

import com.socailmedia.socialmedia1.entity.SocialMediaUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<SocialMediaUser, Long> {
    SocialMediaUser getUserByUsername (String username);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);

    SocialMediaUser findByEmail (String email);

    Optional<SocialMediaUser> findById(Long id);
}
