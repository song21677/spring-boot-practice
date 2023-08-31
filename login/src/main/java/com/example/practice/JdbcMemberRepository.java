package com.example.practice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.Optional;

import static com.example.practice.ConnectionConst.*;

@Slf4j
@Repository
public class JdbcMemberRepository implements MemberRepository{
    private final SQLExceptionTranslator exTranslator;

    public JdbcMemberRepository() {
        this.exTranslator = new SQLErrorCodeSQLExceptionTranslator();
    }

    @Override
    public Member save(Member member) {

        String sql = "insert into member(id, loginId, password, name)" +
                " values(next value for member_id_seq, ?, ?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DriverManager.getConnection(URL, USERID, PASSWORD);
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getLoginId());
            pstmt.setString(2, member.getPassword());
            pstmt.setString(3, member.getName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // log.error("db error", e);
            throw exTranslator.translate("save", sql, e);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    log.info("error", e);
                }
            }

            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch(SQLException e) {
                    log.info("error", e);
                }
            }
        }
        return member;
    }
    @Override
    public Optional<Member> findByLoginId(String loginId) {
        String sql = "select * from member where loginId = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(URL, USERID, PASSWORD);
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, loginId);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                long id = rs.getLong("id");
                String password = rs.getString("password");
                String name = rs.getString("name");
                Member member = new Member(id, loginId, password, name);
                return Optional.of(member);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            // db 접속 실패, sql 문법 오류
            //log.error("error", e);

            DataAccessException resultEx = exTranslator.translate("findById", sql, e);
            // log.info("resultEx", resultEx);
            throw resultEx;
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    log.info("error", e);
                }
            }

            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    log.info("error", e);
                }
            }
        }
    }

    @Override
    public Optional<Member> findById(Long id) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "select * from member where id = ?";
        try {
            con = DriverManager.getConnection(URL, USERID, PASSWORD);
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, id);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                String loginId = rs.getString("loginId");
                String password = rs.getString("password");
                String name = rs.getString("name");
                Member member = new Member(id, loginId, password, name);
                return Optional.of(member);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            log.error("error", e);
            throw exTranslator.translate("findById", sql, e);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    log.info("error", e);
                }
            }

            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    log.info("error", e);
                }
            }
        }
    }
}
