package com.jmdalton0.aviation_index.security;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.jmdalton0.aviation_index.models.User;
import com.jmdalton0.aviation_index.security.exceptions.UsernameAlreadyExistsException;

/**
 * A controller for managing user security sessions.
 * This controller also handles creation of new users.
 */
@Controller
@RequestMapping
public class SecurityController {

    /**
     * A SecurityUserDetailsService is used for managing user security sessions and new users.
     */
    private final SecurityUserDetailsService securityUserDetailsService;

    /**
     * A parameterized constructor.
     * @param securityUserDetailsService
     */
    SecurityController(SecurityUserDetailsService securityUserDetailsService) {
        this.securityUserDetailsService = securityUserDetailsService;
    }

    /**
     * An endpoint to view the login page.
     * @param error an error message if login fails.
     * @param model view model.
     * @return login view.
     */
    @GetMapping("/login")
    public String viewLogin(
        @RequestParam(required = false) String error,
        Model model
    ) {
        model.addAttribute("title", "Login");
        if (error != null) {
            model.addAttribute("error", "Login Failed");
        }
        return "security/login";
    }

    /**
     * An endpoint to view the register page.
     * @param model view model.
     * @return register view.
     */
    @GetMapping("/register")
    public String viewRegister(Model model) {
        model.addAttribute("title", "Register");
        return "security/register";
    }

    /**
     * An endpoint to register a new user.
     * @param user the new user data.
     * @param model view model.
     * @return redirect to the login view.
     */
    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        try {
            securityUserDetailsService.register(user);
        } catch (UsernameAlreadyExistsException e) {
            model.addAttribute("title", "Register");
            model.addAttribute("error", e.getMessage());
            return "security/register";
        }
        return "redirect:/login";
    }

    /**
     * An endpoint to view the edit password form page.
     * @param error an error message if the update fails.
     * @param model view model.
     * @return the edit password form view.
     */
    @GetMapping("/password")
    public String viewEditPassword(
        @RequestParam(required = false) String error,
        Model model
    ) {
        model.addAttribute("title", "Update Password");
        if (error != null) {
            model.addAttribute("error", error);
        }
        return "security/password";
    }

    /**
     * An endoint to update a password.
     * @param curPassword the current password data.
     * @param newPassword the new password data.
     * @param model view model.
     * @return redirect to the user profile view if successful, else the edit password form view.
     */
    @PostMapping("/password")
    public String editPassword(
        @RequestParam(name = "cur-password") String curPassword,
        @RequestParam(name = "new-password") String newPassword,
        Model model
    ) {
        if (!securityUserDetailsService.verifyPassword(curPassword)) {
            model.addAttribute("title", "Update Password");
            model.addAttribute("error", "Current Password Incorrect");
            return "security/password";
        }

        securityUserDetailsService.updatePassword(newPassword);
        
        return "redirect:/users";
    }
}
