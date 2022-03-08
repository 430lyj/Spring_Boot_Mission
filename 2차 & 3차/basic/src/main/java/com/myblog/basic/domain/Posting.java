package com.myblog.basic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="user_id")
    private User writer;

    private String password;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public static Posting createPosting(Board board, String title, String body, User writer, String password){
        Posting posting = new Posting();
        posting.setBoard(board);
        posting.setTitle(title);
        posting.setBody(body);
        posting.setWriter(writer);
        posting.setPassword(password);

        return posting;
    }
}
