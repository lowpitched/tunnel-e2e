package com.tunnel.e2e.resource.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/settings")
    public String settings() {
        return "setting";
    }

    @GetMapping("/play")
    public String play() {
        return "play";
    }


}
