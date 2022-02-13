package project.SangHyun.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.SangHyun.member.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m where m.email.email = :email")
    Optional<Member> findByEmail(@Param("email") String email);
    @Query("select m from Member m where m.nickname.nickname = :nickname")
    Optional<Member> findByNickname(@Param("nickname") String nickname);
}
