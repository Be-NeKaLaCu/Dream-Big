package Recruit.Company;

import Recruit.RecruitAbstract;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import Facade.Http;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class KoreaRecruit extends RecruitAbstract<Element> {
    private final String baseRecruitUrl = "https://recruit2.kumc.or.kr/";

    @Override
    protected String getCompanyName() {
        return "고려대학교병원";
    }

    @Override
    protected String getRecruitPage(int page) throws IOException, InterruptedException {
        return Http.get(getRecruitPageUrl(page)).body();
    }

    protected String getRecruitPageUrl(int page) {
        return baseRecruitUrl + "jobs/default.aspx?SchText=&departmentidx=0&page=" + page;
    }

    @Override
    protected ArrayList<Element> getList(String page) {
        Document doc = Jsoup.parse(page);

        return doc.getElementsByClass("job_title");
    }

    @Override
    protected String getSubject(Element item) {
        return item.getElementsByClass("subject").get(0).text();
    }

    @Override
    protected String getLink(Element item) {
        return baseRecruitUrl + item.getElementsByTag("a").get(0).attr("href").substring(1);
    }

    @Override
    protected LocalDate getStartDate(Element item) {
        String[] dateRange = item.getElementsByTag("td").get(2).text().split(" ");

        return LocalDate.parse("20" + dateRange[0].replaceAll("\\.", "-"));
    }

    @Override
    protected LocalDate getEndDate(Element item) {
        String[] dateRange = item.getElementsByTag("td").get(2).text().split(" ");

        return LocalDate.parse("20" + dateRange[3].replaceAll("\\.", "-"));
    }
}
