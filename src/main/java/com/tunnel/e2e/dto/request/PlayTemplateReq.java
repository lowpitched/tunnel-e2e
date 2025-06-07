package com.tunnel.e2e.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class PlayTemplateReq {
    private String name;
    private List<PlayButtonReq> buttons;
}
