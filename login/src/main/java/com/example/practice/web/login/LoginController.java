package com.example.practice.web.login;

import com.example.practice.entity.Member;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
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
