package project.SangHyun.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.SangHyun.web.dto.MemberRegisterRequestDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
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

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changeDepartment(String department) {
        this.department = department;
    }
}
