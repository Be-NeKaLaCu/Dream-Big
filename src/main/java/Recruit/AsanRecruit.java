package Recruit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class AsanRecruit extends RecruitAbstract<Element> {
    private final String baseRecruitUrl = "https://recruit.amc.seoul.kr/recruit/career/";

    @Override
    protected String getCompanyName() {
        return "서울아산병원";
    }

    @Override
    protected String getRecruitPage(int page) throws IOException, InterruptedException {
        return null;
    }

    protected String getRecruitPageUrl(int page) {
        return baseRecruitUrl + "list.do?pageIndex=" + page;
    }

    @Override
    protected ArrayList<Element> getList(String page) {
        Document doc = Jsoup.parse(page);

        return doc.getElementsByClass("dayListBoxLeft");
    }

    @Override
    protected String getSubject(Element item) {
        return item.getElementsByTag("a").get(0).text();
    }

    @Override
    protected String getLink(Element item) {
        String clickHandler = item.getElementsByTag("a").get(0).attr("onclick");

        String[] split = clickHandler.split("'");

        return baseRecruitUrl + "view.do?seq=" + split[1] + "&scheduleno=" + split[3];
    }

    @Override
    protected LocalDate getStartDate(Element item) {
        String date = getRawDateRange(item)[0];
        String parsableDate = makeDateParsable(date);
        return LocalDate.parse(parsableDate);
    }

    @Override
    protected LocalDate getEndDate(Element item) {
        String date = getRawDateRange(item)[1];
        String parsableDate = makeDateParsable(date);
        return LocalDate.parse(parsableDate);
    }

    String[] getRawDateRange(Element item) {
        return item.getElementsByTag("span").get(1).text().split(" ~ ");
    }

    String makeDateParsable(String date) {
        return date.split("\\(")[0].replaceAll("\\.", "-");
    }
}
