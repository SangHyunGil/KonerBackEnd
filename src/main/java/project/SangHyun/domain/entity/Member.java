package project.SangHyun.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
