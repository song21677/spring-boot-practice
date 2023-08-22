package com.example.practice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final JdbcMemberRepository jdbcMemberRepository;
    @GetMapping("/")
    public String home(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {

        log.debug("{}", memberId);
        if (memberId == null) {
            return "home";
        }

        try {
            Member loginMember = jdbcMemberRepository.findById(memberId);
            model.addAttribute("member", loginMember);
            return "loginHome";
        } catch (NoSuchElementException e) {
            return "home";
        }
    }
}
