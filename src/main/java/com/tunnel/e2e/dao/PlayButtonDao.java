package com.tunnel.e2e.dao;

import com.tunnel.e2e.dao.entity.PlayButton;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayButtonDao extends JpaRepository<PlayButton, Long> {
}
