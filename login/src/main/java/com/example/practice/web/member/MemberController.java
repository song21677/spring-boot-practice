package com.example.practice.web.member;

import com.example.practice.dao.JpaMemberRepository;
import com.example.practice.entity.Member;
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
    //private final JdbcMemberRepository jdbcMemberRepository;
    private final JpaMemberRepository jpaMemberRepository;

    @GetMapping("/add")
    public String addMemberForm(@ModelAttribute("member") MemberSaveForm form) {
        return "addMemberForm";
    }

    @PostMapping("/add")
    public String save(@Validated(ValidationSequence.class) @ModelAttribute("member") MemberSaveForm form, BindingResult bindingResult) {
        log.debug("{}", bindingResult);
        log.debug("{}", form.getName());
        if (bindingResult.hasErrors()) return "addMemberForm";

        Member member = new Member(form.getLoginId(), form.getPassword(), form.getName());
        Member savedMember = jpaMemberRepository.save(member);
        log.debug("{}, {}, {}", savedMember.getLoginId(), savedMember.getPassword(), savedMember.getName());
        return "addMemberForm";
    }
}