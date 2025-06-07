package com.tunnel.e2e.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class ScreenStreamService {

    public void streamScreen(HttpServletResponse response) throws IOException, AWTException {
        response.setContentType("multipart/x-mixed-replace;boundary=BoundaryString");

        Robot robot = new Robot();
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

        try (OutputStream output = response.getOutputStream()) {
            while (true) {
                BufferedImage image = robot.createScreenCapture(screenRect);
                ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
                ImageIO.write(image, "jpeg", jpegOutputStream);
                byte[] imageBytes = jpegOutputStream.toByteArray();

                output.write("--BoundaryString\r\n".getBytes());
                output.write("Content-Type: image/jpeg\r\n".getBytes());
                output.write(("Content-Length: " + imageBytes.length + "\r\n\r\n").getBytes());
                output.write(imageBytes);
                output.flush();

                Thread.sleep(100); // 控制帧率（约 10 FPS）
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
