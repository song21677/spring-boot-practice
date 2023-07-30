package com.example.practice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MemberRepository memberRepository;
    @GetMapping("/")
    public String home(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {

        log.debug("{}", memberId);
        if (memberId == null) {
            return "home";
        }

        // 로그인
        // 쿠키는 존재하지만, 시간이 지나 DB에 회원 정보가 사라진 경우에 발생할 수 있다.
        Member loginMember = memberRepository.findById(memberId);
        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }
}
