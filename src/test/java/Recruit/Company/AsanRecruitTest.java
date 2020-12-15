package Recruit.Company;


import Recruit.Company.AsanRecruit;
import Recruit.Job;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AsanRecruitTest {

    private final AsanRecruit asanRecruit;
    private final ArrayList<Element> list;

    AsanRecruitTest() throws IOException {
        asanRecruit = new AsanRecruit();

        String content = readFileInResources("AsanRecruit/page_1");

        list = asanRecruit.getList(content);
    }

    @Test
    void testGetJobsOnTheDayOf() throws IOException, InterruptedException {
        ArrayList<Job> jobs;

        jobs = asanRecruit.getJobsOnTheDayOf(LocalDate.parse("2020-12-10"));
        assertEquals(2, jobs.size());
        assertEquals("간호부 간호간병통합병동지원인력 기능직(조무) 모집", jobs.get(0).subject);
        assertEquals("관리부 기능직(조무 - 발열모니터링지원) 모집", jobs.get(0).subject);
    }


    @Test
    void testGenerateRecruitUrl() {
        assertEquals("https://recruit.amc.seoul.kr/recruit/career/list.do?pageIndex=1", asanRecruit.getRecruitPageUrl(1));
        assertEquals("https://recruit.amc.seoul.kr/recruit/career/list.do?pageIndex=2", asanRecruit.getRecruitPageUrl(2));
        assertEquals("https://recruit.amc.seoul.kr/recruit/career/list.do?pageIndex=3", asanRecruit.getRecruitPageUrl(3));
        assertEquals("https://recruit.amc.seoul.kr/recruit/career/list.do?pageIndex=4", asanRecruit.getRecruitPageUrl(4));
        assertEquals("https://recruit.amc.seoul.kr/recruit/career/list.do?pageIndex=5", asanRecruit.getRecruitPageUrl(5));
    }

    @Test
    void testGetList() throws IOException {
        assertEquals(
                cleanWhitespaces(readFileInResources("AsanRecruit/list_0")),
                cleanWhitespaces(list.get(0).toString())
        );
        assertEquals(
                cleanWhitespaces(readFileInResources("AsanRecruit/list_1")),
                cleanWhitespaces(list.get(1).toString())
        );
        assertEquals(
                cleanWhitespaces(readFileInResources("AsanRecruit/list_2")),
                cleanWhitespaces(list.get(2).toString())
        );
        assertEquals(
                cleanWhitespaces(readFileInResources("AsanRecruit/list_3")),
                cleanWhitespaces(list.get(3).toString())
        );
        assertEquals(
                cleanWhitespaces(readFileInResources("AsanRecruit/list_4")),
                cleanWhitespaces(list.get(4).toString())
        );

    }

    @Test
    void testGetSubject() {
        String subject = asanRecruit.getSubject(list.get(0));

        assertEquals("간호부 간호간병통합병동지원인력 기능직(조무) 모집", subject);
    }

    @Test
    void testGetStartDate() {
        LocalDate startDate = asanRecruit.getStartDate(list.get(0));

        assertEquals("2020-12-10", startDate.toString());
    }

    @Test
    void testGetEndDate() {
        LocalDate endDate = asanRecruit.getEndDate(list.get(0));

        assertEquals("2020-12-15", endDate.toString());
    }

    @Test
    void testGetLink() {
        String link = asanRecruit.getLink(list.get(0));

        assertEquals("https://recruit.amc.seoul.kr/recruit/career/view.do?seq=2582&scheduleno=20200342", link);
    }

    private String readFileInResources(String path) throws IOException {
        Path file = Path.of("", "src/test/resources").resolve(path);
        return Files.readString(file);
    }

    private String cleanWhitespaces(String s) {
        return s.replaceAll("\\s+", "");
    }
}
