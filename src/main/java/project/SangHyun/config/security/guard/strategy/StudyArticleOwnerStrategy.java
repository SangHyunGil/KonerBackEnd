package project.SangHyun.config.security.guard.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyarticle.repository.StudyArticleRepository;

@Component
@RequiredArgsConstructor
public class StudyArticleOwnerStrategy implements AuthStrategy {

    private final StudyArticleRepository studyArticleRepository;

    public boolean check(Long accessMemberId, Long articleId) {
        StudyArticle studyArticle = studyArticleRepository.findById(articleId).orElseThrow(() -> new AccessDeniedException(""));
        return studyArticle.getCreatorId().equals(accessMemberId);
    }
}
