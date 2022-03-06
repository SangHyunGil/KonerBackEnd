package project.SangHyun.notification.domain;

public enum NotificationType {
    APPLY("에 스터디 신청이 도착했습니다.", "/study/"), ACCEPT("의 스터디 신청이 승인되었습니다.", "/study/"),
    REJECT("의 스터디 신청이 거절되었습니다.", "/study/"), REPLY("에 댓글이 달렸습니다.", "/"),
    CHANGE_AUTHORITY("의 권한이 변경되었습니다.", "/study/"), MESSAGE("로 부터 메세지가 도착했습니다.", "/mail/with/");

    private String content;
    private String url;

    NotificationType(String content, String url) {
        this.content = content;
        this.url = url;
    }

    public String makeContent(String title) {
        return "'" + title + "'" + content;
    }

    public String makeUrl(Long id) {
        return url + id;
    }
}
