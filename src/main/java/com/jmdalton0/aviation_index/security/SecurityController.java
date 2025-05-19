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

@Controller
@RequestMapping
public class SecurityController {

    private final SecurityUserDetailsService securityUserDetailsService;

    SecurityController(SecurityUserDetailsService securityUserDetailsService) {
        this.securityUserDetailsService = securityUserDetailsService;
    }

    @GetMapping("/login")
    public String viewLogin(
        @RequestParam(value = "error", required = false) String error,
        Model model
    ) {
        model.addAttribute("title", "Login");
        if (error != null) {
            model.addAttribute("error", "Login Failed");
        }
        return "login";
    }

    @GetMapping("/register")
    public String viewRegister(Model model) {
        model.addAttribute("title", "Register");
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        try {
            securityUserDetailsService.register(user);
        } catch (UsernameAlreadyExistsException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
        return "redirect:/login";
    }

}
