package Notification;

import Recruit.Job;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class SlackNotifier implements INotifier {
    @Override
    public void notify(Job job) throws Exception {
        String url = System.getenv("SLACK_HOOK_URL");

        String message = makeMessage(job);
        var map = new HashMap<String, String>() {{
            put("text", message);
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(map);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    String makeMessage(Job job) {
        return "*<" + job.link + "|[" + job.company + "] " + job.subject + ">*\n*" + job.startDate.toString() + " ~ " + job.endDate.toString() + "*";
    }
}
