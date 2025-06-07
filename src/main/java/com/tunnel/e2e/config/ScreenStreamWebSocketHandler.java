package com.tunnel.e2e.config;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ScreenStreamWebSocketHandler extends BinaryWebSocketHandler {

    private Robot robot;

    private Rectangle screenRect;

    {
        try {
            robot = new Robot();
            screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        new Thread(() -> {
            try {
                while (session.isOpen()) {
                    session.sendMessage(screenshot()); // 发送二进制图片
                    Thread.sleep(33); // 控制帧率
                }
            } catch (Exception e) {
                try {
                    session.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    private BinaryMessage screenshot() throws IOException {
        try (ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();) {
            BufferedImage image = robot.createScreenCapture(screenRect);
            ImageIO.write(image, "png", jpegOutputStream);
            byte[] imageBytes = jpegOutputStream.toByteArray();
            return new BinaryMessage(imageBytes);
        }

    }
}
