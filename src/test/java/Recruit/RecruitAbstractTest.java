package Recruit;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecruitAbstractTest {

    @Test
    void testGetJobsOnTheDayOf() throws IOException, InterruptedException {
        RecruitAbstract<?> mockRecruit = new MockRecruit();

        ArrayList<Job> jobs = mockRecruit.getJobsOnTheDayOf(LocalDate.parse("2020-12-07"));
        assertEquals(2, jobs.size());
        assertEquals("2020년도 간호직 블라인드 경력 채용 서류전형 합격자 발표 및 실무면접 일정 공고", jobs.get(0).subject);
        assertEquals("진료교수 공개채용", jobs.get(1).subject);

        jobs = mockRecruit.getJobsOnTheDayOf(LocalDate.parse("2020-11-30"));
        assertEquals(4, jobs.size());
        assertEquals("외상외과 임상교수 채용", jobs.get(0).subject);
        assertEquals("2020년도 11월 대체근로자 채용 실무면접 합격자 발표 및 신체검사 일정공고", jobs.get(1).subject);
        assertEquals("2021년도 레지던트 모집 공고", jobs.get(2).subject);
        assertEquals("2021년도 레지던트 지원현황(최종)", jobs.get(3).subject);

        jobs = mockRecruit.getJobsOnTheDayOf(LocalDate.parse("2020-09-16"));
        assertEquals(2, jobs.size());
        assertEquals("2020년도 약무직 채용 최종면접 합격자 발표 및 임용후보자 등록 공고", jobs.get(0).subject);
        assertEquals("2020년도 9월 대체근로자 채용 서류전형 합격자 발표 및 실무면접 일정공고", jobs.get(1).subject);

        jobs = mockRecruit.getJobsOnTheDayOf(LocalDate.parse("1900-01-01"));
        assertEquals(0, jobs.size());
    }
}
