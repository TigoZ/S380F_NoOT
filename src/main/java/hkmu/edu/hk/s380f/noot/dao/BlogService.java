package hkmu.edu.hk.s380f.noot.dao;


import hkmu.edu.hk.s380f.noot.exception.AttachmentNotFound;
import hkmu.edu.hk.s380f.noot.exception.BlogNotFound;
import hkmu.edu.hk.s380f.noot.model.Attachment;
import hkmu.edu.hk.s380f.noot.model.Blog;
import hkmu.edu.hk.s380f.noot.model.BlogUser;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class BlogService {
    @Resource
    private BlogRepository bRepo;

    @Resource
    private BlogUserRepository buRepo;

    @Resource
    private AttachmentRepository aRepo;

    @Transactional
    public List<Blog> getBlogs() {
        return bRepo.findAll();
    }

    @Transactional
    public Blog getBlog(long id)
            throws BlogNotFound {
        Blog blog = bRepo.findById(id).orElse(null);
        if (blog == null) {
            throw new BlogNotFound(id);
        }
        return blog;
    }

    @Transactional
    public Attachment getAttachment(long blogId, UUID attachmentId)
            throws BlogNotFound, AttachmentNotFound {
        Blog blog = bRepo.findById(blogId).orElse(null);
        if (blog == null) {
            throw new BlogNotFound(blogId);
        }
        Attachment attachment = aRepo.findById(attachmentId).orElse(null);
        if (attachment == null) {
            throw new AttachmentNotFound(attachmentId);
        }
        return attachment;
    }

    @Transactional(rollbackFor = BlogNotFound.class)
    public void delete(long id) throws BlogNotFound {
        Blog deletedBlog = bRepo.findById(id).orElse(null);
        if (deletedBlog == null) {
            throw new BlogNotFound(id);
        }
        deletedBlog.getBloguser().getBlogs().remove(deletedBlog);
        bRepo.delete(deletedBlog);
    }

    @Transactional(rollbackFor = AttachmentNotFound.class)
    public void deleteAttachment(long blogId, UUID attachmentId)
            throws BlogNotFound, AttachmentNotFound {
        Blog blog = bRepo.findById(blogId).orElse(null);
        if (blog == null) {
            throw new BlogNotFound(blogId);
        }
        for (Attachment attachment : blog.getAttachments()) {
            if (attachment.getId().equals(attachmentId)) {
                blog.deleteAttachment(attachment);
                bRepo.save(blog);
                return;
            }
        }
        throw new AttachmentNotFound(attachmentId);
    }

    @Transactional
    public long createBlog(String customerName, String subject,
                             String body, List<MultipartFile> attachments)
            throws IOException {
        BlogUser customer = buRepo.findById(customerName).orElse(null);
        if (customer == null) {
            throw new RuntimeException("User " + customerName + " not found.");
        }
        Blog blog = new Blog();
        blog.setBloguser(customer);
        blog.setSubject(subject);
        blog.setBody(body);

        for (MultipartFile filePart : attachments) {
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            attachment.setBlog(blog);
            if (attachment.getName() != null && attachment.getName().length() > 0
                    && attachment.getContents() != null
                    && attachment.getContents().length > 0) {
                blog.getAttachments().add(attachment);
            }
        }
        Blog savedBlog = bRepo.save(blog);
        customer.getBlogs().add(savedBlog);
        return savedBlog.getId();
    }

    @Transactional(rollbackFor = BlogNotFound.class)
    public void updateBlog(long id, String subject,
                             String body, List<MultipartFile> attachments)
            throws IOException, BlogNotFound {
        Blog updatedBlog = bRepo.findById(id).orElse(null);
        if (updatedBlog == null) {
            throw new BlogNotFound(id);
        }
        updatedBlog.setSubject(subject);
        updatedBlog.setBody(body);

        for (MultipartFile filePart : attachments) {
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            attachment.setBlog(updatedBlog);
            if (attachment.getName() != null && attachment.getName().length() > 0
                    && attachment.getContents() != null
                    && attachment.getContents().length > 0) {
                updatedBlog.getAttachments().add(attachment);
            }
        }
        bRepo.save(updatedBlog);
    }
}

