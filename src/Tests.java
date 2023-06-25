import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;

public class Tests {

    @BeforeClass
    public static void start () throws Exception {
    }

    @Test
    public void SanityTest01_verify_query_with_no_results() throws URISyntaxException {
        Requests requestToSend = new Requests();
        String api = "filterWebContent";
        String query ="(\"Roi Yomtovyan\")";
        String parameters = "language:english&format=json&sort=crawled&ts=1595156935835&highlight=true";
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        System.out.println(encodedQuery);
        JSONObject request_verify_query_with_no_results = requestToSend.sendRequest(api,encodedQuery,parameters);
        System.out.println("request_verify_query_with_no_results " + request_verify_query_with_no_results.toString());
        int totalResults = (int) request_verify_query_with_no_results.get("totalResults");
        System.out.println("Total Results: " + totalResults);
        assert totalResults == 0;


    }
}