package Recruit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;

public class SNURecruit extends RecruitAbstract<Element> {

    private final String baseRecruitUrl = "http://www.snuh.org/about/news/recruit";

    @Override
    protected String getRecruitPage(int page) throws IOException, InterruptedException {
        String recruitUrl = getRecruitPageUrl(page);

        URI uri = URI.create(recruitUrl);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(uri).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    String getRecruitPageUrl(int page) {
        return baseRecruitUrl + "/recruList.do?pageIndex=" + page;
    }

    @Override
    protected ArrayList<Element> getList(String page) {
        Document doc = Jsoup.parse(page);

        return doc.getElementsByClass("boardTypeTbl").get(0)
                .getElementsByTag("tbody").get(0)
                .getElementsByTag("tr");
    }

    @Override
    protected String getSubject(Element item) {
        return item.getElementsByClass("alignL").get(0).text();
    }

    @Override
    protected String getLink(Element item) {
        return baseRecruitUrl + item.getElementsByTag("a").get(0).attr("href").substring(1);
    }

    @Override
    protected LocalDate getStartDate(Element item) {
        String[] dateRange = getDateRange(item);
        return LocalDate.parse(dateRange[0]);
    }

    @Override
    protected LocalDate getEndDate(Element item) {
        String[] dateRange = getDateRange(item);
        return LocalDate.parse(dateRange[1]);
    }

    String[] getDateRange(Element item) {
        Element dateElement = item.getElementsByTag("td").get(2);
        return dateElement.text().split(" ~ ");
    }
}
