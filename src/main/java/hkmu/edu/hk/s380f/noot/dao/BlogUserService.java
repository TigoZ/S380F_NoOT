package hkmu.edu.hk.s380f.noot.dao;

import hkmu.edu.hk.s380f.noot.model.BlogUser;
import hkmu.edu.hk.s380f.noot.model.UserRole;
import jakarta.annotation.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogUserService implements UserDetailsService {
    @Resource
    BlogUserRepository blogUserRepo;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        BlogUser blogUser = blogUserRepo.findById(username).orElse(null);
        if (blogUser == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found.");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (UserRole role : blogUser.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return new User(blogUser.getUsername(), blogUser.getPassword(), authorities);
    }

    public boolean isAdmin(String username) {
        BlogUser blogUser = findByUsername(username);
        if (blogUser != null) {
            for (UserRole role : blogUser.getRoles()) {
                if ("ROLE_ADMIN".equals(role.getRole())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Transactional(readOnly = true)
    public BlogUser findByUsername(String username) {
        return blogUserRepo.findById(username).orElse(null);
    }
}
