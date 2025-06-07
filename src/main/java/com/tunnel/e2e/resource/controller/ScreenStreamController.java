package com.tunnel.e2e.resource.controller;

import com.tunnel.e2e.service.ScreenStreamService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
public class ScreenStreamController {

    @Resource
    private ScreenStreamService screenStreamService;

    private volatile boolean isStreamingEnabled = true;

    @GetMapping("/stream/screen")
    public void streamScreen(HttpServletRequest request, HttpServletResponse response) throws IOException, AWTException {

        response.setContentType("multipart/x-mixed-replace;boundary=BoundaryString");

        Robot robot = new Robot();
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

        try (OutputStream output = response.getOutputStream()) {
            while (isStreamingEnabled) {
                BufferedImage image = robot.createScreenCapture(screenRect);
                ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
                ImageIO.write(image, "jpeg", jpegOutputStream);
                byte[] imageBytes = jpegOutputStream.toByteArray();

                output.write("--BoundaryString\r\n".getBytes());
                output.write("Content-Type: image/jpeg\r\n".getBytes());
                output.write(("Content-Length: " + imageBytes.length + "\r\n\r\n").getBytes());
                output.write(imageBytes);
                output.flush();

                Thread.sleep(33); // 控制帧率（约 10 FPS）
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    //关闭上面连接打开的websockt
    @GetMapping("/stop")
    public String stop() {
        isStreamingEnabled = false;
        return  "stop";
    }

    @GetMapping("/start")
    public void start() {
        isStreamingEnabled = true;
    }

}
