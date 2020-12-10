package Recruit;


import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SamsungRecruitTest {

    private final SamsungRecruit recruit;
    private final ArrayList<Element> list;

    SamsungRecruitTest() throws IOException {
        recruit = new SamsungRecruit();

        String content = readFileInResources("SamsungRecruit/page_0");

        list = recruit.getList(content);
    }

    @Test
    void testGenerateRecruitUrl() {
        assertEquals("https://www.samsunghospital.com/home/recruit/recruitInfo/recruitNotice.do?cPage=1", recruit.getRecruitPageUrl(1));
        assertEquals("https://www.samsunghospital.com/home/recruit/recruitInfo/recruitNotice.do?cPage=2", recruit.getRecruitPageUrl(2));
        assertEquals("https://www.samsunghospital.com/home/recruit/recruitInfo/recruitNotice.do?cPage=3", recruit.getRecruitPageUrl(3));
        assertEquals("https://www.samsunghospital.com/home/recruit/recruitInfo/recruitNotice.do?cPage=4", recruit.getRecruitPageUrl(4));
        assertEquals("https://www.samsunghospital.com/home/recruit/recruitInfo/recruitNotice.do?cPage=5", recruit.getRecruitPageUrl(5));
    }

    @Test
    void testGetList() throws IOException {
        assertEquals(
                cleanWhitespaces(readFileInResources("SamsungRecruit/list_0")),
                cleanWhitespaces(list.get(0).toString())
        );
        assertEquals(
                cleanWhitespaces(readFileInResources("SamsungRecruit/list_1")),
                cleanWhitespaces(list.get(1).toString())
        );
        assertEquals(
                cleanWhitespaces(readFileInResources("SamsungRecruit/list_2")),
                cleanWhitespaces(list.get(2).toString())
        );
        assertEquals(
                cleanWhitespaces(readFileInResources("SamsungRecruit/list_3")),
                cleanWhitespaces(list.get(3).toString())
        );
        assertEquals(
                cleanWhitespaces(readFileInResources("SamsungRecruit/list_4")),
                cleanWhitespaces(list.get(4).toString())
        );

    }

    @Test
    void testGetSubject() {
        String subject = recruit.getSubject(list.get(0));

        assertEquals("응급구조학과 계약직 응급구조사 채용", subject);
    }

    @Test
    void testGetStartDate() {
        LocalDate startDate = recruit.getStartDate(list.get(0));

        assertEquals("2020-12-03", startDate.toString());
    }

    @Test
    void testGetEndDate() {
        LocalDate endDate = recruit.getEndDate(list.get(0));

        assertEquals("2020-12-17", endDate.toString());
    }

    @Test
    void testGetLink() {
        String link = recruit.getLink(list.get(0));

        assertEquals("https://www.samsunghospital.com//home/recruit/recruitInfo/recruitNoticeView.do?RECRUIT_CD=2020HACD0001&cPage=2", link);
    }

    private String readFileInResources(String path) throws IOException {
        Path file = Path.of("", "src/test/resources").resolve(path);
        return Files.readString(file);
    }

    private String cleanWhitespaces(String s) {
        return s.replaceAll("\\s+", "");
    }
}
