import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;

public class BuildRequests {

    // this will allow us to read the env configuration from a file
    private static Object file;

    static {
        try {
            file = new JSONParser().parse(new FileReader("envConfig.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    static JSONObject config = (JSONObject) file;

    public HttpRequest request(String api , String query ,String parameters) throws URISyntaxException {
        String apiType = null;
        switch (api) {
            case "filterWebContent":
                apiType = "filterWebContent";
            break;
            case "cyberFilter":
                apiType="cyberFilter";
            break;
            case "reviewFilter":
                apiType = "reviewFilter";

        }
         HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(config.get("baseUrl").toString()+apiType +"?q="+query+parameters+"&token="+config.get("token").toString()))
                .GET()
                .build();
         return request;
    }
}
