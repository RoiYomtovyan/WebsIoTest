import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

public class Requests {
    HttpClient client = HttpClient.newHttpClient();
    BuildRequests buildRequests= new BuildRequests();
    JSONObject responseJson ;

    public JSONObject sendRequest(String api, String query,String parameters) {
        try {
            HttpRequest request = buildRequests.request(api,query,parameters);
//            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(new URI("https://api.webz.io/filterWebContent?q=(\"stock market\" OR NASDAQ) language:english&format=json&sort=crawled&ts=1595156935835&highlight=true&token=01a2ca30-a4d7-47cc-beaa-b9f321cae01e"))
//                    .GET()
//                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Check if the response was successful (status code 200)
            if (response.statusCode() == 200) {
                String responseBody = response.body();
                responseJson = new JSONObject(responseBody);
            } else {
                System.out.println("HTTP request failed with status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseJson;
    }
}