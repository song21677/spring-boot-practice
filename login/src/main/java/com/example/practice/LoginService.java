package com.example.practice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;


@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {
    private final JdbcMemberRepository jdbcMemberRepository;

    public Member login(String loginId, String password) {
        Member member = jdbcMemberRepository.findByLoginId(loginId);
        if (member.getPassword().equals(password)) return member;
        throw new NoSuchElementException("member not found password=" + member.getPassword());
    }
}
