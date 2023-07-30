package com.example.practice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class MemberRepository {
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Optional<Member> findByLoginId(String loginId) {
        // 나중에 stream으로 바꾸기
        Optional<Member> result = Optional.empty();
        for (Long key : store.keySet()) {
            Member m = store.get(key);
            if (m.getLoginId().equals(loginId)) {
                result = Optional.ofNullable(m);
                break;
            }
        }
        return result;
    }

    public Member findById(Long id) {
        return store.get(id);
    }
}
