package hkmu.edu.hk.s380f.noot.controller;

import hkmu.edu.hk.s380f.noot.dao.UserManagementService;
import hkmu.edu.hk.s380f.noot.model.BlogUser;
import hkmu.edu.hk.s380f.noot.model.UserRole;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
import java.util.List;


@Controller
public class RegisterController {

    @Resource
    UserManagementService umService;




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




}
