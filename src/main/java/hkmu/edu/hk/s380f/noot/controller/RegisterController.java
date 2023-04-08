package hkmu.edu.hk.s380f.noot.controller;

import hkmu.edu.hk.s380f.noot.dao.UserManagementService;
import hkmu.edu.hk.s380f.noot.model.BlogUser;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

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
        umService.saveUser(user);

        // 注册成功后，将用户重定向到登录页面。
        return "redirect:/login";
    }

}
