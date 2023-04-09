package hkmu.edu.hk.s380f.noot.dao;


import hkmu.edu.hk.s380f.noot.model.BlogUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogUserRepository extends JpaRepository<BlogUser, String> {
    BlogUser findByUsername(String username);
}