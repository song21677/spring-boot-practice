package com.example.practice;

import java.sql.SQLException;
import java.util.Optional;

public interface MemberRepository {
    public abstract Member save(Member member) throws SQLException;
    Member findByLoginId(String loginId);
    Member findById(Long id);
}
