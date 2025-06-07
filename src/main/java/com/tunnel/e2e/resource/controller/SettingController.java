package com.tunnel.e2e.resource.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SettingController {

    @GetMapping("/settings")
    public String settings() {
        return "setting";
    }



}
