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

    public MemberInfoResponseDto getMemberInfo(MemberDetails memberDetails) {
        Member member = findMemberById(memberDetails.getId());
        return MemberInfoResponseDto.create(member);
    }

    public MemberProfileResponseDto getProfile(Long memberId) {
        Member member = findMemberById(memberId);
        return MemberProfileResponseDto.create(member);
    }

    @Transactional
    public MemberUpdateResponseDto updateMember(Long memberId, MemberUpdateRequestDto requestDto) throws IOException {
        Member member = findMemberById(memberId);
        return MemberUpdateResponseDto.create(member.updateMemberInfo(requestDto, fileStoreHelper.storeFile(requestDto.getProfileImg())));
    }

    @Transactional
    public MemberDeleteResponseDto deleteMember(Long memberId) {
        Member member = findMemberById(memberId);
        memberRepository.delete(member);
        return  MemberDeleteResponseDto.create(member);
    }

    @Transactional
    public MemberChangePwResponseDto changePassword(MemberChangePwRequestDto requestDto) {
        Member member = findMemberByEmail(requestDto.getEmail());
        member.changePassword(passwordEncoder.encode(requestDto.getPassword()));
        String redisKey = redisHelper.getRedisKey(RedisKey.PASSWORD, requestDto.getEmail());
        redisHelper.delete(redisKey);
        return MemberChangePwResponseDto.create(member);
    }

    private Member findMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
    }

    private Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
    }
}
