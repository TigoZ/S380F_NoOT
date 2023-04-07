package hkmu.edu.hk.s380f.noot.controller;

import hkmu.edu.hk.s380f.noot.dao.UserManagementService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequestMapping("/user")
public class UserManagementController {
    @Resource
    UserManagementService umService;
    @GetMapping({"", "/", "/list"})
    public String list(ModelMap model) {
        model.addAttribute("blogUsers", umService.getBlogUsers());
        return "listUser";
    }
    public static class Form {
        private String username;
        private String password;
        private String[] roles;
        // getters and setters for all properties

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String[] getRoles() {
            return roles;
        }

        public void setRoles(String[] roles) {
            this.roles = roles;
        }
    }
    @GetMapping("/create")
    public ModelAndView create() {
        return new ModelAndView("addUser", "blogUser", new Form());
    }
    @PostMapping("/create")
    public String create(Form form) throws IOException {
        umService.createBlogUser(form.getUsername(),
                form.getPassword(), form.getRoles());
        return "redirect:/user/list";
    }
    @GetMapping("/delete/{username}")
    public String deleteBlog(@PathVariable("username") String username) {
        umService.delete(username);
        return "redirect:/user/list";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }
    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String confirmPassword,
                               @RequestParam String email,
                               @RequestParam String phone) {
        if (!password.equals(confirmPassword)) {
            return "redirect:/register?error";
        }

        // Add logic to create a new user with the provided information
        // and save it to the database

        return "redirect:/login";
    }


}