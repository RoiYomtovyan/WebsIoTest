import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;

public class Requests {
    HttpClient client = HttpClient.newHttpClient();
    BuildRequests buildRequests= new BuildRequests();
    JSONObject responseJson ;

    public JSONObject sendRequest(String api, String query, String parameters) {
        try {
            HttpRequest request = buildRequests.request(api,query,parameters);
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Check if the response was successful (status code 200) - in this tests scope we are assuming we want to test only valid requests.
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