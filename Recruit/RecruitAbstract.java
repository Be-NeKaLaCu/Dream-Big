package Recruit;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;

public abstract class RecruitAbstract {

    public ArrayList<Job> getJobs() {
        ArrayList<Job> jobs = new ArrayList<Job>();
        String page = getPage();

        for (String item : getList(page)) {
            Job job = new Job(
                getSubject(item),
                getStartDate(item),
                getEndDate(item)
            );
            jobs.add(job);
        }

        return jobs;
    }

    abstract ArrayList<String> getList(String page);
    abstract String getSubject(String item);
    abstract String getStartDate(String item);
    abstract String getEndDate(String item);

    abstract String getRecruitUrl();

    private String getPage() {
        URI uri = URI.create(getRecruitUrl());
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(uri).build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        return response.body();
    }
}
