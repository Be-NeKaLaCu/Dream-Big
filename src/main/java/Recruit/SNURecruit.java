package Recruit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;

public class SNURecruit extends RecruitAbstract<Element> {

    private int pageIndex = 0;

    SNURecruit(Page page) {
        super(page);
    }

    @Override
    protected final String generateRecruitUrl() {
        pageIndex ++;
        return "http://www.snuh.org/about/news/recruit/recruList.do?pageIndex=" + pageIndex;
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
    protected final String getStartDate(Element item) {
        return null;
    }

    @Override
    protected final String getEndDate(Element item) {
        return null;
    }
}
