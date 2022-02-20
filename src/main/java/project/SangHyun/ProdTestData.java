package project.SangHyun;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.member.domain.Department;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.domain.MemberRole;
import project.SangHyun.member.repository.MemberRepository;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Profile("dev")
public class ProdTestData {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.initMember();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final MemberRepository memberRepository;
        private final PasswordEncoder passwordEncoder;

        private void initMember() {
            Member memberA = new Member("KonerAdmin", passwordEncoder.encode("KonerAdmin!"), "상현", Department.CSE, "https://koner-bucket.s3.ap-northeast-2.amazonaws.com/profileImg/koryong1.jpg", MemberRole.ROLE_ADMIN, "상현입니다.");
            memberRepository.save(memberA);
        }
    }
}
