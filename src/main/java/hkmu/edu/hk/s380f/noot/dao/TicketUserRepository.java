package hkmu.edu.hk.s380f.noot.dao;


import hkmu.edu.hk.s380f.noot.model.TicketUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketUserRepository extends JpaRepository<TicketUser, String> {
}