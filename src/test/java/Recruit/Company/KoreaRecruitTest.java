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


public class KoreaRecruitTest {

    private final KoreaRecruit recruit;
    private final ArrayList<Element> list;

    KoreaRecruitTest() throws IOException {
        recruit = new KoreaRecruit();

        String content = readFileInResources("KoreaRecruit/page_0");

        list = recruit.getList(content);
    }

    @Test
    void testGetJobsOnTheDayOf() throws IOException, InterruptedException {
        ArrayList<Job> jobs;

        jobs = recruit.getJobsOnTheDayOf(LocalDate.parse("2020-12-14"));
        assertEquals(4, jobs.size());
        assertEquals("[의료원]신규 교직원(정밀의학센터 임상병리사) 모집(연장)", jobs.get(0).subject);
        assertEquals("[안암병원]간호부 중환자실 시간제 일반업무원(재공고)", jobs.get(1).subject);
        assertEquals("[안암병원]간호부 PRN 시간제 일반업무원(재공고)", jobs.get(2).subject);
        assertEquals("[구로병원]간호부(간호간병병동) 계약직간호조무사 모집", jobs.get(3).subject);

        jobs = recruit.getJobsOnTheDayOf(LocalDate.parse("2020-12-10"));
        assertEquals(6, jobs.size());
        assertEquals("[의료원]신규 교직원(흉부외과 전담간호사) 모집", jobs.get(0).subject);
        assertEquals("[원내모집]구로병원 감염관리실", jobs.get(1).subject);
        assertEquals("[안산병원] 원무팀 계약직직원 모집", jobs.get(2).subject);
        assertEquals("[안암병원]간호부 19병동(COVID환자 치료병동) 시간제 일반업무원(재공고)", jobs.get(3).subject);
        assertEquals("[구로병원]간호부 계약직일반업무원 모집", jobs.get(4).subject);
        assertEquals("[구로병원]원무팀 계약직직원 모집", jobs.get(5).subject);

        jobs = recruit.getJobsOnTheDayOf(LocalDate.parse("2999-12-31"));
        assertEquals(0, jobs.size());

        jobs = recruit.getJobsOnTheDayOf(LocalDate.parse("1900-01-01"));
        assertEquals(0, jobs.size());
    }

    @Test
    void testGenerateRecruitUrl() {
        assertEquals("https://recruit2.kumc.or.kr/jobs/default.aspx?SchText=&departmentidx=0&page=1", recruit.getRecruitPageUrl(1));
        assertEquals("https://recruit2.kumc.or.kr/jobs/default.aspx?SchText=&departmentidx=0&page=2", recruit.getRecruitPageUrl(2));
        assertEquals("https://recruit2.kumc.or.kr/jobs/default.aspx?SchText=&departmentidx=0&page=3", recruit.getRecruitPageUrl(3));
        assertEquals("https://recruit2.kumc.or.kr/jobs/default.aspx?SchText=&departmentidx=0&page=4", recruit.getRecruitPageUrl(4));
        assertEquals("https://recruit2.kumc.or.kr/jobs/default.aspx?SchText=&departmentidx=0&page=5", recruit.getRecruitPageUrl(5));
    }

    @Test
    void testGetList() throws IOException {
        assertEquals(
                cleanWhitespaces(readFileInResources("KoreaRecruit/list_0")),
                cleanWhitespaces(list.get(0).toString())
        );
        assertEquals(
                cleanWhitespaces(readFileInResources("KoreaRecruit/list_1")),
                cleanWhitespaces(list.get(1).toString())
        );

    }

    @Test
    void testGetSubject() {
        String subject = recruit.getSubject(list.get(0));

        assertEquals("[안산병원] 호흡기내과 계약직간호사(결핵관리 전담) 모집", subject);
    }

    @Test
    void testGetStartDate() {
        LocalDate startDate = recruit.getStartDate(list.get(0));

        assertEquals("2020-12-15", startDate.toString());
    }

    @Test
    void testGetEndDate() {
        LocalDate endDate = recruit.getEndDate(list.get(0));

        assertEquals("2021-02-28", endDate.toString());
    }

    @Test
    void testGetLink() {
        String link = recruit.getLink(list.get(0));

        assertEquals("https://recruit2.kumc.or.kr/jobs/email/view.aspx?jidx=1946197&pidx=1946197", link);
    }

    private String readFileInResources(String path) throws IOException {
        Path file = Path.of("", "src/test/resources").resolve(path);
        return Files.readString(file);
    }

    private String cleanWhitespaces(String s) {
        return s.replaceAll("\\s+", "");
    }
}
