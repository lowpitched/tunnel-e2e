package com.tunnel.e2e.dao.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "play_button")
@Data
public class PlayButton {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = PlayTemplate.class, cascade= CascadeType.ALL)
    @JoinColumn(name = "template_id")
    private PlayTemplate template;
    private String serialNo;
    private String text;
    private String keyCode;
    private String x;
    private String y;
    private String customStyle;

}
