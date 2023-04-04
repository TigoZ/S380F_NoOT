package hkmu.edu.hk.s380f.noot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index() {
        return "redirect:/blog/list";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}

