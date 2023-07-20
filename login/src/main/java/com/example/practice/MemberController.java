package com.example.practice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class MemberController {
    private final MemberRepository memberRepository;
    @GetMapping("/add")
    public String addMemberForm(@ModelAttribute("member") Member member) {
        return "addMemberForm";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute Member member) {
        memberRepository.save(member);
        return "home";
    }
}
