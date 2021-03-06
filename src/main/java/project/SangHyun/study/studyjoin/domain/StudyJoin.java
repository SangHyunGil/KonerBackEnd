package project.SangHyun.study.studyjoin.domain;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.context.ApplicationEventPublisher;
import project.SangHyun.common.EntityDate;
import project.SangHyun.member.domain.Member;
import project.SangHyun.notification.domain.NotificationType;
import project.SangHyun.dto.request.NotificationRequestDto;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.domain.StudyRole;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public class StudyJoin extends EntityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "join_id")
    private Long id;

    @Embedded
    private ApplyContent applyContent;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StudyRole studyRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Study study;

    public StudyJoin(Long id) {
        this.id = id;
    }

    @Builder
    public StudyJoin(Member member, String applyContent, Study study, StudyRole studyRole) {
        this.member = member;
        this.applyContent = new ApplyContent(applyContent);
        this.study = study;
        this.studyRole = studyRole;
    }

    public void setStudy(Study study) {
        this.study = study;
    }

    public void acceptMember() {
        this.studyRole = StudyRole.MEMBER;
    }

    public void publishEvent(ApplicationEventPublisher eventPublisher, NotificationType notificationType) {
        eventPublisher.publishEvent(new NotificationRequestDto(member, notificationType,
                notificationType.makeContent(study.getTitle()), notificationType.makeUrl(study.getId())));
    }

    public Long getParticipantId() {
        return member.getId();
    }

    public String getParticipantNickname() {
        return member.getNickname();
    }

    public String getParticipantProfileImgUrl() {
        return member.getProfileImgUrl();
    }

    public void changeAuthority(StudyRole studyRole) {
        this.studyRole = studyRole;
    }
}
