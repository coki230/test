package es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.cardinality.ParsedCardinality;
import org.elasticsearch.search.aggregations.metrics.sum.ParsedSum;
import org.elasticsearch.search.aggregations.metrics.valuecount.ParsedValueCount;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCount;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.util.Map;

public class TestDictinctAggregation {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http"),
                        new HttpHost("localhost", 9201, "http")));



        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 根据字段count进行聚合，聚合后的数据放到桶by_count里
        AggregationBuilder aggregationBuilder = AggregationBuilders.terms("aaa").field("time_bucket");
        // 把桶里面的数据进行函数聚合
        aggregationBuilder.subAggregation(AggregationBuilders.count("ct").field("match"));

        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.aggregation(aggregationBuilder);
//        searchSourceBuilder.sort(new FieldSortBuilder("time_bucket").order(SortOrder.DESC));
        SearchRequest searchRequest = new SearchRequest("endpoint_sla_day");
        searchRequest.types("type");
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest);
        for (SearchHit searchHit : searchResponse.getHits()) {
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            System.out.println(sourceAsMap);
        }
        Aggregations aggregations = searchResponse.getAggregations();
        Terms all = aggregations.get("aaa");
        for (Terms.Bucket bucket : all.getBuckets()) {
            ParsedValueCount ct = bucket.getAggregations().get("ct");
            System.out.println(bucket.getKey());
            System.out.println(ct.getValue());
        }



        client.close();
    }
}
