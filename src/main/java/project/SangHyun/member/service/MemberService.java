package project.SangHyun.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.common.advice.exception.MemberNotFoundException;
import project.SangHyun.common.helper.AwsS3BucketHelper;
import project.SangHyun.config.redis.RedisKey;
import project.SangHyun.config.security.member.MemberDetails;
import project.SangHyun.member.controller.dto.request.ChangePwRequestDto;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.helper.RedisHelper;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.member.service.dto.request.MemberUpdateDto;
import project.SangHyun.member.service.dto.response.MemberDto;

import java.io.IOException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final RedisHelper redisHelper;
    private final AwsS3BucketHelper awsS3BucketHelper;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public MemberDto getMemberInfo(MemberDetails memberDetails) {
        Member member = findMemberById(memberDetails.getId());
        return MemberDto.create(member);
    }

    public MemberDto getProfile(Long memberId) {
        Member member = findMemberById(memberId);
        return MemberDto.create(member);
    }

    @Transactional
    public MemberDto updateMember(Long memberId, MemberUpdateDto requestDto) throws IOException {
        Member member = findMemberById(memberId);
        Member updatedMember = member.update(requestDto.toEntity(), awsS3BucketHelper.store(requestDto.getProfileImg()));
        return MemberDto.create(updatedMember);
    }

    @Transactional
    @PreAuthorize("@MemberOwner.check(memberId)")
    public void deleteMember(Long memberId) {
        Member member = findMemberById(memberId);
        memberRepository.delete(member);
    }

    @Transactional
    public void changePassword(ChangePwRequestDto requestDto) {
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
