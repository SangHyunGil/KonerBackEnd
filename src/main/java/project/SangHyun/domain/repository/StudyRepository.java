package project.SangHyun.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.SangHyun.domain.entity.Study;

import java.util.List;

public interface StudyRepository extends JpaRepository<Study, Long>, StudyCustomRepository {
    @Query("select b from Study b join fetch b.member")
    List<Study> findAllByFetchJoin();
}
