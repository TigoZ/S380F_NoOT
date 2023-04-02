package hkmu.edu.hk.s380f.noot.dao;

import hkmu.edu.hk.s380f.noot.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
