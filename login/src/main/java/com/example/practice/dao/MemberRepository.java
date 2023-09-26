package com.example.practice.dao;

import com.example.practice.entity.Member;
import java.sql.SQLException;
import java.util.Optional;

public interface MemberRepository {
    public abstract Member save(Member member) throws SQLException;
    Optional<Member> findByLoginId(String loginId);
    Optional<Member> findById(Long id);
}
