package com.example.practice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Entity
public class Member {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private long id;

    // 필수 정보
    // 5 ~ 20자의 영문 소문자, 숫자와 특수기호(_), (-)만 사용 가능
    @Column(nullable = false, length = 20)
    private String loginId;

    // 필수 정보
    // 영문 대/소문자, 숫자, 특수문자를 포함한 8 ~ 16자
    @Column(nullable = false, length = 16)
    private String password;

    // 필수 정보
    @Column(nullable = false)
    private String name;

    public Member() {}
    public Member(String loginId, String password, String name) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
