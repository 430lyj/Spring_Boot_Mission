package com.myblog.basic.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Posting {
    @Id @GeneratedValue
    private Long id;

    private String title;
    private String body;
    private String writer;
    private String password;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "board_id")
    private Board board;

}
