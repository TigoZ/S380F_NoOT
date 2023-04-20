package hkmu.edu.hk.s380f.noot.dao;

import hkmu.edu.hk.s380f.noot.controller.BlogController;
import hkmu.edu.hk.s380f.noot.exception.AttachmentNotFound;
import hkmu.edu.hk.s380f.noot.exception.BlogNotFound;
import hkmu.edu.hk.s380f.noot.model.Attachment;
import hkmu.edu.hk.s380f.noot.model.Blog;
import hkmu.edu.hk.s380f.noot.model.BlogUser;
import hkmu.edu.hk.s380f.noot.model.Comment;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CommentHistoryService {

    @Resource
    private CommentRepository cRepo;
    @Transactional
    public List<Comment> getAllComments() {
        return cRepo.findAll();
    }

}

