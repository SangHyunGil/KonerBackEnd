package project.SangHyun.study.videoroom.helper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JanusRequestDto {
    private String janus;
    private String plugin;
    private String transaction;
    private String admin_secret;
    private Object request;

    public static JanusRequestDto create(String transaction, String adminSecret, Object requestDto) {
        return new JanusRequestDto("message_plugin", "janus.plugin.videoroom", transaction, adminSecret, requestDto);
    }
}
