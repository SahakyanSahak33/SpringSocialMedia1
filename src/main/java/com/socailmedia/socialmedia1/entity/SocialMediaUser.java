package com.socailmedia.socialmedia1.entity;

import com.socailmedia.socialmedia1.validator.CheckEmail;
import com.socailmedia.socialmedia1.validator.Password;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Table(name = "social_media_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SocialMediaUser {
    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @NotBlank(message = "User's username cannot be empty.")
    @Size(min = 3, max = 20)
    @Column(name = "username")
    String username;
    @NotBlank(message = "This input cannot be empty.")
    @Size(min = 3, max = 20)
    @Column(name = "first_name")
    String firstName;
    @NotBlank(message = "This input cannot be empty.")
    @Size(min = 3, max = 20)
    @Column(name = "second_name")
    String lastName;
    @NotBlank
    @Pattern(regexp = "\\+\\d{3}-\\d{2}-\\d{3}-\\d{3}", message = "Please use pattern +XXX-XXX-XXX")
    @Column(name = "phone_number")
    String phoneNumber;
    @NotBlank
    @CheckEmail
    @Column(name = "email", unique = true)
    String email;
    @NotBlank
    @Password
    @Column(name = "password")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    String password;
    @Column(name = "gender")
    String gender;
    @Column(name = "date")
    @EqualsAndHashCode.Exclude
    String date;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id")
    )
    private Collection<Role> roles;
}
