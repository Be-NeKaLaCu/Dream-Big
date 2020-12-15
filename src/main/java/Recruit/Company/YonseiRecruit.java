package Recruit.Company;

import java.io.IOException;

import java.time.LocalDate;
import java.util.HashMap;

import Recruit.RecruitAbstract;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import Facade.Http;

public class YonseiRecruit extends RecruitAbstract<JsonElement> {
    private final String baseRecruitUrl = "https://yuhs.recruiter.co.kr/app/jobnotice";

    @Override
    protected String getRecruitPage(int page) throws IOException, InterruptedException {
        String recruitUrl = baseRecruitUrl + "/list.json";

        HashMap<String, String> map = new HashMap<>();
        map.put("recruitClassSn", "");
        map.put("recruitClassName", "");
        map.put("jobnoticeStateCode", "10");
        map.put("pageSize", "10");
        map.put("currentPage", Integer.toString(page));

        return Http.postFormData(recruitUrl, map).body();
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
