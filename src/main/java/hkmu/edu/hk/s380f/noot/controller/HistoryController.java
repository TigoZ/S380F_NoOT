package hkmu.edu.hk.s380f.noot.controller;


import hkmu.edu.hk.s380f.noot.dao.CommentHistoryService;
import hkmu.edu.hk.s380f.noot.dao.UserManagementService;
import hkmu.edu.hk.s380f.noot.model.Comment;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;


@Controller
@RequestMapping("/comment-history")
public class HistoryController {
    @Resource
    private CommentHistoryService commentHistoryService;

    @GetMapping({"", "/", "/comment"})
    public String showCommentHistory(ModelMap model) {
        List<Comment> comments = commentHistoryService.getAllComments();
        model.addAttribute("comments", comments);
        return "comment";
    }
    public static class Form {
        private long id;
        private String content;
        private String userid;
        private long blogid;
        private LocalDateTime CreatedAt;
        private LocalDateTime UpdatedAt;
        private List<MultipartFile> comments;
        // Getters and Setters of customerName, subject, body, attachments

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public long getBlogid() {
            return blogid;
        }

        public void setBlogid(long blogid) {
            this.blogid = blogid;
        }

        public LocalDateTime getCreatedAt() {
            return CreatedAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            CreatedAt = createdAt;
        }

        public LocalDateTime getUpdatedAt() {
            return UpdatedAt;
        }

        public void setUpdatedAt(LocalDateTime updatedAt) {
            UpdatedAt = updatedAt;
        }

        public List<MultipartFile> getComments() {
            return comments;
        }

        public void setComments(List<MultipartFile> comments) {
            this.comments = comments;
        }
    }
}