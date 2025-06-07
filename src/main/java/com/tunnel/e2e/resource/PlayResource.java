package com.tunnel.e2e.resource;

import com.tunnel.e2e.service.RobotService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayResource {

    @Resource
    private RobotService robotService;

    @GetMapping("/play/key/{keyCode}")
    public void keyType(@PathVariable String keyChar) {
        robotService.typeKey(keyChar);
    }

    @GetMapping("/play/mouse/{x}/{y}")
    public void mouseMove(@PathVariable Integer x, @PathVariable Integer y) {
        robotService.moveMouse(x, y);
    }

}
