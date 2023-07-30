package com.example.practice;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class MemberSaveForm {
    // 필수 정보
    // 5 ~ 20자의 영문 소문자, 숫자와 특수기호(_), (-)만 사용 가능
    @NotBlank
    @Pattern(regexp = "^[a-z0-9_-]{5,20}$", groups = SecondValidation.class)
    private String loginId;

    // 필수 정보
    // 영문 대/소문자, 숫자, 특수문자를 포함한 8 ~ 16자
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,16}$", groups = SecondValidation.class)
    private String password;

    // 필수 정보
    @NotBlank
    private String name;

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
