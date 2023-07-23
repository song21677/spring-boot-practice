package com.example.practice;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MemberValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Member.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Member member = (Member)target;

        if (member.getTest() < 5) {

        }

        // 영문자, 숫자, 특수문자 포함 최소 8~20자
        String pattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
        if (!StringUtils.hasText(member.getPassword())
                || !(member.getPassword().matches(pattern))) {
            errors.rejectValue("password", "config",null);
        }
    }
}