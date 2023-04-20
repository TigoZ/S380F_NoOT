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

import java.util.List;


@Controller
@RequestMapping("/comment-history")
public class HistoryController {
    @Resource
    private CommentHistoryService commentHistoryService;

    @GetMapping("/comment")
    public String showCommentHistory(Model model) {
        List<Comment> comments = commentHistoryService.getAllComments();
        model.addAttribute("commentDatabase", comments);
        return "commentHistory";
    }
    public static class Form {

        private List<MultipartFile> comments;

        // Getters and Setters of customerName, subject, body, attachments

        public List<MultipartFile> getComments() {
            return comments;
        }

        public void setComments(List<MultipartFile> comments) {
            this.comments = comments;
        }
    }
}