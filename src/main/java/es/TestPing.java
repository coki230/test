package es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public class TestPing {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("10.9.9.70", 9200, "http")));

        DeleteRequest deleteRequest = new DeleteRequest();
        deleteRequest.index("all_p90_month");
        deleteRequest.id("V80O7FAUR3iJjdJro4ufUQ");
        deleteRequest.type("type");
        client.delete(deleteRequest);

        client.close();
    }
}
