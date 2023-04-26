package com.socailmedia.socialmedia1.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserLogInDTO {
    String username;
    String password;
}