package hkmu.edu.hk.s380f.noot.dao;

import hkmu.edu.hk.s380f.noot.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {
    @Query("SELECT a FROM Attachment a WHERE a.blog.id IN (SELECT b.id FROM Blog b)")
    List<Attachment> findAttachmentsWithBlogIds();

}
