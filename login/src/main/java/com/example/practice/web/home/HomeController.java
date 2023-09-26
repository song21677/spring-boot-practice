package com.example.practice.web.home;

import com.example.practice.dao.JdbcMemberRepository;
import com.example.practice.dao.JpaMemberRepository;
import com.example.practice.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final JpaMemberRepository jpaMemberRepository;
    @GetMapping("/")
    public String home(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {

        log.debug("{}", memberId);
        if (memberId == null) {
            return "home";
        }

        Optional<Member> loginMember = jpaMemberRepository.findById(memberId);
        if(loginMember.isEmpty()) return "home";

        model.addAttribute("member", loginMember.get());
        return "loginHome";
    }
}
