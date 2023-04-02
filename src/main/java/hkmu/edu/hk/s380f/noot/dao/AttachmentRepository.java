package hkmu.edu.hk.s380f.noot.dao;

import hkmu.edu.hk.s380f.noot.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {
}
