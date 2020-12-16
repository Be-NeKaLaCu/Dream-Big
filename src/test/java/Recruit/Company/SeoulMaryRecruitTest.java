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


public class SeoulMaryRecruitTest {

    private final SeoulMaryRecruit recruit;
    private final ArrayList<Element> list;

    SeoulMaryRecruitTest() throws IOException {
        recruit = new SeoulMaryRecruit();

        String content = readFileInResources("SeoulMaryRecruit/page_0");

        list = recruit.getList(content);
    }

    @Test
    void testGetJobsOnTheDayOf() throws IOException, InterruptedException {
        ArrayList<Job> jobs;

        jobs = recruit.getJobsOnTheDayOf(LocalDate.parse("2020-12-15"));
        assertEquals(1, jobs.size());
        assertEquals("외래간호직(간호행정교육팀/계약직) 공개채용", jobs.get(0).subject);
    }

    @Test
    void testGenerateRecruitUrl() {
        assertEquals("https://recruit.cmcnu.or.kr/cmcseoul/index.do?cPage=1", recruit.getRecruitPageUrl(1));
        assertEquals("https://recruit.cmcnu.or.kr/cmcseoul/index.do?cPage=2", recruit.getRecruitPageUrl(2));
        assertEquals("https://recruit.cmcnu.or.kr/cmcseoul/index.do?cPage=3", recruit.getRecruitPageUrl(3));
    }

    @Test
    void testGetList() throws IOException {
        assertEquals(
                cleanWhitespaces(readFileInResources("SeoulMaryRecruit/list_0")),
                cleanWhitespaces(list.get(0).toString())
        );
    }

    @Test
    void testGetSubject() {
        String subject = recruit.getSubject(list.get(0));

        assertEquals("외래간호직(간호행정교육팀/계약직) 공개채용", subject);
    }

    @Test
    void testGetStartDate() {
        LocalDate startDate = recruit.getStartDate(list.get(0));

        assertEquals("2020-12-15", startDate.toString());
    }

    @Test
    void testGetEndDate() {
        LocalDate endDate = recruit.getEndDate(list.get(0));

        assertEquals("2020-12-21", endDate.toString());
    }

    @Test
    void testGetLink() {
        String link = recruit.getLink(list.get(0));

        assertEquals("https://recruit.cmcnu.or.kr/cmcseoul/application/appView.do?seq_no=8782&cPage=1", link);
    }

    private String readFileInResources(String path) throws IOException {
        Path file = Path.of("", "src/test/resources").resolve(path);
        return Files.readString(file);
    }

    private String cleanWhitespaces(String s) {
        return s.replaceAll("\\s+", "");
    }
}
