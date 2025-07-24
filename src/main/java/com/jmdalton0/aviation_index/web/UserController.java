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

/**
 * A controller for managing users.
 */
@Controller
@RequestMapping("/users")
public class UserController {
    
    /**
     * A UserService is used to manage users.
     */
    private final UserService userService;

    /**
     * A parameterized constructor.
     * @param userService
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * An endpoint to view the profile page of a user.
     * @param model view model.
     * @return user profile view.
     */
    @GetMapping
    public String viewProfile(Model model) {
        Long userId = SecurityUtil.getAuthenticatedUserId();
        User user = userService.findById(userId);
        model.addAttribute("title", "Profile");
        model.addAttribute("user", user);
        return "users/view";
    }

    /**
     * An endpoint to view a form for editing a new user.
     * @param model view model.
     * @return new user form view.
     */
    @GetMapping("/edit")
    public String viewEditProfile(Model model) {
        return "users/edit";
    }

    /**
     * An endoint to edit a user.
     * @param username the updated user's username.
     * @return redirect to the user profile view.
     */
    @PostMapping("/edit")
    public String editProfile(@RequestParam String username) {
        Long userId = SecurityUtil.getAuthenticatedUserId();
        User user = userService.findById(userId);
        user.setUsername(username);
        userService.save(user);
        return "redirect:/users";
    }

}
