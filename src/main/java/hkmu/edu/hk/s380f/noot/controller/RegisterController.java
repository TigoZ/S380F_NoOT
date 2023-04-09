package hkmu.edu.hk.s380f.noot.controller;

import hkmu.edu.hk.s380f.noot.dao.BlogUserRepository;
import hkmu.edu.hk.s380f.noot.dao.UserManagementService;
import hkmu.edu.hk.s380f.noot.model.BlogUser;
import hkmu.edu.hk.s380f.noot.model.ResetPasswordRequest;
import hkmu.edu.hk.s380f.noot.model.UserRole;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RegisterController {

    @Resource
    UserManagementService umService;

    @Autowired
    private BlogUserRepository blogUserRepository;

    @GetMapping("/register")
    public ModelAndView showRegisterForm() {
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("user", new BlogUser());
        return modelAndView;
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute BlogUser user) {
        UserRole defaultRole = new UserRole();
        defaultRole.setUser(user);
        defaultRole.setRole("ROLE_USER");

        List<UserRole> userRoles = new ArrayList<>();
        userRoles.add(defaultRole);
        user.setRoles(userRoles);

        // Add the {noop} prefix before the password
        user.setPassword("{noop}" + user.getPassword());

        umService.saveUser(user);

        return "redirect:/login";
    }

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm() {
        return "forgot_password";
    }


    @PostMapping("/checkUsernameAndResetPassword")
    @ResponseBody
    public Map<String, Object> checkUsernameAndResetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        Map<String, Object> result = new HashMap<>();
        BlogUser user = blogUserRepository.findByUsername(resetPasswordRequest.getUsername());
        if (user != null) {
            user.setPassword("{noop}" + resetPasswordRequest.getNewPassword());
            blogUserRepository.save(user);
            result.put("success", true);
        } else {
            result.put("success", false);
        }

        return result;
    }

}
