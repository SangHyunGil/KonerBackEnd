package project.SangHyun.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.member.helper.RedisHelper;
import project.SangHyun.study.videoroom.helper.JanusHelper;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class HelperTest {

    @Autowired
    JanusHelper janusHelper;

    @Autowired
    RedisHelper redisHelper;
}
