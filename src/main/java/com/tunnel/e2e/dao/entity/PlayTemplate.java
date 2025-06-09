package com.tunnel.e2e.dao.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "play_template")
public class PlayTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(cascade= CascadeType.ALL)
    @JoinColumn(name = "template_id") // 指定 play_button 表中外键字段名
    private List<PlayButton> buttons;

}
