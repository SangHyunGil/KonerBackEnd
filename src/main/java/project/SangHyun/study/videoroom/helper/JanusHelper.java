package project.SangHyun.study.videoroom.helper;

import com.google.gson.Gson;
import com.google.gson.internal.Primitives;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import project.SangHyun.common.advice.exception.JanusRequestException;
import project.SangHyun.study.videoroom.helper.dto.JanusRequestDto;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class JanusHelper {

    private final Integer RANDOM_NUMBER_DIGIT = 12;

    private final RestTemplate restTemplate;
    private final Gson gson;
    @Value("${janus.server}")
    private String janusServer;
    @Value("${janus.admin.secret}")
    private String adminSecret;

    public <T> T postAndGetResponseDto(Object requestDto, Class<T> classOfT) {
        JanusRequestDto janusRequest = JanusRequestDto.create(makeRandomNumber(RANDOM_NUMBER_DIGIT), adminSecret, requestDto);
        String json = gson.toJson(janusRequest);
        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(janusServer, json, String.class);
            return Primitives.wrap(classOfT).cast(gson.fromJson(responseEntity.getBody(), classOfT));
        } catch (Exception e) {
            throw new JanusRequestException();
        }
    }

    private String makeRandomNumber(int n) {
        Random rand = new Random();
        String rst = Integer.toString(rand.nextInt(10));
        for(int i=0; i < n-1; i++){
            rst += Integer.toString(rand.nextInt(10));
        }
        return rst;
    }
}
