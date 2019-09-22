package es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.*;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.valuecount.ParsedValueCount;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.util.Map;

public class TestHistogram {
    public static void main(String[] args) throws IOException {

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http"),
                        new HttpHost("localhost", 9201, "http")));

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        DateHistogramAggregationBuilder aggregationBuilder = AggregationBuilders.dateHistogram("time").field("heartbeat_time");
        aggregationBuilder.dateHistogramInterval(DateHistogramInterval.DAY);
        aggregationBuilder.subAggregation(AggregationBuilders.terms("detect_point").field("detect_point"));
        searchSourceBuilder.aggregation(aggregationBuilder);
        SearchRequest searchRequest = new SearchRequest("endpoint_inventory");
        searchRequest.types("type");
        searchRequest.source(searchSourceBuilder);


        SearchResponse response = client.search(searchRequest);
        for (SearchHit searchHit : response.getHits()) {
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            System.out.println(sourceAsMap);
        }
        Aggregations aggregations = response.getAggregations();
        ParsedDateHistogram terms = aggregations.get("time");

        for (Histogram.Bucket bucket : terms.getBuckets()) {
            System.out.println(bucket.getKey());
            System.out.println(bucket.getKeyAsString());
            System.out.println(bucket.getDocCount());
            Terms aggregation = bucket.getAggregations().get("detect_point");
            for (Terms.Bucket bucket1 : aggregation.getBuckets()) {
                System.out.println(bucket1.getKey());
                System.out.println(bucket1.getDocCount());
            }
        }
        client.close();
    }
}
