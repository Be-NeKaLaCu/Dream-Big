package Recruit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.time.LocalDate;
import java.util.ArrayList;

public class SNURecruit extends RecruitAbstract<Element> {

    private int pageIndex = 0;
    private final String baseRecruitUrl = "http://www.snuh.org/about/news/recruit";

    SNURecruit(Page page) {
        super(page);
    }

    @Override
    protected final String generateNextRecruitUrl() {
        pageIndex ++;
        return baseRecruitUrl + "/recruList.do?pageIndex=" + pageIndex;
    }

    @Override
    protected final ArrayList<Element> getList(String page) {
        Document doc = Jsoup.parse(page);

        return doc.getElementsByClass("boardTypeTbl").get(0)
                .getElementsByTag("tbody").get(0)
                .getElementsByTag("tr");
    }

    @Override
    protected final String getSubject(Element item) {
        return item.getElementsByClass("alignL").get(0).text();
    }

    @Override
    protected String getLink(Element item) {
        return baseRecruitUrl + item.getElementsByTag("a").get(0).attr("href").substring(1);
    }

    @Override
    protected final LocalDate getStartDate(Element item) {
        String[] dateRange = getDateRange(item);
        return LocalDate.parse(dateRange[0]);
    }

    @Override
    protected final LocalDate getEndDate(Element item) {
        String[] dateRange = getDateRange(item);
        return LocalDate.parse(dateRange[1]);
    }

    private String[] getDateRange(Element item) {
        Element dateElement = item.getElementsByTag("td").get(2);
        return dateElement.text().split(" ~ ");
    }
}
