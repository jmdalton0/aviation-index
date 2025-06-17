package com.jmdalton0.aviation_index.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jmdalton0.aviation_index.models.User;
import com.jmdalton0.aviation_index.security.SecurityUtil;
import com.jmdalton0.aviation_index.services.UserService;

@Controller
@RequestMapping("/users")
public class UserController {
    
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String viewProfile(Model model) {
        Long userId = SecurityUtil.getAuthenticatedUserId();
        User user = userService.findById(userId);
        model.addAttribute("title", "Profile");
        model.addAttribute("user", user);
        return "users/view";
    }

    @GetMapping("/edit")
    public String viewEditProfile(Model model) {
        return "users/edit";
    }

    @PostMapping("/edit")
    public String editProfile(@RequestParam String username) {
        Long userId = SecurityUtil.getAuthenticatedUserId();
        User user = userService.findById(userId);
        user.setUsername(username);
        userService.save(user);
        return "redirect:/users";
    }

}
