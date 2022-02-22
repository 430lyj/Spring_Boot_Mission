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

    public static Posting createPosting(Board board, String title, String body, String writer, String password){
        Posting posting = new Posting();
        posting.setBoard(board);
        posting.setTitle(title);
        posting.setBody(body);
        posting.setWriter(writer);
        posting.setPassword(password);

        return posting;
    }
}
