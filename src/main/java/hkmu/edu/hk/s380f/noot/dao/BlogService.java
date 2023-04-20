package hkmu.edu.hk.s380f.noot.dao;


import hkmu.edu.hk.s380f.noot.exception.AttachmentNotFound;
import hkmu.edu.hk.s380f.noot.exception.BlogNotFound;
import hkmu.edu.hk.s380f.noot.model.Attachment;
import hkmu.edu.hk.s380f.noot.model.Blog;
import hkmu.edu.hk.s380f.noot.model.BlogUser;
import hkmu.edu.hk.s380f.noot.model.Comment;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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

    @Autowired
    private BlogUserRepository blogUserRepository;



    @Transactional
    public List<Blog> getBlogs() {
        List<Blog> blogs = bRepo.findAll();
        for (Blog blog : blogs) {
            blog.setCustomerName(blog.getCustomer().getUsername());
        }
        return blogs;
    }

    @Transactional
    public Blog getBlog(long id)
            throws BlogNotFound {
        Blog blog = bRepo.findById(id).orElse(null);
        if (blog == null) {
            throw new BlogNotFound(id);
        }
        blog.setCustomerName(blog.getCustomer().getUsername());
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
        deletedBlog.getCustomer().getBlogs().remove(deletedBlog);
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
        blog.setCustomer(customer);
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

    @Transactional
    public void saveComment(long blogId, String content) {
        Blog blog = bRepo.findById(blogId).orElse(null);
        if (blog == null) {
            throw new RuntimeException("Blog " + blogId + " not found.");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        BlogUser currentUser = blogUserRepository.findByUsername(currentUserName);

        if (currentUser == null) {
            throw new RuntimeException("User " + currentUserName + " not found.");
        }

        Comment comment = new Comment();
        comment.setUser(currentUser);
        comment.setBlog(blog);
        comment.setContent(content);

        blog.addComment(comment);
        bRepo.save(blog);
    }




    @Transactional
    public List<Blog> getBlogsByUsername(String username) {
        BlogUser user = buRepo.findById(username).orElse(null);
        if (user != null) {
            return user.getBlogs();
        } else {
            return new ArrayList<>();
        }
    }



}

