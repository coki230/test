package es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.*;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.cardinality.ParsedCardinality;
import org.elasticsearch.search.aggregations.metrics.tophits.ParsedTopHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.Map;

public class TestQuChong {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("10.65.3.34", 19200, "http")));


        DateHistogramAggregationBuilder aggregationBuilder = AggregationBuilders.dateHistogram("start_time").field("start_time");
        aggregationBuilder.subAggregation(AggregationBuilders.count("service_id").field("service_id"));

        aggregationBuilder.dateHistogramInterval(DateHistogramInterval.MINUTE);
        aggregationBuilder.minDocCount(0);
        ExtendedBounds extendedBounds = new ExtendedBounds(1576132692006L, 1576136292006L);
        aggregationBuilder.extendedBounds(extendedBounds);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 根据字段count进行聚合，聚合后的数据放到桶by_count里
        searchSourceBuilder.aggregation(aggregationBuilder);
        SearchRequest searchRequest = new SearchRequest("cloud-monitor_span_info");
        searchRequest.types("type");
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest);
        Aggregations aggregations = searchResponse.getAggregations();
        ParsedDateHistogram all = aggregations.get("start_time");

        for (Histogram.Bucket bucket : all.getBuckets()) {
            System.out.println(bucket.getKeyAsString());
            System.out.println(bucket.getDocCount());
            System.out.println(bucket.getKey());
        }

        client.close();
    }
}
