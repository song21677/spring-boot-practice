package com.example.practice.dao;

import com.example.practice.entity.Member;
import java.util.Optional;
import java.util.stream.DoubleStream;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByLoginId(String loginId);
}
