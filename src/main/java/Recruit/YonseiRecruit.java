package Recruit;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


public class YonseiRecruit extends RecruitAbstract<JsonElement> {
    private final String baseRecruitUrl = "https://yuhs.recruiter.co.kr/app/jobnotice";

    @Override
    protected String getRecruitPage(int page) throws IOException, InterruptedException {
        String recruitUrl = baseRecruitUrl + "/list.json";
        String requestBody = "recruitClassSn=&recruitClassName=&jobnoticeStateCode=10&pageSize=10&currentPage=" + page;

        URI uri = URI.create(recruitUrl);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(uri)
                    .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
    
    @Override
    protected JsonArray getList(String page) {
        JsonElement doc = JsonParser.parseString(page);
        return doc.getAsJsonObject().getAsJsonArray("list");
    }

    @Override
    protected String getSubject(JsonElement item) {
        return item.getAsJsonObject().get("jobnoticeName").getAsString();
    }

    @Override
    protected String getLink(JsonElement item) {
        return baseRecruitUrl + "/view?systemKindCode=MRS2&jobnoticeSn=" + item.getAsJsonObject().get("jobnoticeSn").toString();
    }

    @Override
    protected String getCompanyName() {
        return "연세대학병원";
    }

    @Override
    protected LocalDate getStartDate(JsonElement item) {
        JsonElement startDate = item.getAsJsonObject().get("applyStartDate");
        return getDate(startDate);
    }

    @Override
    protected LocalDate getEndDate(JsonElement item) {
        JsonElement endDate = item.getAsJsonObject().get("applyEndDate");
        return getDate(endDate);
    }

    private LocalDate getDate(JsonElement dateElement) {
        return LocalDate.of(
            dateElement.getAsJsonObject().get("year").getAsInt() + 1900, 
            dateElement.getAsJsonObject().get("month").getAsInt() + 1, 
            dateElement.getAsJsonObject().get("date").getAsInt()
        ); 
    }
    
}
