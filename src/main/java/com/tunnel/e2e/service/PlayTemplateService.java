package com.tunnel.e2e.service;

import com.tunnel.e2e.dao.PlayTemplateDao;
import com.tunnel.e2e.dao.entity.PlayTemplate;
import com.tunnel.e2e.dto.mapper.PlayTemplateMapper;
import com.tunnel.e2e.dto.request.PlayTemplateReq;
import com.tunnel.e2e.dto.response.PlayTemplateResp;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayTemplateService {

    @Resource
    private PlayTemplateDao dao;
    @Resource
    private PlayTemplateMapper mapper;

    @Transactional
    public PlayTemplateResp save(PlayTemplateReq req) {
        PlayTemplate entity = dao.save(mapper.toEntity(req));
        return mapper.toDto(entity);
    }

    public PlayTemplateResp findById(Long id) {
        PlayTemplate entity = dao.findById(id).orElse(null);
        return mapper.toDto(entity);
    }

}
