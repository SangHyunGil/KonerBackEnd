package project.SangHyun.config.security.guard;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.SangHyun.config.security.guard.strategy.AuthStrategy;
import project.SangHyun.study.studyjoin.repository.StudyJoinRepository;

@Configuration
@RequiredArgsConstructor
public class GuardConfig {

    private final StudyJoinRepository studyJoinRepository;
    private final AuthHelper authHelper;

    @Bean
    public MemberAuthChecker MemberOwner(AuthStrategy memberOwnerStrategy) {
        return new MemberAuthChecker(authHelper, memberOwnerStrategy);
    }

    @Bean
    public MemberAuthChecker StudyMember(AuthStrategy studyMemberStrategy) {
        return new MemberAuthChecker(authHelper, studyMemberStrategy);
    }

    @Bean
    public MemberAuthChecker StudyCreator(AuthStrategy studyCreatorStrategy) {
        return new MemberAuthChecker(authHelper, studyCreatorStrategy);
    }

    @Bean
    public MemberAuthChecker StudyCreatorOrAdmin(AuthStrategy studyCreatorOrAdminStrategy) {
        return new MemberAuthChecker(authHelper, studyCreatorOrAdminStrategy);
    }

    @Bean
    public StudyAuthChecker StudyArticleOwner(AuthStrategy studyArticleOwnerStrategy) {
        return new StudyAuthChecker(studyJoinRepository, authHelper, studyArticleOwnerStrategy);
    }

    @Bean
    public StudyAuthChecker StudyCommentOwner(AuthStrategy studyCommentOwnerStrategy) {
        return new StudyAuthChecker(studyJoinRepository, authHelper, studyCommentOwnerStrategy);
    }

    @Bean
    public StudyAuthChecker VideoRoomOwner(AuthStrategy videoRoomOwnerStrategy) {
        return new StudyAuthChecker(studyJoinRepository, authHelper, videoRoomOwnerStrategy);
    }
}