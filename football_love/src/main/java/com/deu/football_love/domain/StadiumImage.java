package com.deu.football_love.domain;

import javax.persistence.*;

@Entity
public class StadiumImage extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "stadiumimg_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stadium_id")
    private Stadium stadiumId;

    @Column(name = "stadiumimg_uri")
    private String uri;
}
