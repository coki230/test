package es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("10.65.3.17", 19200, "http")));
        Test test = new Test();
        test.getServiceInstance(client);

        client.close();
    }



    public void getServiceInstance(RestHighLevelClient client) throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // dops-backend "service_id", 2
        boolQueryBuilder.must().add(QueryBuilders.termQuery("source_service_id", 2));
        boolQueryBuilder.must().add(QueryBuilders.termQuery("dest_service_id", 183));
        boolQueryBuilder.must().add(QueryBuilders.rangeQuery("start_time").gte(1579224610000L).lte(1579224620000L));
        searchSourceBuilder.query(boolQueryBuilder);
        SearchRequest searchRequest = new SearchRequest("cloud-monitor_service_instance_relation-20200117");
        searchRequest.types("type");
        searchSourceBuilder.size(100);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest);
        SearchHits hits = searchResponse.getHits();
        System.out.println(hits.totalHits);
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit searchHit : hits1) {
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            System.out.println("=============================================");
            System.out.println(sourceAsMap);
            System.out.println("=============================================");
        }
    }


    public void getSegmentData(RestHighLevelClient client) throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // dops-backend "service_id", 2
        boolQueryBuilder.must().add(QueryBuilders.termQuery("service_id", 2));
        boolQueryBuilder.must().add(QueryBuilders.termQuery("endpoint_name", "/dops/project"));
        boolQueryBuilder.must().add(QueryBuilders.rangeQuery("start_time").gte(1579224610000L).lte(1579224620000L));
        searchSourceBuilder.query(boolQueryBuilder);
        SearchRequest searchRequest = new SearchRequest("cloud-monitor_segment-20200117");
        searchRequest.types("type");
        searchSourceBuilder.size(100);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest);
        SearchHits hits = searchResponse.getHits();
        System.out.println(hits.totalHits);
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit searchHit : hits1) {
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            System.out.println("=============================================");
            System.out.println(hits1.length);
            System.out.println(sourceAsMap.get("data_binary"));

            System.out.println("=============================================");
        }
    }

}
