package project.SangHyun.member.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import project.SangHyun.common.EntityDate;

import javax.persistence.*;

@Entity
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
    private Introduction introduction;

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

    public Member(String nickname, Department department, String introduction) {
        this.nickname = new Nickname(nickname);
        this.department = department;
        this.introduction = new Introduction(introduction);
    }

    @Builder
    public Member(String email, String password, String nickname, Department department, String profileImgUrl, MemberRole memberRole, String introduction) {
        this.email = new Email(email);
        this.password = new Password(password);
        this.nickname = new Nickname(nickname);
        this.department = department;
        this.profileImgUrl = new MemberProfileImgUrl(profileImgUrl);
        this.memberRole = memberRole;
        this.introduction = new Introduction(introduction);
    }

    public Member update(Member member, String profileImgUrl) {
        this.nickname = new Nickname(member.getNickname());
        this.department = member.getDepartment();
        if (updatedProfileImg(profileImgUrl)) {
            this.profileImgUrl = new MemberProfileImgUrl(profileImgUrl);
        }
        this.introduction = new Introduction(member.getIntroduction());
        return this;
    }

    private boolean updatedProfileImg(String profileImgUrl) {
        return profileImgUrl != null;
    }

    public void authenticate() {
        this.memberRole = MemberRole.ROLE_MEMBER;
    }

    public void changePassword(String password) {
        this.password = new Password(password);
    }

    public Long getId() {
        return id;
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

    public Department getDepartment() {
        return department;
    }

    public String getDepartmentName() {
        return department.getDesc();
    }

    public String getProfileImgUrl() {
        return profileImgUrl.getProfileImgUrl();
    }

    public MemberRole getMemberRole() {
        return memberRole;
    }

    public String getIntroduction() {
        return introduction.getIntroduction();
    }
}
