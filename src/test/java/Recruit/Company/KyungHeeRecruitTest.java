package Recruit.Company;

import Recruit.Job;
import com.google.gson.JsonArray;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KyungHeeRecruitTest {
    private final KyungHeeRecruit recruit;
    private final JsonArray list;

    KyungHeeRecruitTest() throws IOException {
        recruit = new KyungHeeRecruit();

        String content = readFileInResources("KyungHeeRecruit/page_0");
        list = recruit.getList(content);
    }

    @Test
    void testGetJobsOnTheDayOf() throws IOException, InterruptedException {
        ArrayList<Job> jobs;

        jobs = recruit.getJobsOnTheDayOf(LocalDate.parse("2020-12-14"));
        assertEquals(2, jobs.size());
        assertEquals("계약직 신입직원 [영양팀 조리원] 공개채용 모집", jobs.get(0).subject);
        assertEquals("정규직 신입직원 [뇌신경검사실 임상병리사] 공개채용 모집", jobs.get(1).subject);

        jobs = recruit.getJobsOnTheDayOf(LocalDate.parse("2020-12-10"));
        assertEquals(6, jobs.size());
        assertEquals("계약직 신입직원 [조제팀 약사] 공개채용 모집공고", jobs.get(0).subject);
        assertEquals("계약직 신입직원 [의료협력팀 진료협력파트 상근직 간호사] 공개채용 모집 공고", jobs.get(1).subject);
        assertEquals("원무팀 사무직 신입직원(계약직) 공개채용 모집", jobs.get(2).subject);
        assertEquals("정규직 신입직원 [방사선종양학과 의학물리전문인] 공개채용 모집", jobs.get(3).subject);
        assertEquals("정규직 신입직원 [재활의학과 물리치료사] 공개채용 모집", jobs.get(4).subject);
        assertEquals("단기 계약직 신입직원 [진단검사의학과 임상병리사(선별진료소 근무)] 공개채용 모집", jobs.get(5).subject);

        jobs = recruit.getJobsOnTheDayOf(LocalDate.parse("1900-01-01"));
        assertEquals(0, jobs.size());
    }

    @Test
    void testGetList() throws IOException {
        assertEquals(
            cleanWhitespaces(readFileInResources("KyungHeeRecruit/list_0")),
            cleanWhitespaces(list.get(0).toString())
        );

        assertEquals(
            cleanWhitespaces(readFileInResources("KyungHeeRecruit/list_1")),
            cleanWhitespaces(list.get(1).toString())
        );
    }

    @Test
    void testGetCompanyName() {
        assertEquals("경희대학교병원", recruit.getCompanyName());
    }

    @Test
    void testGetLink() {
        assertEquals("https://khmc.recruiter.co.kr/app/jobnotice/view?systemKindCode=MRS1&jobnoticeSn=24956", recruit.getLink(list.get(0)));
    }

    @Test
    void testGetSubject() {
        assertEquals("단기 계약직 신입직원 [진단검사의학과 임상병리사(선별진료소 근무)] 공개채용 모집", recruit.getSubject(list.get(0)));
    }

    @Test
    void testGetStartDate() {
        LocalDate startDate = recruit.getStartDate(list.get(0));
        assertEquals("2020-12-10", startDate.toString());
    }

    @Test
    void testGetEndDate() {
        LocalDate endDate = recruit.getEndDate(list.get(0));
        assertEquals("2020-12-14", endDate.toString());
    }

    private String readFileInResources(String path) throws IOException {
        Path file = Path.of("", "src/test/resources").resolve(path);
        return Files.readString(file);
    }

    private String cleanWhitespaces(String s) {
        return s.replaceAll("\\s+", "");
    }
}
