package com.myblog.basic.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter @Setter
@Table(name = "UserTable")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Long age;
    private String email;
}
