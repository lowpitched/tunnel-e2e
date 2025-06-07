package com.tunnel.e2e.service;

import com.tunnel.e2e.enumuration.KeyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Objects;

@Service
public class RobotService {

    private static final Logger log = LoggerFactory.getLogger(RobotService.class);

    private final Robot robot;

    {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    public void typeKey(String keyChar) {
        Integer keyCode = KeyMap.getKeyCode(keyChar);
        if (Objects.isNull(keyCode)) {
            log.warn("invalid key char {}" + keyChar);
            return;
        }
        robot.keyPress(keyCode);
    }

    public void moveMouse(Integer x, Integer y) {
        log.debug("move mouse to x={} y={}", x, y);
        robot.mouseMove(x, y);
    }

    public void moveMouseAndClickLeftButton(Integer x, Integer y) {
        log.debug("move mouse to x={} y={}", x, y);
        robot.mouseMove(x, y);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    public void moveMouseAndClickRightButton(Integer x, Integer y) {
        log.debug("move mouse to x={} y={}", x, y);
        robot.mouseMove(x, y);
        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
    }


}
