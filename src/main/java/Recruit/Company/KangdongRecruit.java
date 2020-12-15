package Recruit.Company;

import Facade.Http;
import Recruit.RecruitAbstract;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class KangdongRecruit extends RecruitAbstract<Element> {
    private final String baseRecruitUrl = "https://recruit.kdh.or.kr:446/";

    @Override
    protected String getCompanyName() {
        return "강동성심병원";
    }

    @Override
    protected String getRecruitPage(int page) throws IOException, InterruptedException {
        return Http.get(getRecruitPageUrl(page)).body();
    }

    protected String getRecruitPageUrl(int page) {
        return baseRecruitUrl + "index.jsp?inggbn=ing&movePage=" + page;
    }

    @Override
    protected ArrayList<Element> getList(String page) {
        Document doc = Jsoup.parse(page);

        return doc.getElementById("rct_list_ing").getElementsByTag("a");
    }

    @Override
    protected String getSubject(Element item) {
        return item.getElementsByTag("p").get(0).ownText();
    }

    @Override
    protected String getLink(Element item) {
        return baseRecruitUrl + item.attr("href").substring(1);
    }

    @Override
    protected LocalDate getStartDate(Element item) {
        try {
            String detailPage = Http.get(getLink(item)).body();

            Document doc = Jsoup.parse(detailPage);

            String startDate = doc.getElementById("gesi").text().split(" ")[2].replaceAll("\\.", "-");

            return LocalDate.parse(startDate);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected LocalDate getEndDate(Element item) {
        return LocalDate.parse(item.getElementsByTag("span").get(1).text().replaceAll("\\.", "-"));
    }
}
