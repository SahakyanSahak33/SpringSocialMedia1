package com.socailmedia.socialmedia1.dto;

import com.socailmedia.socialmedia1.validator.Password;
import lombok.*;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NewPasswordDTO {
    @NotBlank
    @Password
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    String newPassword;
    String oldPassword;
}
