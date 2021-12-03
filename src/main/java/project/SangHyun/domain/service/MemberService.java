package project.SangHyun.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import project.SangHyun.advice.exception.MemberNotFoundException;
import project.SangHyun.advice.exception.MemberSameNickNameException;
import project.SangHyun.config.security.member.MemberDetails;
import project.SangHyun.domain.dto.MemberGetInfoResponseDto;
import project.SangHyun.domain.dto.MemberUpdateInfoResponseDto;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.repository.MemberRepository;
import project.SangHyun.web.dto.MemberUpdateInfoRequestDto;

@Slf4j
@Service
@Transactional(readOnly = true)
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * AccessToken으로 유저 정보 조회
     * @param memberDetails
     * @return
     */
    public MemberGetInfoResponseDto getMemberInfo(MemberDetails memberDetails) {
        Member member = memberRepository.findByEmail(memberDetails.getUsername()).orElseThrow(MemberNotFoundException::new);
        return MemberGetInfoResponseDto.createDto(member);
    }

    /**
     * ID(PK)로 유저 정보 조회
     * @param userId
     * @return
     */
    public MemberGetInfoResponseDto getMemberInfo(Long userId) {
        Member member = memberRepository.findById(userId).orElseThrow(MemberNotFoundException::new);
        return MemberGetInfoResponseDto.createDto(member);
    }

    /**
     * 유저 정보 수정
     * @param userId
     * @param requestDto
     * @return
     */
    @Transactional
    public MemberUpdateInfoResponseDto updateMemberInfo(Long userId, MemberUpdateInfoRequestDto requestDto) {
        Member member = memberRepository.findById(userId).orElseThrow(MemberNotFoundException::new);
        UpdateMemberInfo(member, requestDto);
        log.info("changed member = {}", member.getNickname(), member.getDepartment());
        return MemberUpdateInfoResponseDto.createDto(member);
    }

    private void UpdateMemberInfo(Member member, MemberUpdateInfoRequestDto requestDto) {
        if (requestDto.getNickname() == null) return;
        if (requestDto.getNickname().equals(member.getNickname())) throw new MemberSameNickNameException();
        member.changeNickname(requestDto.getNickname());

        if (requestDto.getDepartment().equals(member.getDepartment())) return;
        member.changeDepartment(requestDto.getDepartment());
    }
}
