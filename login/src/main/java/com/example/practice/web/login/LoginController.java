package com.example.practice.web.login;

import com.example.practice.entity.Member;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {
	private final LoginService loginService;
	// private final SessionManager sessionManager;
	@GetMapping("/login")
	public String loginForm(@ModelAttribute LoginForm form) {
		return "loginForm";
	}

	@PostMapping("/login")
	public String login(@RequestParam(defaultValue = "/") String redirectURL, @Validated @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletRequest request) {
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
		//세션 관리자를 통해 세션을 생성하고, 회원 데이터 보관
		// sessionManager.createSession(loginMember, response);
		HttpSession session = request.getSession();
		session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember.get());
		return "redirect:" + redirectURL;
	}

	@PostMapping("/logout")
	public String logout(HttpServletRequest request) {
		// sessionManager.expire(request);
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return "redirect:/";
	}
}
