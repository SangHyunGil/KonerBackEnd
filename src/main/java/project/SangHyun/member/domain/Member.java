package project.SangHyun.member.domain;

import lombok.*;
import project.SangHyun.common.EntityDate;
import project.SangHyun.member.dto.request.MemberUpdateRequestDto;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "MEMBERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public class Member extends EntityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private String profileImgUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole memberRole;

    public Member(Long id) {
        this.id = id;
    }

    @Builder
    public Member(String email, String password, String nickname, String department, String profileImgUrl, MemberRole memberRole) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.department = department;
        this.profileImgUrl = profileImgUrl;
        this.memberRole = memberRole;
    }

    public Member updateMemberInfo(MemberUpdateRequestDto requestDto, String profileImgUrl) {
        this.email = requestDto.getEmail();
        this.nickname = requestDto.getNickname();
        this.department = requestDto.getDepartment();
        this.profileImgUrl = profileImgUrl;
        return this;
    }

    public void changeRole(MemberRole memberRole) {
        this.memberRole = memberRole;
    }

    public void changePassword(String password) {
        this.password = password;
    }
}
