package com.example.practice.web.member;

import com.example.practice.domain.member.JdbcMemberRepository;
import com.example.practice.domain.member.Member;
import com.example.practice.web.login.LoginForm;
import com.example.practice.web.login.LoginService;
import com.example.practice.web.login.ValidationSequence;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberController {
    private final JdbcMemberRepository jdbcMemberRepository;
    private final LoginService loginService;

    @GetMapping("/add")
    public String addMemberForm(@ModelAttribute("member") MemberSaveForm form) {
        return "addMemberForm";
    }

    @PostMapping("/add")
    public String save(@Validated(ValidationSequence.class) @ModelAttribute("member") MemberSaveForm form, BindingResult bindingResult) {
        log.debug("{}", bindingResult);
        log.debug("{}", form.getName());
        if (bindingResult.hasErrors()) return "addMemberForm";

        Member member = new Member(0, form.getLoginId(), form.getPassword(), form.getName());
        Member save = jdbcMemberRepository.save(member);
        log.debug("{}, {}, {}", member.getLoginId(), member.getPassword(), member.getName());
        return "addMemberForm";
    }

    @GetMapping("/login")
    public String loginForm(@ModelAttribute LoginForm form) {
        return "loginForm";
    }

    @PostMapping("/login")
    public String login(@RequestParam(defaultValue = "/") String redirectURL, @Validated @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response) {
        log.debug("{} {}", form.getLoginId(), form.getPassword());
        log.debug("{}", bindingResult);
        if (bindingResult.hasErrors()) {
            return "loginForm";
        }

        Optional<Member> loginMember = loginService.login(form.getLoginId(), form.getPassword());

        if (loginMember.isEmpty()) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "loginForm";
        }

            log.debug("{}", loginMember.get().getLoginId());

            // 쿠키에 시간 정보를 주지 않으면 세션 쿠키(브라우저 종료시 모두 종료)
            // 로그인 성공 처리
            Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.get().getId()));
            response.addCookie(idCookie);
            return "redirect:" + redirectURL;
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        expireCookie(response, "memberId");
        return "redirect:/";
    }

    private static void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}