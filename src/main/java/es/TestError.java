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
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.valuecount.ParsedValueCount;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.util.Map;

public class TestError {
    public static void main(String[] args) throws IOException {

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http"),
                        new HttpHost("localhost", 9201, "http")));

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        AggregationBuilder aggregationBuilder = AggregationBuilders.terms("time_bucket").field("time_bucket");
        aggregationBuilder.subAggregation(AggregationBuilders.terms("service_instance_id").field("service_instance_id").subAggregation(AggregationBuilders.count("ct").field("service_instance_id")));
        searchSourceBuilder.aggregation(aggregationBuilder);
        searchSourceBuilder.sort("time_bucket", SortOrder.DESC);
        searchSourceBuilder.size(20);
        SearchRequest searchRequest = new SearchRequest("endpoint_p99_month");
        searchRequest.types("type");
        searchRequest.source(searchSourceBuilder);


        SearchResponse response = client.search(searchRequest);
        for (SearchHit searchHit : response.getHits()) {
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            System.out.println(sourceAsMap);
        }
        Aggregations aggregations = response.getAggregations();
        Terms terms = aggregations.get("time_bucket");

        for (Terms.Bucket bucket : terms.getBuckets()) {
            ParsedLongTerms parsedLongTerms = bucket.getAggregations().get("service_instance_id");
            System.out.println(bucket.getKey());
            for (Terms.Bucket instance : parsedLongTerms.getBuckets()) {
                System.out.println("instance" + instance.getKey());
                ParsedValueCount aggregation = instance.getAggregations().get("ct");
                System.out.println(aggregation.getValue());
            }
        }
        client.close();
    }
}
