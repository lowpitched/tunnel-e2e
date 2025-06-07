package com.tunnel.e2e.dto.mapper;

import com.tunnel.e2e.dao.entity.PlayButton;
import com.tunnel.e2e.dto.request.PlayButtonReq;
import com.tunnel.e2e.dto.response.PlayButtonResp;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlayButtonMapper {

    PlayButton toEntity(PlayButtonReq request);

    PlayButtonResp toDto(PlayButton entity);

}
