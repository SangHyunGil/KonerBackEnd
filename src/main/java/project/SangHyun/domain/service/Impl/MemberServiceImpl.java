package project.SangHyun.domain.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import project.SangHyun.advice.exception.MemberNotFoundException;
import project.SangHyun.config.security.member.MemberDetails;
import project.SangHyun.domain.entity.Study;
import project.SangHyun.domain.repository.StudyJoinRepository;
import project.SangHyun.dto.response.StudyFindResponseDto;
import project.SangHyun.dto.response.MemberInfoResponseDto;
import project.SangHyun.dto.response.MemberProfileResponseDto;
import project.SangHyun.dto.response.MemberUpdateInfoResponseDto;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.repository.MemberRepository;
import project.SangHyun.domain.service.MemberService;
import project.SangHyun.dto.request.MemberUpdateInfoRequestDto;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final StudyJoinRepository studyJoinRepository;

    /**
     * AccessToken으로 유저 정보 조회
     * @param memberDetails
     * @return
     */
    @Override
    public MemberInfoResponseDto getMemberInfo(MemberDetails memberDetails) {
        Member member = memberRepository.findByEmail(memberDetails.getUsername()).orElseThrow(MemberNotFoundException::new);
        List<Study> studies = studyJoinRepository.findStudyByMemberId(member.getId());
        return MemberInfoResponseDto.createDto(member, studies);
    }

    /**
     * ID(PK)로 유저 정보 조회
     * @param memberId
     * @return
     */
    @Override
    public MemberProfileResponseDto getProfile(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        List<StudyFindResponseDto> studies = studyJoinRepository.findStudyByMemberId(memberId)
                                                .stream()
                                                .map(study -> StudyFindResponseDto.createDto(study))
                                                .collect(Collectors.toList());
        return MemberProfileResponseDto.createDto(member, studies);
    }

    /**
     * 유저 정보 수정
     * @param userId
     * @param requestDto
     * @return
     */
    @Override
    @Transactional
    public MemberUpdateInfoResponseDto updateMemberInfo(Long userId, MemberUpdateInfoRequestDto requestDto) {
        Member member = memberRepository.findById(userId).orElseThrow(MemberNotFoundException::new);
        return MemberUpdateInfoResponseDto.createDto(member.updateMemberInfo(requestDto));
    }
}
