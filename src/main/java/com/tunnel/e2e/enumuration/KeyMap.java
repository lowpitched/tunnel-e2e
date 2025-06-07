package com.tunnel.e2e.enumuration;

public enum KeyMap {

    a(65),
    b(66),
    c(67);


    private final Integer keyCode;

    KeyMap(int keyCode) {
        this.keyCode = keyCode;
    }

    public static Integer getKeyCode(String key) {
        for (KeyMap keyMap : KeyMap.values()) {
            if (keyMap.name().equals(key)) {
                return keyMap.keyCode;
            }
        }
        return null;
    }

}
