package es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

public class TestQuChong {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("10.65.3.34", 19200, "http")));



        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 根据字段count进行聚合，聚合后的数据放到桶by_count里
        AggregationBuilder aggregationBuilder = AggregationBuilders.terms("service_id").field("service_id");
        searchSourceBuilder.aggregation(aggregationBuilder);
        SearchRequest searchRequest = new SearchRequest("cloud-monitor_span_info");
        searchRequest.types("type");
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest);
        Aggregations aggregations = searchResponse.getAggregations();
        Terms all = aggregations.get("service_id");
        for (Terms.Bucket bucket : all.getBuckets()) {
            System.out.println(bucket.getKeyAsString());
            System.out.println(bucket.getDocCount());
        }

        client.close();
    }
}
