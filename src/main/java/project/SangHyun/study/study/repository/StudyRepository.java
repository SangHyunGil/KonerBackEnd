package project.SangHyun.study.study.repository;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.domain.StudyCategory;

public interface StudyRepository extends JpaRepository<Study, Long>, StudyCustomRepository {
    @Query("select s from Study s " +
            "left join fetch s.member " +
            "where s.category = :category and s.id < :studyId " +
            "order by s.id desc")
    Slice<Study> findAllOrderByStudyIdDesc(@Param("studyId") Long lastStudyId,
                                           @Param("category") StudyCategory category, Pageable pageable);
}
