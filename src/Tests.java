import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.BeforeClass;
import org.junit.Test;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.net.URISyntaxException;

public class Tests {
    Requests requestToSend = new Requests();
    JSONParser parser = new JSONParser();

    @BeforeClass
    public static void start () throws Exception {
    }

    @Test
    /*
     this test sending query that should return no results and verify there is no error and a valid response is returned:
     test description:
     1. send request with search parameters that should return 0 total results.
     2. verify that the response status code is 200 (its verified in the "Requests" class for each request we are sending)
     3. verify that the total results value in the response is 0
     */
    public void SanityTest01_verify_query_with_no_results() throws URISyntaxException {
        String api = "filterWebContent";
        String query ="(\"Roi Yomtovyan\")";
        String parameters = "language:english&format=json&sort=crawled&ts=1595156935835&highlight=true";
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8); // need it in order to be able to send the request with space character and parenthesis
        org.json.JSONObject request_verify_query_with_no_results = requestToSend.sendRequest(api,encodedQuery,parameters);
        System.out.println("request_verify_query_with_no_results " + request_verify_query_with_no_results.toString());
        int totalResults = (int) request_verify_query_with_no_results.get("totalResults");
        System.out.println("Total Results: " + totalResults);
        assert totalResults == 0;


    }

    @Test
    /*
     this test verifying the calculation of moreResultsAvailable in the response:
     test description:
     1. send a valid request and get the response.
     2. get the "posts" json array size to verify the number of posts displayed in the response.
     3. get the total results parameter value from the results.
     4. calculate the moreResultsAvailable value by subtraction of the posts array size from the total results.
     5. compare the result you get in step 4 to the moreResultsAvailable parameter in the response.
     */
    public void SanityTest02_validate_moreResultsAvailable_value() throws URISyntaxException, ParseException {

        String api = "filterWebContent";
        String query ="((\"stock market\" OR NASDAQ) )";
        String parameters = "language:english&format=json&sort=crawled&ts=1595156935835&highlight=true";
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8); // need it in order to be able to send the request with space character and parenthesis
        System.out.println(encodedQuery);
        org.json.JSONObject request_to_verify_totalResults_in_response = requestToSend.sendRequest(api,encodedQuery,parameters);
        System.out.println("request_to_verify_totalResults_in_response " + request_to_verify_totalResults_in_response.toString());
        JSONObject jsonObject = (JSONObject) parser.parse(request_to_verify_totalResults_in_response.toString());
        JSONArray threadsArray = (JSONArray) jsonObject.get("posts");
        int threadCount = threadsArray.size();
        System.out.println("Total Results in posts array: " + threadCount);
        int totalResults = (int) request_to_verify_totalResults_in_response.get("totalResults");
        System.out.println("Total Results value: " + totalResults);
        int moreResultsAvailable = (int) request_to_verify_totalResults_in_response.get("moreResultsAvailable");
        System.out.println("More Result Available value: " + moreResultsAvailable);
        int totalMoreSubtraction = totalResults-moreResultsAvailable;
        assert totalMoreSubtraction == threadCount;


    }
    @Test
     /*
     This test verifying the calculation of requestsLeft:
     assuming each API key has limited available requests amount.
     This test should run with a dedicated API key, so it won't be influenced by another running tests      description:
     1. send a request with a specific API key value and get the response.
     2. get the requestsLeft value from the response.
     3. send another request with the same API key value and get the response.
     4. get the requestsLeft value from the response of step 3.
     5. verify the subscription of the value you get in step 2 from the value you get in step 4 is 1
     */
    public void SanityTest03_validate_requestsLeft_value() throws URISyntaxException, ParseException {
        String api = "filterWebContent";
        String query ="((\"stock market\" OR NASDAQ) )";
        String parameters = "language:english&format=json&sort=crawled&ts=1595156935835&highlight=true";
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        org.json.JSONObject request_verify_requestsLeft_number1 = requestToSend.sendRequest(api,encodedQuery,parameters);
        int requestsLeft = (int) request_verify_requestsLeft_number1.get("requestsLeft");
        System.out.println("TrequestsLeft: " + requestsLeft);
        org.json.JSONObject request_verify_requestsLeft_number2 = requestToSend.sendRequest(api,encodedQuery,parameters);
        int requestsLeftUpdated = (int) request_verify_requestsLeft_number2.get("requestsLeft");
        System.out.println("TrequestsLeftUpdated: " + requestsLeftUpdated);
        assert requestsLeft - requestsLeftUpdated == 1;

        }
    }
