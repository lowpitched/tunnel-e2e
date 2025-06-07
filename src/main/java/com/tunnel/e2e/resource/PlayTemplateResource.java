package com.tunnel.e2e.resource;

import com.tunnel.e2e.dto.request.PlayTemplateReq;
import com.tunnel.e2e.dto.response.PlayTemplateResp;
import com.tunnel.e2e.service.PlayTemplateService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
public class PlayTemplateResource {

    @Resource
    private PlayTemplateService playTemplateService;

    @PostMapping("/play-template")
    public PlayTemplateResp savePlayTemplate(@RequestBody PlayTemplateReq req) {
        return playTemplateService.save(req);
    }

    @GetMapping("/play-template/{id}")
    public PlayTemplateResp findById(@PathVariable Long id) {
        return playTemplateService.findById(id);
    }

}
