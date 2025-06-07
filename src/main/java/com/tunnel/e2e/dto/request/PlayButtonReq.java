package com.tunnel.e2e.dto.request;

import lombok.Data;

@Data
public class PlayButtonReq {

    private String serialNo;
    private String text;
    private String keyCode;
    private String x;
    private String y;

}
