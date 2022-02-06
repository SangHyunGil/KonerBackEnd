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

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    @Embedded
    private Nickname nickname;

    @Embedded
    private MemberProfileImgUrl profileImgUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Department department;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole memberRole;

    public Member(Long id) {
        this.id = id;
    }

    @Builder
    public Member(String email, String password, String nickname, Department department, String profileImgUrl, MemberRole memberRole) {
        this.email = new Email(email);
        this.password = new Password(password);
        this.nickname = new Nickname(nickname);
        this.department = department;
        this.profileImgUrl = new MemberProfileImgUrl(profileImgUrl);
        this.memberRole = memberRole;
    }

    public Member updateMemberInfo(MemberUpdateRequestDto requestDto, String profileImgUrl) {
        this.email = new Email(requestDto.getEmail());
        this.nickname = new Nickname(requestDto.getNickname());
        this.department = requestDto.getDepartment();
        this.profileImgUrl = new MemberProfileImgUrl(profileImgUrl);
        return this;
    }

    public void changeRole(MemberRole memberRole) {
        this.memberRole = memberRole;
    }

    public void changePassword(String password) {
        this.password = new Password(password);
    }

    public String getEmail() {
        return email.getEmail();
    }

    public String getPassword() {
        return password.getPassword();
    }

    public String getNickname() {
        return nickname.getNickname();
    }

    public String getDepartmentName() {
        return department.getDesc();
    }

    public String getProfileImgUrl() {
        return profileImgUrl.getProfileImgUrl();
    }
}
