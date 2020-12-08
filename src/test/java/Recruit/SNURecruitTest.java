package Recruit;


import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SNURecruitTest {

    private final SNURecruit recruitInstance;
    private final ArrayList<Element> list;

    SNURecruitTest() throws IOException {
        recruitInstance = new SNURecruit(new Page());

        String content = readFileInResources("SNURecruit/page");

        list = recruitInstance.getList(content);
    }

    @Test
    void testGenerateRecruitUrl() {
        assertEquals("http://www.snuh.org/about/news/recruit/recruList.do?pageIndex=1", recruitInstance.generateRecruitUrl());
        assertEquals("http://www.snuh.org/about/news/recruit/recruList.do?pageIndex=2", recruitInstance.generateRecruitUrl());
        assertEquals("http://www.snuh.org/about/news/recruit/recruList.do?pageIndex=3", recruitInstance.generateRecruitUrl());
        assertEquals("http://www.snuh.org/about/news/recruit/recruList.do?pageIndex=4", recruitInstance.generateRecruitUrl());
        assertEquals("http://www.snuh.org/about/news/recruit/recruList.do?pageIndex=5", recruitInstance.generateRecruitUrl());
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
        String subject = recruitInstance.getSubject(list.get(0));

        assertEquals("2021년도 임상강사 선발 최종 합격자 발표 및 등록 안내", subject);
    }
    
    
    private String readFileInResources(String path) throws IOException {
        Path file = Path.of("", "src/test/resources").resolve(path);
        return Files.readString(file);
    }

    private String cleanWhitespaces(String s) {
        return s.replaceAll("\\s+", "");
    }
}
