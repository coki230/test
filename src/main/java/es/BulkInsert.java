package es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class BulkInsert {
    public static void main(String[] args) {
        BulkInsert bulkInsert = new BulkInsert();
        BulkRequest request = new BulkRequest();
        IndexRequest in1 = new IndexRequest();
        in1.id("1");
        in1.type("type");
        in1.index("test");
        Map<String, String> souce = new HashMap<>();
        souce.put("age", "3");
        souce.put("name", "lucy");
        in1.source(souce);
        IndexRequest in2 = new IndexRequest();
        in2.id("1");
        in2.type("type");
        in2.index("test");
        Map<String, String> souce2 = new HashMap<>();
        souce2.put("age", "34");
        souce2.put("name", "lucy");
        in2.source(souce2);
        request.add(in1);
        request.add(in2);
        bulkInsert.synchronousBulk(request);

    }
    public void synchronousBulk(BulkRequest request) {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));

        request.timeout(TimeValue.timeValueMinutes(2));
        request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
        request.waitForActiveShards(ActiveShardCount.ONE);
        try {
            int size = request.requests().size();
            BulkResponse responses = client.bulk(request);
            System.out.println(responses);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
