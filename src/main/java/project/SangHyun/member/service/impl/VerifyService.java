package project.SangHyun.member.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.common.advice.exception.MemberNotFoundException;
import project.SangHyun.common.advice.exception.RedisValueDifferentException;
import project.SangHyun.config.redis.RedisKey;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.domain.MemberRole;
import project.SangHyun.member.dto.request.VerifyEmailRequestDto;
import project.SangHyun.member.helper.RedisHelper;
import project.SangHyun.member.helper.email.EmailHelper;
import project.SangHyun.member.repository.MemberRepository;

import java.util.UUID;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VerifyService {

    private static final Long EMAIL_VALID_TIME = 60 * 5L;

    private final RedisHelper redisHelper;
    private final EmailHelper emailHelper;
    private final MemberRepository memberRepository;

    public String send(String email, RedisKey redisKey) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        String authCode = makeAuthCodeAndStoreInRedis(redisKey, email);
        emailHelper.sendEmail(member.getEmail(), authCode, redisKey);
        return "이메일 전송에 성공하였습니다.";
    }

    @Transactional
    public String verify(VerifyEmailRequestDto requestDto) {
        String redisKey = getRedisKey(requestDto.getRedisKey(), requestDto.getEmail());
        validateRedisValue(redisKey, requestDto.getAuthCode());
        if (isVerifyEmail(requestDto)) {
            Member member = memberRepository.findByEmail(requestDto.getEmail()).orElseThrow(MemberNotFoundException::new);
            member.changeRole(MemberRole.ROLE_MEMBER);
        }
        redisHelper.delete(getRedisKey(requestDto.getRedisKey(), requestDto.getEmail()));
        return "이메일 인증이 완료되었습니다.";
    }

    private String makeAuthCodeAndStoreInRedis(RedisKey redisKey, String email) {
        String key = getRedisKey(redisKey, email);
        String authCode = UUID.randomUUID().toString();
        redisHelper.store(key, authCode, EMAIL_VALID_TIME);
        return authCode;
    }

    private String getRedisKey(RedisKey redisKey, String email) {
        return redisKey.getKey() + email;
    }

    private void validateRedisValue(String key, String email) {
        if (isNotValidRedisValue(key, email))
            throw new RedisValueDifferentException();
    }

    private Boolean isNotValidRedisValue(String key, String email) {
        return !redisHelper.validate(key, email);
    }

    private boolean isVerifyEmail(VerifyEmailRequestDto requestDto) {
        return RedisKey.isVerifying(requestDto.getRedisKey());
    }
}
