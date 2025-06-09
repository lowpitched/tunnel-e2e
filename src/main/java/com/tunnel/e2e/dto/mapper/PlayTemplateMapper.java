package com.tunnel.e2e.dto.mapper;

import com.tunnel.e2e.dao.entity.PlayTemplate;
import com.tunnel.e2e.dto.request.PlayTemplateReq;
import com.tunnel.e2e.dto.response.PlayTemplateResp;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE)
public interface PlayTemplateMapper {

    PlayTemplate toEntity(PlayTemplateReq request);

    PlayTemplateResp toDto(PlayTemplate entity);

    void updateEntity(PlayTemplateReq request,  @MappingTarget PlayTemplate entity);
}
