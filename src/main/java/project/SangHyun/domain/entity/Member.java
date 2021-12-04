package project.SangHyun.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.enums.Role;
import project.SangHyun.dto.request.MemberRegisterRequestDto;
import project.SangHyun.dto.request.MemberUpdateInfoRequestDto;

import javax.persistence.*;
import java.util.List;

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
    private Role role;

    public static Member createMember(MemberRegisterRequestDto requestDto) {
        return Member.builder()
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .nickname(requestDto.getNickname())
                .department(requestDto.getDepartment())
                .role(Role.ROLE_NOT_PERMITTED)
                .build();
    }

    public Member updateMemberInfo(MemberUpdateInfoRequestDto requestDto) {
        this.email = requestDto.getEmail();
        this.nickname = requestDto.getNickname();
        this.department = requestDto.getDepartment();

        return this;
    }

    @Builder
    public Member(String email, String password, String nickname, String department, Role role) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.department = department;
        this.role = role;
    }

    public void changeRole(Role role) {
        this.role = role;
    }

    public void changePassword(String password) {
        this.password = password;
    }
}
