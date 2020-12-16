package Recruit.Company;

import Facade.Http;
import Recruit.RecruitAbstract;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class SeoulMaryRecruit extends RecruitAbstract<Element> {
    private final String baseRecruitUrl = "https://recruit.cmcnu.or.kr/";

    @Override
    protected String getCompanyName() {
        return "서울성심병원";
    }

    @Override
    protected String getRecruitPage(int page) throws IOException, InterruptedException {
        return Http.get(getRecruitPageUrl(page)).body();
    }

    protected String getRecruitPageUrl(int page) {
        return baseRecruitUrl + "cmcseoul/index.do?cPage=" + page;
    }

    @Override
    protected ArrayList<Element> getList(String page) {
        Document doc = Jsoup.parse(page);

        return doc.getElementsByClass("items clearfix").get(0).getElementsByTag("li");
    }

    @Override
    protected String getSubject(Element item) {
        return item.getElementsByTag("strong").get(0).text().split("]")[1].trim();
    }

    @Override
    protected String getLink(Element item) {
        return baseRecruitUrl + item.getElementsByTag("a").get(0).attr("href").substring(1);
    }

    @Override
    protected LocalDate getStartDate(Element item) {
        String date = item.getElementsByClass("data").get(0).text();

        return LocalDate.parse(date);
    }

    @Override
    protected LocalDate getEndDate(Element item) {
        try {
            String detailPage = Http.get(getLink(item)).body();

            Document doc = Jsoup.parse(detailPage);

            String dateRange = doc.getElementsContainingOwnText("접수기간").next().get(0).text();
            String endDate = dateRange.split(" ")[2];

            return LocalDate.parse(endDate);
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }
}
