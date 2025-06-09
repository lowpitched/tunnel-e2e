package com.tunnel.e2e.service;

import com.tunnel.e2e.dao.PlayTemplateDao;
import com.tunnel.e2e.dao.entity.PlayTemplate;
import com.tunnel.e2e.dto.mapper.PlayTemplateMapper;
import com.tunnel.e2e.dto.request.PlayTemplateReq;
import com.tunnel.e2e.dto.response.PlayTemplateResp;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PlayTemplateService {

    @Resource
    private PlayTemplateDao dao;
    @Resource
    private PlayTemplateMapper mapper;

    @Transactional
    public PlayTemplateResp save(PlayTemplateReq req) {
        PlayTemplate saveEntity = mapper.toEntity(req);
        PlayTemplate entity = dao.save(saveEntity);
        return mapper.toDto(entity);
    }

    public PlayTemplateResp findById(Long id) {
        PlayTemplate entity = dao.findById(id).orElse(null);
        return mapper.toDto(entity);
    }

    public List<PlayTemplateResp> findAll() {
        return dao.findAll().stream().map(mapper::toDto).toList();
    }

    public PlayTemplateResp update(Long id, PlayTemplateReq req) {
        PlayTemplate template = dao.findById(id).orElseThrow(() -> new RuntimeException("Template not found"));
        mapper.updateEntity(req, template);
        return mapper.toDto(dao.save(template));
    }

    public void delete(Long id) {
        dao.deleteById(id);
    }
}
