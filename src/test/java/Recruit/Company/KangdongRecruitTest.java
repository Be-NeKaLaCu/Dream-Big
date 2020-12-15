package Recruit.Company;


import Recruit.Job;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class KangdongRecruitTest {

    private final KangdongRecruit recruit;
    private final ArrayList<Element> list;

    KangdongRecruitTest() throws IOException {
        recruit = new KangdongRecruit();

        String content = readFileInResources("KangdongRecruit/page_0");

        list = recruit.getList(content);
    }

    @Test
    void testGetJobsOnTheDayOf() throws IOException, InterruptedException {
        ArrayList<Job> jobs;

        jobs = recruit.getJobsOnTheDayOf(LocalDate.parse("2020-12-04"));
        assertEquals(1, jobs.size());
        assertEquals("강동성심병원 임상교원 및 임상강사 초빙", jobs.get(0).subject);

        jobs = recruit.getJobsOnTheDayOf(LocalDate.parse("2999-12-31"));
        assertEquals(0, jobs.size());

        jobs = recruit.getJobsOnTheDayOf(LocalDate.parse("1900-01-01"));
        assertEquals(0, jobs.size());
    }

    @Test
    void testGenerateRecruitUrl() {
        assertEquals("https://recruit.kdh.or.kr:446/index.jsp?inggbn=ing&movePage=1", recruit.getRecruitPageUrl(1));
        assertEquals("https://recruit.kdh.or.kr:446/index.jsp?inggbn=ing&movePage=2", recruit.getRecruitPageUrl(2));
        assertEquals("https://recruit.kdh.or.kr:446/index.jsp?inggbn=ing&movePage=3", recruit.getRecruitPageUrl(3));
        assertEquals("https://recruit.kdh.or.kr:446/index.jsp?inggbn=ing&movePage=4", recruit.getRecruitPageUrl(4));
        assertEquals("https://recruit.kdh.or.kr:446/index.jsp?inggbn=ing&movePage=5", recruit.getRecruitPageUrl(5));
    }

    @Test
    void testGetList() throws IOException {
        assertEquals(
                cleanWhitespaces(readFileInResources("KangdongRecruit/list_0")),
                cleanWhitespaces(list.get(0).toString())
        );
    }

    @Test
    void testGetSubject() {
        String subject = recruit.getSubject(list.get(0));

        assertEquals("강동성심병원 임상교원 및 임상강사 초빙", subject);
    }

    @Test
    void testGetStartDate() {
        LocalDate startDate = recruit.getStartDate(list.get(0));

        assertEquals("2020-12-04", startDate.toString());
    }

    @Test
    void testGetEndDate() {
        LocalDate endDate = recruit.getEndDate(list.get(0));

        assertEquals("2021-02-28", endDate.toString());
    }

    @Test
    void testGetLink() {
        String link = recruit.getLink(list.get(0));

        assertEquals("https://recruit.kdh.or.kr:446/hrt_p20_detail.jsp?locate=6&adoptcnt=225&inggbn=ing&movePage=1&adoptyy=2020", link);
    }

    private String readFileInResources(String path) throws IOException {
        Path file = Path.of("", "src/test/resources").resolve(path);
        return Files.readString(file);
    }

    private String cleanWhitespaces(String s) {
        return s.replaceAll("\\s+", "");
    }
}
