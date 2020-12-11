package Facade;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

import com.google.gson.Gson;

public class Http {
    
    public static HttpResponse<String> get(String url) throws IOException, InterruptedException {
        URI uri = URI.create(url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(uri).build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public static HttpResponse<String> postFormData(String url, HashMap<String, String> map) throws IOException, InterruptedException {
        String  formData = "";

        for (var pair: map.entrySet()) {
            formData += pair.getKey() + "=" + pair.getValue() + "&";
        }

        URI uri = URI.create(url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(uri)
                    .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                    .POST(HttpRequest.BodyPublishers.ofString(formData))
                    .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public static HttpResponse<String> postJson(String url, HashMap<String, String> map) throws IOException, InterruptedException {
        String json = new Gson().toJson(map);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
