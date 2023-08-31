package com.example.practice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
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

        Optional<Member> loginMember = jdbcMemberRepository.findById(memberId);
        if(loginMember.isEmpty()) return "home";

        model.addAttribute("member", loginMember.get());
        return "loginHome";
    }
}
