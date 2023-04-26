package com.socailmedia.socialmedia1.controller;


import com.socailmedia.socialmedia1.dto.NewPasswordDTO;
import com.socailmedia.socialmedia1.dto.UserLogInDTO;
import com.socailmedia.socialmedia1.entity.Role;
import com.socailmedia.socialmedia1.entity.SocialMediaUser;
import com.socailmedia.socialmedia1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class SpringController {

    private final UserService userService;

    @GetMapping(value = {"/", "/home", "/start"})
    public String home() {
        return "index";
    }

    @RequestMapping("/currentUser")
    public String currentUser(@ModelAttribute("id") Long id, Model model) {
        SocialMediaUser user = userService.findById(id);
        model.addAttribute("firstName", user.getUsername());
        model.addAttribute("isAuthenticated", true);
        model.addAttribute("emailAddress", user.getEmail());
        model.addAttribute("id", user.getId());
        return "index";
    }

    /*@GetMapping("/index")
    public String homeLoggedIn(@RequestParam("username") String username, Model model) {
        SocialMediaUser user = userService.searchUserByUsername(username);
        model.addAttribute("userLoggedIn", user);
        return "index";
    }*/

    @GetMapping("/registration")
    public String registration(Model model) {
        SocialMediaUser user = new SocialMediaUser();
        model.addAttribute("user", user);
        return "registration";
    }
    @GetMapping("/registration-error")
    public String registrationError(Model model) {
        SocialMediaUser user = new SocialMediaUser();
        model.addAttribute("user", user);
        model.addAttribute("registrationError", true);
        return "registration";
    }

    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public String saveUser(@Valid @ModelAttribute("user") SocialMediaUser user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        } else if (userService.checkUser(user)){
            Date dt = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = sdf.format(dt);
            user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            user.setDate(currentTime);
            user.setRoles(List.of(new Role("ROLE_USER")));
            userService.saveUser(user);
            System.out.println(user);
            return "redirect:/";
        }
        return "redirect:/registration-error";
    }

    @GetMapping("/login")
    public String login(Model model) {
        UserLogInDTO userLogInDTO = new UserLogInDTO();
        model.addAttribute("userLogIn", userLogInDTO);
        return "login";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        System.out.println("----------------------| /login-error.html |------------------------");
        model.addAttribute("loginError", true);
        UserLogInDTO userLogInDTO = new UserLogInDTO();
        model.addAttribute("userLogIn", userLogInDTO);
        return "login";
    }

    @RequestMapping(value = "/asking")
    public String asking(@ModelAttribute("userLogIn") UserLogInDTO userLogInDTO, Model model) {
        SocialMediaUser user =userService.searchUserByUsername(userLogInDTO.getUsername());
        if (user==null) {
            return "redirect:/login-error";
        }
        String password = userLogInDTO.getPassword();
        if(BCrypt.checkpw(password, user.getPassword())) {
            String username =userLogInDTO.getUsername();
            user = userService.searchUserByUsername(username);
            model.addAttribute("id", user.getId());
            return "redirect:/currentUser";
        }
        return "redirect:/login-error";
    }

    @RequestMapping(value = "/settings")
    public String settings(@RequestParam("id") Long id, Model model) {
        SocialMediaUser user =  userService.findById(id);
        NewPasswordDTO passwordDTO = new NewPasswordDTO();
        model.addAttribute("userForChanges",user);
        model.addAttribute("passwordDTO", passwordDTO);
        return "settings";
    }

    @RequestMapping(value = "/changes")
    public String changes(@Valid @ModelAttribute("userForChanges") SocialMediaUser user, @Valid @ModelAttribute("passwordDTO") NewPasswordDTO passwordDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "settings";
        } else if (BCrypt.checkpw(passwordDTO.getOldPassword(), user.getPassword())) {
            user.setPassword(BCrypt.hashpw(passwordDTO.getNewPassword(), BCrypt.gensalt()));
            userService.saveUser(user);
            return "redirect:/currentUser";
        }
        model.addAttribute("isNotCorrect", true);
        return "redirect:/settings";
    }


}
