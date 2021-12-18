package project.SangHyun.study.studyjoin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.SangHyun.study.studyjoin.domain.StudyJoin;

import java.util.Optional;

public interface StudyJoinRepository extends JpaRepository<StudyJoin, Long>, StudyJoinCustomRepository {

}
