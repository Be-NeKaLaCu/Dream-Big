package Recruit.Company;

import Recruit.RecruitAbstract;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import Facade.Http;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class SamsungRecruit extends RecruitAbstract<Element> {
    private final String baseRecruitUrl = "https://www.samsunghospital.com/";

    @Override
    protected String getCompanyName() {
        return "삼성서울병원";
    }

    @Override
    protected String getRecruitPage(int page) throws IOException, InterruptedException {
        return Http.get(getRecruitPageUrl(page)).body();
    }

    protected String getRecruitPageUrl(int page) {
        return baseRecruitUrl + "home/recruit/recruitInfo/recruitNotice.do?cPage=" + page;
    }

    @Override
    protected ArrayList<Element> getList(String page) {
        Document doc = Jsoup.parse(page);

        return doc.getElementsByClass("table-default board-list").get(0)
                .getElementsByTag("tbody").get(0)
                .getElementsByTag("tr");
    }

    @Override
    protected String getSubject(Element item) {
        return item.getElementsByClass("text-left").get(0).text();
    }

    @Override
    protected String getLink(Element item) {
        return baseRecruitUrl + item.getElementsByTag("a").get(0).attr("href");
    }

    @Override
    protected LocalDate getStartDate(Element item) {
        String[] dateRange = item.getElementsByTag("td").get(4).text().strip().split(" ~ ");

        return LocalDate.parse(dateRange[0].replaceAll("\\.", "-"));
    }

    @Override
    protected LocalDate getEndDate(Element item) {
        String[] dateRange = item.getElementsByTag("td").get(4).text().strip().split(" ~ ");

        return LocalDate.parse(dateRange[1].replaceAll("\\.", "-"));
    }
}
