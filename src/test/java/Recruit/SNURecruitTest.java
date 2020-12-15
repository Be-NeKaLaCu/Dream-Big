package Recruit;


import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SNURecruitTest {

    private final SNURecruit snuRecruit;
    private final ArrayList<Element> list;

    SNURecruitTest() throws IOException {
        snuRecruit = new SNURecruit();

        String content = readFileInResources("SNURecruit/page_1");

        list = snuRecruit.getList(content);
    }

    @Test
    void testGetJobsOnTheDayOf() throws IOException, InterruptedException {
        ArrayList<Job> jobs;

        jobs = snuRecruit.getJobsOnTheDayOf(LocalDate.parse("2020-12-13"));
        assertEquals(2, jobs.size());
        assertEquals("완화의료임상윤리센터에서 책임감 있고 성실한 연구원을 모집합니다.", jobs.get(0).subject);
        assertEquals("2021년도 레지던트 선발 필기시험 시험실(좌석배치) 안내", jobs.get(1).subject);

        jobs = snuRecruit.getJobsOnTheDayOf(LocalDate.parse("2020-12-04"));
        assertEquals(3, jobs.size());
        assertEquals("서울대학교병원 블라인드 직원채용 (대체근로자) 공고 (장애인 특별우대)", jobs.get(0).subject);
        assertEquals("서울대학교병원 블라인드 직원채용 (단시간근로자) 공고 (장애인 특별우대)", jobs.get(1).subject);
        assertEquals("<레지던트 지원자용> 코로나-19 관련 레지던트 필기시험 진행 안내", jobs.get(2).subject);

        jobs = snuRecruit.getJobsOnTheDayOf(LocalDate.parse("2020-12-01"));
        assertEquals(0, jobs.size());
    }

    @Test
    void testGenerateRecruitUrl() {
        assertEquals("http://www.snuh.org/about/news/recruit/recruList.do?pageIndex=1", snuRecruit.getRecruitPageUrl(1));
        assertEquals("http://www.snuh.org/about/news/recruit/recruList.do?pageIndex=2", snuRecruit.getRecruitPageUrl(2));
        assertEquals("http://www.snuh.org/about/news/recruit/recruList.do?pageIndex=3", snuRecruit.getRecruitPageUrl(3));
        assertEquals("http://www.snuh.org/about/news/recruit/recruList.do?pageIndex=4", snuRecruit.getRecruitPageUrl(4));
        assertEquals("http://www.snuh.org/about/news/recruit/recruList.do?pageIndex=5", snuRecruit.getRecruitPageUrl(5));
    }

    @Test
    void testGetList() throws IOException {
        assertEquals(
                cleanWhitespaces(readFileInResources("SNURecruit/list_0")),
                cleanWhitespaces(list.get(0).toString())
        );
        assertEquals(
                cleanWhitespaces(readFileInResources("SNURecruit/list_1")),
                cleanWhitespaces(list.get(1).toString())
        );
        assertEquals(
                cleanWhitespaces(readFileInResources("SNURecruit/list_2")),
                cleanWhitespaces(list.get(2).toString())
        );
        assertEquals(
                cleanWhitespaces(readFileInResources("SNURecruit/list_3")),
                cleanWhitespaces(list.get(3).toString())
        );
        assertEquals(
                cleanWhitespaces(readFileInResources("SNURecruit/list_4")),
                cleanWhitespaces(list.get(4).toString())
        );

    }

    @Test
    void testGetSubject() {
        String subject = snuRecruit.getSubject(list.get(0));

        assertEquals("2021년도 임상강사 선발 최종 합격자 발표 및 등록 안내", subject);
    }

    @Test
    void testGetStartDate() {
        LocalDate startDate = snuRecruit.getStartDate(list.get(0));
        
        assertEquals("2020-12-08", startDate.toString());
    }

    @Test
    void testGetEndDate() {
        LocalDate endDate = snuRecruit.getEndDate(list.get(0));
        
        assertEquals("2020-12-18", endDate.toString());
    }

    @Test
    void testGetLink() {
        String link = snuRecruit.getLink(list.get(0));

        assertEquals("http://www.snuh.org/about/news/recruit/recruView.do?recruit_id=20153", link);
    }

    private String readFileInResources(String path) throws IOException {
        Path file = Path.of("", "src/test/resources").resolve(path);
        return Files.readString(file);
    }

    private String cleanWhitespaces(String s) {
        return s.replaceAll("\\s+", "");
    }
}
