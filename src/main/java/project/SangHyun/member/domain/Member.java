package project.SangHyun.member.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.SangHyun.member.dto.request.MemberUpdateRequestDto;
import project.SangHyun.member.enums.MemberRole;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "MEMBERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private String department;
    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    public Member(Long id) {
        this.id = id;
    }

    @Builder
    public Member(String email, String password, String nickname, String department, MemberRole memberRole) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.department = department;
        this.memberRole = memberRole;
    }

    public Member updateMemberInfo(MemberUpdateRequestDto requestDto) {
        this.email = requestDto.getEmail();
        this.nickname = requestDto.getNickname();
        this.department = requestDto.getDepartment();

        return this;
    }

    public void changeRole(MemberRole memberRole) {
        this.memberRole = memberRole;
    }

    public void changePassword(String password) {
        this.password = password;
    }
}
