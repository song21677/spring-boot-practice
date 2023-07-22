package com.example.practice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.regex.Pattern;

@RequiredArgsConstructor
@Controller
public class MemberController {
    private final MemberRepository memberRepository;
    @GetMapping("/add")
    public String addMemberForm(@ModelAttribute("member") Member member) {
        return "addMemberForm";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute Member member, BindingResult bindingResult) {

        // TypeMismatch 테스트
        if (member.getTest() < 5) {
            bindingResult.addError(new FieldError("member", "test", "5 이상이어야 합니다."));
        }

        // 영문자, 숫자, 특수문자 포함 최소 8~20자
        String pattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
        if (!StringUtils.hasText(member.getPassword())
                || !(member.getPassword().matches(pattern))) {
            bindingResult.addError(new FieldError("member", "password", "비밀번호는 영문자, 숫자, 특수문자를 포함해 최소 8~20자리여야 합니다."));
        }


        memberRepository.save(member);
        return "addMemberForm";
    }
}
