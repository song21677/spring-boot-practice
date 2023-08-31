package com.example.practice;

import java.sql.SQLException;
import java.util.Optional;

public interface MemberRepository {
    public abstract Member save(Member member) throws SQLException;
    Optional<Member> findByLoginId(String loginId);
    Optional<Member> findById(Long id);
}
