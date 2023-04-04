package hkmu.edu.hk.s380f.noot.controller;


import hkmu.edu.hk.s380f.noot.dao.BlogService;
import hkmu.edu.hk.s380f.noot.exception.AttachmentNotFound;
import hkmu.edu.hk.s380f.noot.exception.BlogNotFound;
import hkmu.edu.hk.s380f.noot.model.Attachment;
import hkmu.edu.hk.s380f.noot.model.Blog;
import hkmu.edu.hk.s380f.noot.view.DownloadingView;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/blog")
public class BlogController {

    @Resource
    private BlogService bService;

    // Controller methods, Form-backing object, ...
    @GetMapping(value = {"", "/list"})
    public String list(ModelMap model) {
        model.addAttribute("blogDatabase", bService.getBlogs());
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

    @PostMapping("/create")
    public View create(Form form, Principal principal) throws IOException {
        long blogId = bService.createBlog(principal.getName(),
                form.getSubject(), form.getBody(), form.getAttachments());
        return new RedirectView("/blog/view/" + blogId, true);
    }


    @GetMapping("/view/{blogId}")
    public String view(@PathVariable("blogId") long blogId,
                       ModelMap model)
            throws BlogNotFound {
        Blog blog = bService.getBlog(blogId);
        model.addAttribute("blogId", blogId);
        model.addAttribute("blog", blog);
        return "view";
    }

    @GetMapping("/{blogId}/attachment/{attachment:.+}")
    public View download(@PathVariable("blogId") long blogId,
                         @PathVariable("attachment") UUID attachmentId)
            throws BlogNotFound, AttachmentNotFound {
        Attachment attachment = bService.getAttachment(blogId, attachmentId);
        return new DownloadingView(attachment.getName(),
                attachment.getMimeContentType(), attachment.getContents());
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

    @GetMapping("/delete/{blogId}")
    public String deleteBlog(@PathVariable("blogId") long blogId)
            throws BlogNotFound {
        bService.delete(blogId);
        return "redirect:/blog/list";
    }

    @GetMapping("/{blogId}/delete/{attachment:.+}")
    public String deleteAttachment(@PathVariable("blogId") long blogId,
                                   @PathVariable("attachment") UUID attachmentId)
            throws BlogNotFound, AttachmentNotFound {
        bService.deleteAttachment(blogId, attachmentId);
        return "redirect:/blog/view/" + blogId;
    }

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

    @ExceptionHandler({BlogNotFound.class, AttachmentNotFound.class})
    public ModelAndView error(Exception e) {
        return new ModelAndView("error", "message", e.getMessage());
    }
}

