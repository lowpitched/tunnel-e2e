package com.tunnel.e2e.resource;

import com.tunnel.e2e.dto.request.PlayTemplateReq;
import com.tunnel.e2e.dto.response.PlayTemplateResp;
import com.tunnel.e2e.service.PlayTemplateService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlayTemplateResource {

    @Resource
    private PlayTemplateService playTemplateService;

    @PostMapping("/play-templates")
    public PlayTemplateResp savePlayTemplate(@RequestBody PlayTemplateReq req) {
        return playTemplateService.save(req);
    }

    @PutMapping("/play-templates/{id}")
    public PlayTemplateResp updatePlayTemplate(@PathVariable Long id, @RequestBody PlayTemplateReq req) {
        return playTemplateService.update(id, req);
    }

    @DeleteMapping("/play-templates/{id}")
    public void deletePlayTemplate(@PathVariable Long id) {
        playTemplateService.delete(id);
    }

    @GetMapping("/play-templates/{id}")
    public PlayTemplateResp findById(@PathVariable Long id) {
        return playTemplateService.findById(id);
    }

    @GetMapping("/play-templates")
    public List<PlayTemplateResp> getAllTemplates() {
        return playTemplateService.findAll();
    }


}
