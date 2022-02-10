package project.SangHyun.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.common.advice.exception.MemberNotFoundException;
import project.SangHyun.common.helper.FileStoreHelper;
import project.SangHyun.config.redis.RedisKey;
import project.SangHyun.config.security.member.MemberDetails;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.dto.request.MemberChangePwRequestDto;
import project.SangHyun.member.dto.request.MemberUpdateRequestDto;
import project.SangHyun.member.dto.response.*;
import project.SangHyun.member.helper.RedisHelper;
import project.SangHyun.member.repository.MemberRepository;

import java.io.IOException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final RedisHelper redisHelper;
    private final FileStoreHelper fileStoreHelper;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public MemberResponseDto getMemberInfo(MemberDetails memberDetails) {
        Member member = findMemberById(memberDetails.getId());
        return MemberResponseDto.create(member);
    }

    public MemberResponseDto getProfile(Long memberId) {
        Member member = findMemberById(memberId);
        return MemberResponseDto.create(member);
    }

    @Transactional
    public MemberResponseDto updateMember(Long memberId, MemberUpdateRequestDto requestDto) throws IOException {
        Member member = findMemberById(memberId);
        Member updatedMember = member.updateMemberInfo(requestDto, fileStoreHelper.storeFile(requestDto.getProfileImg()));
        return MemberResponseDto.create(updatedMember);
    }

    @Transactional
    public void deleteMember(Long memberId) {
        Member member = findMemberById(memberId);
        memberRepository.delete(member);
    }

    @Transactional
    public void changePassword(MemberChangePwRequestDto requestDto) {
        Member member = findMemberByEmail(requestDto.getEmail());
        member.changePassword(passwordEncoder.encode(requestDto.getPassword()));
        String redisKey = redisHelper.getRedisKey(RedisKey.PASSWORD, requestDto.getEmail());
        redisHelper.delete(redisKey);
    }

    private Member findMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
    }

    private Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
    }
}
