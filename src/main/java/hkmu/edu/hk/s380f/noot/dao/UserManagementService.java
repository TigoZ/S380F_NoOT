package hkmu.edu.hk.s380f.noot.dao;

import hkmu.edu.hk.s380f.noot.model.BlogUser;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserManagementService {
    @Resource
    private BlogUserRepository buRepo;

    @Transactional
    public List<BlogUser> getBlogUsers() {
        return buRepo.findAll();
    }

    @Transactional
    public void delete(String username) {
        BlogUser blogUser = buRepo.findById(username).orElse(null);
        if (blogUser == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found.");
        }
        buRepo.delete(blogUser);
    }

    @Transactional
    public void createBlogUser(String username, String password, String[] roles) {
        BlogUser user = new BlogUser(username, password, roles);
        buRepo.save(user);
    }
}