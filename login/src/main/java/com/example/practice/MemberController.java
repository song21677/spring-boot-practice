package com.example.practice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class MemberController {
    private final MemberRepository memberRepository;
    private final MemberValidator memberValidator;

    @InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(memberValidator);
    }

    @GetMapping("/add")
    public String addMemberForm(@ModelAttribute("member") Member member) {
        return "addMemberForm";
    }

    @PostMapping("/add")
    public String save(@Validated @ModelAttribute Member member, BindingResult bindingResult) {
        // memberValidator.validate(member, bindingResult);
        memberRepository.save(member);
        return "addMemberForm";
    }
}