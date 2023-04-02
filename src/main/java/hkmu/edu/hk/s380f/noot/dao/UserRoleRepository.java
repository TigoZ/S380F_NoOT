package hkmu.edu.hk.s380f.noot.dao;

import hkmu.edu.hk.s380f.noot.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
}