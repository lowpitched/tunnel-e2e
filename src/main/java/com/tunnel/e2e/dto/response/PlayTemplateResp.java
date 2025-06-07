package com.tunnel.e2e.dto.response;

import com.tunnel.e2e.dto.request.PlayButtonReq;
import lombok.Data;

import java.util.List;

@Data
public class PlayTemplateResp {

    private Long id;
    private String name;
    private List<PlayButtonReq> buttons;

}
