package com.example.practice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {
    private final JdbcMemberRepository jdbcMemberRepository;

    public Optional<Member> login(String loginId, String password) {
        return jdbcMemberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password));
    }
}
