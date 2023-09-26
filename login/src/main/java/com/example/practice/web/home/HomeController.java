package com.example.practice.web.home;

import com.example.practice.dao.JdbcMemberRepository;
import com.example.practice.dao.JpaMemberRepository;
import com.example.practice.entity.Member;
import com.example.practice.web.login.SessionConst;
import com.example.practice.web.login.SessionManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final JpaMemberRepository jpaMemberRepository;
    private final SessionManager sessionManager;
    @GetMapping("/")
    public String home(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {

        // Member member = (Member) sessionManager.getSession(request);
        //HttpSession session = request.getSession(false);
        log.debug("{}", loginMember);
//        if (session == null) {
//            return "home";
//        }
        if (loginMember == null) {
            return "home";
        }

        // Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        model.addAttribute("member", loginMember);
        return "loginHome";
    }
}
