package com.tunnel.e2e.dto.response;

import lombok.Data;

@Data
public class PlayButtonResp {
    private Long id;
    private String serialNo;
    private String text;
    private String keyCode;
    private String customStyle;
    private String x;
    private String y;
}
