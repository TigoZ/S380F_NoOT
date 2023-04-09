package hkmu.edu.hk.s380f.noot.controller;


import hkmu.edu.hk.s380f.noot.dao.UserManagementService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


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
        private String email;
        private String phoneNumber;
        // getters and setters for all properties

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

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

    @GetMapping("/delete/{username}")
    public String deleteBlog(@PathVariable("username") String username) {
        umService.delete(username);
        return "redirect:/user/list";
    }



}