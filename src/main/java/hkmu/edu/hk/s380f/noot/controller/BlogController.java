package hkmu.edu.hk.s380f.noot.controller;


import hkmu.edu.hk.s380f.noot.dao.BlogService;
import hkmu.edu.hk.s380f.noot.dao.BlogUserService;
import hkmu.edu.hk.s380f.noot.dao.CommentRepository;
import hkmu.edu.hk.s380f.noot.dao.CommentService;
import hkmu.edu.hk.s380f.noot.exception.AttachmentNotFound;
import hkmu.edu.hk.s380f.noot.exception.BlogNotFound;
import hkmu.edu.hk.s380f.noot.model.Attachment;
import hkmu.edu.hk.s380f.noot.model.Blog;
import hkmu.edu.hk.s380f.noot.model.BlogUser;
import hkmu.edu.hk.s380f.noot.model.Comment;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Controller
@RequestMapping("/blog")
public class BlogController {

    @Resource
    private BlogService bService;

    @Resource
    private CommentRepository commentRepository;

    @Autowired
    private BlogUserService blogUserService;

    @Autowired
    private CommentService commentService;


    private static final Logger logger = LoggerFactory.getLogger(BlogController.class);

    // Controller methods, Form-backing object, ...
    @GetMapping(value = {"", "/list"})
    public String list(ModelMap model, Principal principal) {
        List<Blog> blogs = bService.getBlogs();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        model.addAttribute("currentUsername", currentUsername);

        if (principal != null) {
            String loggedInUsername = principal.getName();
            Map<Long, Boolean> canEditOrDelete = new HashMap<>();
            for (Blog blog : blogs) {
                boolean canEdit = loggedInUsername.equals(blog.getCustomerName()) || blogUserService.isAdmin(loggedInUsername);
                canEditOrDelete.put(blog.getId(), canEdit);
            }
            model.addAttribute("canEditOrDelete", canEditOrDelete);
        }
        model.addAttribute("blogDatabase", blogs);
        return "list";
    }


    @GetMapping("/create")
    public ModelAndView create() {
        return new ModelAndView("add", "blogForm", new Form());
    }

    public static class Form {
        private String subject;
        private String body;
        private List<MultipartFile> attachments;




