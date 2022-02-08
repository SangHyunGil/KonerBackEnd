package project.SangHyun.member.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import project.SangHyun.member.service.MemberService;

import java.io.IOException;


@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final RedisHelper redisHelper;
    private final FileStoreHelper fileStoreHelper;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Override
    public MemberInfoResponseDto getMemberInfo(MemberDetails memberDetails) {
        Member member = memberRepository.findByEmail(memberDetails.getUsername()).orElseThrow(MemberNotFoundException::new);
        return MemberInfoResponseDto.create(member);
    }

    @Override
    public MemberProfileResponseDto getProfile(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        return MemberProfileResponseDto.create(member);
    }

    @Override
    @Transactional
    public MemberUpdateResponseDto updateMember(Long memberId, MemberUpdateRequestDto requestDto) throws IOException {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        return MemberUpdateResponseDto.create(member.updateMemberInfo(requestDto, fileStoreHelper.storeFile(requestDto.getProfileImg())));
    }

    @Override
    @Transactional
    public MemberDeleteResponseDto deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        memberRepository.delete(member);
        return  MemberDeleteResponseDto.create(member);
    }

    @Override
    @Transactional
    public MemberChangePwResponseDto changePassword(MemberChangePwRequestDto requestDto) {
        Member member = memberRepository.findByEmail(requestDto.getEmail()).orElseThrow(MemberNotFoundException::new);
        member.changePassword(passwordEncoder.encode(requestDto.getPassword()));
        redisHelper.delete(getRedisKey(RedisKey.PASSWORD, requestDto.getEmail()));
        return MemberChangePwResponseDto.create(member);
    }

    private String getRedisKey(RedisKey redisKey, String email) {
        return redisKey.getKey() + email;
    }
}
