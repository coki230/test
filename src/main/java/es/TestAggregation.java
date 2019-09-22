package es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.sum.ParsedSum;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.Map;

public class TestAggregation {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http"),
                        new HttpHost("localhost", 9201, "http")));



        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 根据字段count进行聚合，聚合后的数据放到桶by_count里
        AggregationBuilder aggregationBuilder = AggregationBuilders.terms("by_count").field("entity_id");
        // 把桶里面的数据进行函数聚合
        aggregationBuilder.subAggregation(AggregationBuilders.sum("sum_count").field("count"));
        aggregationBuilder.subAggregation(AggregationBuilders.sum("sum_value").field("value"));

        boolQueryBuilder.must(QueryBuilders.rangeQuery("time_bucket").gte(2019052715));
        boolQueryBuilder.must(QueryBuilders.matchQuery("service_id", 2));
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.aggregation(aggregationBuilder);
        SearchRequest searchRequest = new SearchRequest("service_instance_resp_time_hour");
        searchRequest.types("type");
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest);
        for (SearchHit searchHit : searchResponse.getHits()) {
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            System.out.println(sourceAsMap);
        }
        Aggregations aggregations = searchResponse.getAggregations();
        Terms all = aggregations.get("by_count");
        for (Terms.Bucket bucket : all.getBuckets()) {
            ParsedSum sum_count = bucket.getAggregations().get("sum_count");
            ParsedSum sum_value = bucket.getAggregations().get("sum_value");
            System.out.println(bucket.getKey());
            System.out.println(sum_count.getValue());
            System.out.println(sum_value.getValue());
        }



        client.close();
    }
}
