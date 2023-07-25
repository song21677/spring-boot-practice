package com.example.practice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberController {
    private final MemberRepository memberRepository;

    @GetMapping("/add")
    public String addMemberForm(@ModelAttribute("member") Member member) {
        return "addMemberForm";
    }

//    @PostMapping("/add")
//    public String save(@Validated @ModelAttribute Member member, BindingResult bindingResult) {
//        memberRepository.save(member);
//        return "addMemberForm";
//    }

    @PostMapping("/add")
    public String save(@Validated @ModelAttribute("member") MemberSaveForm form, BindingResult bindingResult) {
        Member member = new Member(form.getLoginId(), form.getPassword(), form.getName());
        Member save = memberRepository.save(member);
        return "addMemberForm";
    }
}