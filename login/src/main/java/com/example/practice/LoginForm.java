package com.example.practice;

import javax.validation.constraints.NotBlank;

public class LoginForm {
    @NotBlank
    private String loginId;

    @NotBlank
    private String password;

    public String getLoginId() {
        return loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