        // Getters and Setters of customerName, subject, body, attachments
        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public List<MultipartFile> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<MultipartFile> attachments) {
            this.attachments = attachments;
        }
    }



    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping("/create")
    public View create(Form form, Principal principal) throws IOException {
        long blogId = bService.createBlog(principal.getName(),
                form.getSubject(), form.getBody(), form.getAttachments());

        logger.info("Blog created with customerName: {}", principal.getName());

        return new RedirectView("/blog/view/" + blogId, true);
    }


    @GetMapping("/view/{blogId}")
    public String view(@PathVariable("blogId") long blogId, ModelMap model)
            throws BlogNotFound {
        Blog blog = bService.getBlog(blogId);
        model.addAttribute("blogId", blogId);
        model.addAttribute("blog", blog);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        model.addAttribute("currentUsername", currentUsername);

        return "view";
    }


    @GetMapping("/{blogId}/image/{attachment:.+}")
    public ResponseEntity<byte[]> getImage(@PathVariable("blogId") long blogId,
                                           @PathVariable("attachment") UUID attachmentId)
            throws BlogNotFound, AttachmentNotFound {
        Attachment attachment = bService.getAttachment(blogId, attachmentId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(attachment.getMimeContentType()));
        headers.setContentDispositionFormData(attachment.getName(), attachment.getName());
        return new ResponseEntity<>(attachment.getContents(), headers, HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/delete/{blogId}")
    public String deleteBlog(@PathVariable("blogId") long blogId)
            throws BlogNotFound {
        bService.delete(blogId);
        return "redirect:/blog/list";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{blogId}/delete/{attachment:.+}")
    public String deleteAttachment(@PathVariable("blogId") long blogId,
                                   @PathVariable("attachment") UUID attachmentId)
            throws BlogNotFound, AttachmentNotFound {
        bService.deleteAttachment(blogId, attachmentId);
        return "redirect:/blog/view/" + blogId;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/edit/{blogId}")
    public ModelAndView showEdit(@PathVariable("blogId") long blogId,
                                 Principal principal, HttpServletRequest request)
            throws BlogNotFound {
        Blog blog = bService.getBlog(blogId);
        if (blog == null
                || (!request.isUserInRole("ROLE_ADMIN")
                && !principal.getName().equals(blog.getCustomerName()))) {
            return new ModelAndView(new RedirectView("/blog/list", true));
        }

        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("blog", blog);

        Form blogForm = new Form();
        blogForm.setSubject(blog.getSubject());
        blogForm.setBody(blog.getBody());
        modelAndView.addObject("blogForm", blogForm);

        return modelAndView;
    }

    @PostMapping("/edit/{blogId}")
    public String edit(@PathVariable("blogId") long blogId, Form form,
                       Principal principal, HttpServletRequest request)
            throws IOException, BlogNotFound {
        Blog blog = bService.getBlog(blogId);
        if (blog == null
                || (!request.isUserInRole("ROLE_ADMIN")
                && !principal.getName().equals(blog.getCustomerName()))) {
            return "redirect:/blog/list";
        }

        bService.updateBlog(blogId, form.getSubject(),
                form.getBody(), form.getAttachments());
        return "redirect:/blog/view/" + blogId;
    }

    @PostMapping("/comment/{blogId}")
    public String saveComment(@PathVariable long blogId, @RequestParam String username, @RequestParam String content) {
        bService.saveComment(blogId, content);
        return "redirect:/blog/view/" + blogId;
    }


    @PostMapping("/comment/delete/{commentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteComment(@PathVariable("commentId") Long commentId, RedirectAttributes redirectAttributes) {
        Comment comment = commentService.findById(commentId);
        Long blogId = comment.getBlog().getId();
        commentService.deleteComment(commentId);
        redirectAttributes.addFlashAttribute("message", "Comment deleted successfully.");
        return "redirect:/blog/view/" + blogId;
    }


    @GetMapping({"/profile", "/profile/{username}"})
    public String showProfile(Model model, Principal principal, @PathVariable(required = false) String username) {
        if (principal == null) {
            return "redirect:/login";
        }

        if (username == null) {
            username = principal.getName();
        }

        List<Blog> userBlogs = bService.getBlogsByUsername(username);
        model.addAttribute("userBlogs", userBlogs);

        BlogUser blogUser = blogUserService.findByUsername(username);
        model.addAttribute("user", blogUser);
        model.addAttribute("description", blogUser.getDescription());

        boolean canEditProfile = principal.getName().equals(username) || blogUserService.isAdmin(principal.getName());
        model.addAttribute("canEditProfile", canEditProfile);


        if (username.equals(principal.getName())) {
            return "profile";
        } else {
            return "userProfile";
        }
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/profile/edit/{username}")
    public ModelAndView editProfile(@PathVariable("username") String username, Principal principal) {
        BlogUser user = blogUserService.findByUsername(username);

        if (user == null || (!blogUserService.isAdmin(principal.getName()) && !principal.getName().equals(user.getUsername()))) {
            return new ModelAndView(new RedirectView("/profile", true));
        }

        ModelAndView modelAndView = new ModelAndView("editProfile");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping("/profile/edit/{username}")
    public String updateProfile(@PathVariable("username") String username, @ModelAttribute("user") BlogUser updatedUser, Principal principal) {
        BlogUser user = blogUserService.findByUsername(username);

        if (user == null || (!blogUserService.isAdmin(principal.getName()) && !principal.getName().equals(user.getUsername()))) {
            return "redirect:/profile";
        }

        user.setEmail(updatedUser.getEmail());
        user.setPhoneNumber(updatedUser.getPhoneNumber());
        user.setDescription(updatedUser.getDescription());

        blogUserService.save(user);

        return "redirect:/blog/profile";
    }


    @ExceptionHandler({BlogNotFound.class, AttachmentNotFound.class})
    public ModelAndView error(Exception e) {
        return new ModelAndView("error", "message", e.getMessage());
    }
}

