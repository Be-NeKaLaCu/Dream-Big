package Notification;

import Recruit.Job;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class SlackNotifierTest {
    @Test
    void testNotify() throws Exception {
        SlackNotifier sn = new SlackNotifier();

        Job job = new Job(
                "서울대학교병원",
                "2021년도 임상강사 선발 최종 합격자 발표 및 등록 안내",
                "http://www.snuh.org/about/news/recruit/recruView.do?recruit_id=20153",
                LocalDate.now(),
                LocalDate.now()
        );

        sn.notify(job);
    }
}
