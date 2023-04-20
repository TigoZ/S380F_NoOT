package hkmu.edu.hk.s380f.noot.controller;

import hkmu.edu.hk.s380f.noot.dao.UserManagementService;
import hkmu.edu.hk.s380f.noot.model.BlogUser;
import hkmu.edu.hk.s380f.noot.model.UserRole;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import hkmu.edu.hk.s380f.noot.dao.BlogUserRepository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;



@Controller
@RequestMapping("/user")
public class UserManagementController {
    @Resource
    UserManagementService umService;

    @Resource
    BlogUserRepository blogUserRepository;

    @GetMapping({"", "/", "/list"})
    public String list(ModelMap model) {
        model.addAttribute("blogUsers", umService.getBlogUsers());
        return "listUser";
    }

    public static class Form {
        private String username;
        private String password;
        private List<String> roles;
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

        public List<String> getRoles() {
            return roles;
        }

        public void setRoles(List<String> roles) {
            this.roles = roles;
        }
    }

    @GetMapping("/delete/{username}")
    public String deleteBlog(@PathVariable("username") String username) {
        umService.delete(username);
        return "redirect:/user/list";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/addAdmin")
    public String showAddAdminForm(Model model) {
        BlogUser blogUser = new BlogUser();
        model.addAttribute("blogUser", blogUser);
        return "addAdmin";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addAdmin")
    public String createAdmin(@ModelAttribute("blogUser") BlogUser blogUser) {
        UserRole adminRole = new UserRole(blogUser, "ROLE_ADMIN");
        List<UserRole> userRoles = new ArrayList<>();
        userRoles.add(adminRole);
        blogUser.setRoles(userRoles);

        blogUser.setPassword("{noop}" + blogUser.getPassword());

        blogUserRepository.save(blogUser);
        return "redirect:/user/list";
    }

    @GetMapping("/edit/{username}")
    public String showEditUserForm(@PathVariable("username") String username, Model model) {
        Optional<BlogUser> blogUserOptional = blogUserRepository.findById(username);
        if (blogUserOptional.isPresent()) {
            BlogUser blogUser = blogUserOptional.get();
            Form form = new Form();
            form.setUsername(blogUser.getUsername());
            form.setPassword(blogUser.getPassword());
            form.setEmail(blogUser.getEmail());
            form.setPhoneNumber(blogUser.getPhoneNumber());

            List<String> roles = new ArrayList<>();
            for (UserRole role : blogUser.getRoles()) {
                roles.add(role.getRole());
            }
            form.setRoles(roles);

            model.addAttribute("form", form);
            return "editUser";
        } else {
            return "redirect:/user/list";
        }
    }



    @PostMapping("/update/{username}")
    public String updateUser(@PathVariable("username") String username, @ModelAttribute("form") Form form, Model model) {
        Optional<BlogUser> blogUserOptional = blogUserRepository.findById(username);
        if (blogUserOptional.isPresent()) {
            BlogUser existingBlogUser = blogUserOptional.get();

            // Update the properties
            existingBlogUser.setUsername(form.getUsername());
            existingBlogUser.setPassword("{noop}" + form.getPassword());
            existingBlogUser.setEmail(form.getEmail());
            existingBlogUser.setPhoneNumber(form.getPhoneNumber());

            // Convert roles from List<String> to List<UserRole>
            List<UserRole> userRoles = new ArrayList<>();
            for (String role : form.getRoles()) {
                userRoles.add(new UserRole(existingBlogUser, role));
            }
            existingBlogUser.setRoles(userRoles);

            // Save the updated user
            blogUserRepository.save(existingBlogUser);

            return "redirect:/user/list";
        } else {
            model.addAttribute("error", "User '" + username + "' not found.");
            model.addAttribute("form", form);
            return "editUser";
        }
    }

}