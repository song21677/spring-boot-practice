package com.example.practice.web.login;

import com.example.practice.dao.JdbcMemberRepository;
import com.example.practice.dao.JpaMemberRepository;
import com.example.practice.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {
    private final JpaMemberRepository jpaMemberRepository;

    public Optional<Member> login(String loginId, String password) {
        return jpaMemberRepository.findByLoginId(loginId)
            .filter(m -> m.getPassword().equals(password));
    }
}
